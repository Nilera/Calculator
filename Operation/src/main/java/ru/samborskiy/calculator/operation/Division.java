package ru.samborskiy.calculator.operation;

public class Division extends BinaryOperation {

    @Override
    public int evaluate() throws IllegalArgumentException {
        return left / right;
    }

}
