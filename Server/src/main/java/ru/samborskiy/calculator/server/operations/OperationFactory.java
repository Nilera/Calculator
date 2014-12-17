package ru.samborskiy.calculator.server.operations;

public class OperationFactory {

    private OperationFactory() {
    }

    public static Operation getOperation(char sign, int left, int right) {
        switch (sign) {
            case '+':
                return new Addition(left, right);
            case '-':
                return new Substraction(left, right);
            case '*':
                return new Multiplication(left, right);
            case '/':
                return new Division(left, right);
            default:
                throw new IllegalArgumentException("Incorrect operation " + sign);
        }
    }

}
