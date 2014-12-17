package ru.samborskiy.calculator.operation;

public class Multiplication extends BinaryOperation {

    @Override
    public int evaluate() throws IllegalArgumentException {
        return left * right;
    }

}
