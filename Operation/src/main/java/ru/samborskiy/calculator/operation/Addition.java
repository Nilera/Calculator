package ru.samborskiy.calculator.operation;

public class Addition extends BinaryOperation {

    @Override
    public int evaluate() throws IllegalArgumentException {
        return left + right;
    }

}
