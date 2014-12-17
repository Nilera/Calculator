package ru.samborskiy.calculator.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import ru.samborskiy.calculator.domain.Expression;
import ru.samborskiy.calculator.domain.Result;
import ru.samborskiy.calculator.operation.Operation;
import ru.samborskiy.calculator.operation.OperationFactory;

import java.io.IOException;
import java.util.Arrays;


public class Worker implements Runnable {

    public static final String QUEUE_NAME = "calculator";

    private String name;
    private Channel channel;

    public Worker(String name, Channel channel) {
        this.name = name;
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(QUEUE_NAME, true, consumer);
            while (true) {
                Delivery delivery = consumer.nextDelivery();
                Expression exp = Expression.extract(delivery.getBody());
                System.out.println(String.format("%s received %s %s", name, exp.getSign(), Arrays.toString(exp.getArgs())));
                expressionProcessing(exp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void expressionProcessing(Expression exp) throws IOException {
        Operation operation = OperationFactory.getOperation(exp.getSign());
        operation.setArgs(exp.getArgs());
        Result result;
        try {
            result = new Result(operation.evaluate(), "");
        } catch (Exception e) {
            result = new Result(0, e.getMessage());
        }
        channel.basicPublish("", exp.getQueue(), null, result.toString().getBytes());
    }

}
