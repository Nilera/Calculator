package ru.samborskiy.calculator.client;

import ru.samborskiy.calculator.domain.Expression;
import ru.samborskiy.calculator.domain.Result;
import ru.samborskiy.calculator.operation.Operation;
import ru.samborskiy.calculator.operation.OperationFactory;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class ReversePolishNotation {

    private String expressionText;
    private Tree root;

    public ReversePolishNotation(String expressionText) {
        this.expressionText = expressionText;
    }

    public boolean parseExpression() {
        Stack<Tree> stack = new Stack<>();
        StringTokenizer st = new StringTokenizer(expressionText, " ");
        try {
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                Operation operation = OperationFactory.getOperation(token);
                if (operation != null) {
                    List<Tree> children = new ArrayList<>();
                    for (int i = 0; i < operation.argumentNumber(); i++) {
                        children.add(stack.pop());
                    }
                    stack.push(new Tree(token, children));
                } else {
                    Integer value = getInteger(token);
                    if (value == null) {
                        return false;
                    } else {
                        stack.push(new Tree(value));
                    }
                }
            }
            root = stack.pop();
            return stack.isEmpty();
        } catch (EmptyStackException e) {
            return false;
        }
    }

    public int evaluate(MQService service) throws Exception {
        return recursiveEvaluate(root, service);
    }

    private int recursiveEvaluate(Tree node, MQService service) throws Exception {
        if (node.getChildren() != null) {
            int[] args = new int[node.size()];
            int i = args.length - 1;
            for (Tree child : node) {
                args[i--] = recursiveEvaluate(child, service);
            }
            node.setValue(calculateValue(service, node.getSign(), args));
            return node.getValue();
        }
        return node.getValue();
    }

    private int calculateValue(MQService service, String sign, int... args) throws Exception {
        Expression expression = new Expression(sign, args);
        service.sendMessage(expression.toString().getBytes());
        Result result = Result.extract(service.getMessage().getBody());
        if (!result.getErrorMessage().isEmpty()) {
            throw new IllegalArgumentException(result.getErrorMessage());
        }
        return result.getResult();
    }

    private Integer getInteger(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
