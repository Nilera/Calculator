package ru.samborskiy.calculator.client;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

import java.io.IOException;

public class MQService implements AutoCloseable {

    private static final String MQ_ADDRESS = "localhost";
    private static final String QUEUE_NAME = "calculator";

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

    public void sendMessage(byte[] message) throws IOException {
        if (channel != null && channel.isOpen()) {
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().replyTo(queue).build();
            channel.basicPublish("", QUEUE_NAME, properties, message);
        }
    }

    public Delivery getMessage() throws InterruptedException {
        return consumer.nextDelivery();
    }

}
