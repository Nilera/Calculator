package ru.samborskiy.calculator.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import ru.samborskiy.calculator.server.entities.Expression;
import ru.samborskiy.calculator.server.entities.Result;
import ru.samborskiy.calculator.server.operations.Operation;
import ru.samborskiy.calculator.server.operations.OperationFactory;

import java.io.IOException;


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
                Expression exp = Expression.build(delivery.getBody());
                System.out.println(String.format("%s received %d %c %d", name, exp.getLeft(), exp.getSign(), exp.getRight()));
                expressionProcessing(exp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void expressionProcessing(Expression exp) throws IOException {
        Operation operation = OperationFactory.getOperation(exp.getSign(), exp.getLeft(), exp.getRight());
        Result result = new Result(operation.evaluate());
        channel.basicPublish("", exp.getQueue(), null, result.toString().getBytes());
    }

}
