package ru.samborskiy.calculator.server.operations;

public class Division extends Operation {

    public Division(int left, int right) {
        super(left, right);
    }

    @Override
    public int evaluate() {
        return left / right;
    }

}
