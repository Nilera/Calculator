package ru.samborskiy.calculator.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {

    public static final String EXIT_COMMAND = "exit";

    public static void main(String[] args) {
        try (MQService service = new MQService()) {
            service.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String expression;
            while (!(expression = in.readLine()).equals(EXIT_COMMAND)) {
                ReversePolishNotation notation = new ReversePolishNotation(expression);
                try {
                    if (notation.parseExpression()) {
                        System.out.println(notation.evaluate(service));
                    } else {
                        System.err.println("Incorrect expression");
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
