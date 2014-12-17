package ru.samborskiy.calculator.server.operations;

public abstract class Operation {

    protected int left;
    protected int right;

    public Operation(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public abstract int evaluate();

}
