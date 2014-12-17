package ru.samborskiy.calculator.operation;

public interface Operation {

    public void setArgs(int... args);

    public int evaluate() throws IllegalArgumentException;

    public int argumentNumber();

}
