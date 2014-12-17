package ru.samborskiy.calculator.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;

public final class Result {

    private final int result;
    private final String errorMessage;

    @JsonCreator
    public Result(@JsonProperty("result") int result, @JsonProperty("errorMessage") String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public static Result extract(byte[] data) {
        try {
            return EntityUtil.deserialize(data, Result.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return EntityUtil.serialize(this);
    }

}
