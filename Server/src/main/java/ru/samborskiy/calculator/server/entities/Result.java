package ru.samborskiy.calculator.server.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;

public final class Result {

    private final int result;

    @JsonCreator
    public Result(@JsonProperty("result") int result) {
        this.result = result;
    }

    public static Result build(byte[] data) {
        try {
            return EntityUtil.serialize(data, Result.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getResult() {
        return result;
    }

    @Override
    public String toString() {
        return EntityUtil.deserialize(this);
    }

}
