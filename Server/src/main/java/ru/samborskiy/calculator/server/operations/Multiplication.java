package ru.samborskiy.calculator.server.operations;

public class Multiplication extends Operation {

    public Multiplication(int left, int right) {
        super(left, right);
    }

    @Override
    public int evaluate() {
        return left * right;
    }

}
