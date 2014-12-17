package ru.samborskiy.calculator.operation;

public class Subtraction extends BinaryOperation {

    @Override
    public int evaluate() throws IllegalArgumentException {
        return left - right;
    }

}
