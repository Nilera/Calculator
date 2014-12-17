package ru.samborskiy.calculator.server.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;

public final class Expression {

    private final int left;
    private final int right;
    private final char sign;
    private final String queue;

    @JsonCreator
    public Expression(@JsonProperty("left") int left, @JsonProperty("right") int right,
                      @JsonProperty("sign") char sign, @JsonProperty("queue") String queue) {
        this.left = left;
        this.right = right;
        this.sign = sign;
        this.queue = queue;
    }

    public static Expression build(byte[] data) {
        try {
            return EntityUtil.serialize(data, Expression.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public char getSign() {
        return sign;
    }

    public String getQueue() {
        return queue;
    }

    @Override
    public String toString() {
        return EntityUtil.deserialize(this);
    }
}
