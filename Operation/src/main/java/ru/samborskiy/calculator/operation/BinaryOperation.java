package ru.samborskiy.calculator.operation;

public abstract class BinaryOperation implements Operation {

    protected int left;
    protected int right;

    @Override
    public void setArgs(int... args) {
        left = args[0];
        right = args[1];
    }

    @Override
    public int argumentNumber() {
        return 2;
    }
}
