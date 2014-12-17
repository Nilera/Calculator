package ru.samborskiy.calculator.server.operations;

public class Substraction extends Operation {

    public Substraction(int left, int right) {
        super(left, right);
    }

    @Override
    public int evaluate() {
        return left - right;
    }

}
