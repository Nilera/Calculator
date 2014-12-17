package ru.samborskiy.calculator.client;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Tree implements Iterable<Tree> {

    private String sign;
    private Integer value;
    private List<Tree> children;

    public Tree(Integer value) {
        this.value = value;
    }

    public Tree(String sign, List<Tree> children) {
        this.sign = sign;
        this.children = children;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public String getSign() {
        return sign;
    }

    public List<Tree> getChildren() {
        return children;
    }

    @Override
    public Iterator<Tree> iterator() {
        return children.iterator();
    }

    @Override
    public void forEach(Consumer<? super Tree> action) {
        children.forEach(action);
    }

    @Override
    public Spliterator<Tree> spliterator() {
        return children.spliterator();
    }

    public int size() {
        return children.size();
    }

}
