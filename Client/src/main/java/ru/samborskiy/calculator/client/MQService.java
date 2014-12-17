package ru.samborskiy.calculator.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import ru.samborskiy.calculator.server.Worker;

import java.io.IOException;

public class MQService implements AutoCloseable {

    private static final String MQ_ADDRESS = "localhost";

    private Connection connection;
    private Channel channel;
    private String consumerTag;
    private String queue;
    private QueueingConsumer consumer;

    public void connect() throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(MQ_ADDRESS);
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        queue = channel.queueDeclare().getQueue();
        consumer = new QueueingConsumer(channel);
        consumerTag = channel.basicConsume(queue, consumer);
    }

    @Override
    public void close() throws Exception {
        if (channel != null) {
            channel.basicCancel(consumerTag);
            channel.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public String getQueueName() {
        return queue;
    }

    public void sendMessage(byte[] message) throws IOException {
        if (channel != null && channel.isOpen()) {
            channel.basicPublish("", Worker.QUEUE_NAME, null, message);
        }
    }

    public Delivery getMessage() throws InterruptedException {
        return consumer.nextDelivery();
    }

}
