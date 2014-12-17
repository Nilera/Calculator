package ru.samborskiy.calculator.client;

import ru.samborskiy.calculator.server.entities.Expression;
import ru.samborskiy.calculator.server.entities.Result;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;

public class ReversePolishNotation {

    private static final String SIGNS = "+-*/";

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
                if (isSign(token)) {
                    Tree right = stack.pop();
                    Tree left = stack.pop();
                    stack.push(new Tree(left, right, token.charAt(0)));
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

    public int evaluate(MQService service) throws IOException, InterruptedException {
        return dfs(root, service);
    }

    private int dfs(Tree node, MQService service) throws IOException, InterruptedException {
        if (node.getLeft() != null) {
            int left = dfs(node.getLeft(), service);
            int right = dfs(node.getRight(), service);
            Expression expression = new Expression(left, right, node.getSign(), service.getQueueName());
            service.sendMessage(expression.toString().getBytes());
            Result result = Result.build(service.getMessage().getBody());
            node.setValue(result.getResult());
            return node.getValue();
        }
        return node.getValue();
    }

    private Integer getInteger(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean isSign(String token) {
        return token.length() == 1 && SIGNS.contains(token);
    }
}
