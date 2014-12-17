package ru.samborskiy.calculator.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class Server {

    private static final String MQ_ADDRESS = "localhost";
    private static final int WORKER_NUMBER = 3;

    public static void main(String[] args) throws IOException, InterruptedException {
        Channel channel = connect();
        System.out.println("Waiting for messages...");
        for (int i = 1; i <= WORKER_NUMBER; i++) {
            new Thread(new Worker("worker" + i, channel)).start();
        }
    }

    private static Channel connect() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(MQ_ADDRESS);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(Worker.QUEUE_NAME, true, false, true, null);
        return channel;
    }

}
