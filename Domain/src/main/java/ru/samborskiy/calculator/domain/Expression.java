package ru.samborskiy.calculator.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;

public final class Expression {

    private final String sign;
    private final String queue;
    private final int[] args;

    @JsonCreator
    public Expression(@JsonProperty("sign") String sign, @JsonProperty("queue") String queue,
                      @JsonProperty("args") int[] args) {
        this.sign = sign;
        this.queue = queue;
        this.args = args;
    }

    public static Expression extract(byte[] data) {
        try {
            return EntityUtil.deserialize(data, Expression.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSign() {
        return sign;
    }

    public String getQueue() {
        return queue;
    }

    public int[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return EntityUtil.serialize(this);
    }
}
