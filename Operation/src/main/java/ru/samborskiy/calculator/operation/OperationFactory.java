package ru.samborskiy.calculator.operation;

import java.util.HashMap;
import java.util.Map;

public class OperationFactory {

    private static Map<String, Operation> operations = new HashMap<String, Operation>() {
        {
            put("+", new Addition());
            put("-", new Subtraction());
            put("*", new Multiplication());
            put("/", new Division());
        }
    };

    private OperationFactory() {
    }

    public static Operation getOperation(String sign) {
        return operations.get(sign);
    }

}
