package ru.samborskiy.calculator.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;

public final class Expression {

    private final String sign;
    private final int[] args;

    @JsonCreator
    public Expression(@JsonProperty("sign") String sign, @JsonProperty("args") int[] args) {
        this.sign = sign;
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

    public int[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return EntityUtil.serialize(this);
    }
}
