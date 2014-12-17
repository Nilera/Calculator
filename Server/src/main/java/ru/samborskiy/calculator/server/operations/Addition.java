package ru.samborskiy.calculator.server.operations;

public class Addition extends Operation {

    public Addition(int left, int right) {
        super(left, right);
    }

    @Override
    public int evaluate() {
        return left + right;
    }

}
