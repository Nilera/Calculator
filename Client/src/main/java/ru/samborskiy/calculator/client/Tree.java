package ru.samborskiy.calculator.client;

public class Tree {

    private char sign;
    private Integer value;
    private Tree left;
    private Tree right;

    public Tree(Integer value) {
        this.value = value;
    }

    public Tree(Tree left, Tree right, char sign) {
        this.left = left;
        this.right = right;
        this.sign = sign;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public Tree getLeft() {
        return left;
    }

    public Tree getRight() {
        return right;
    }

    public char getSign() {
        return sign;
    }

}
