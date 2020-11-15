package de.wkuch.app;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * Hello world!
 */
public class Producer {

    private final static String EXCHANGE_NAME = "testExch";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            long time = System.currentTimeMillis();
            Random rn = new Random();
            while(true){
                if(time+1000 < System.currentTimeMillis()){
                    time = System.currentTimeMillis();
                    String message = "Dice roll of this second: " + (rn.nextInt(6) + 1);
                    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
                    System.out.println(" [x] Sent '" + message + "'");
                }

            }

        }
    }


}

