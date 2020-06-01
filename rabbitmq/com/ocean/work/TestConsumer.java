package com.ocean.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class TestConsumer {
    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] argv) throws Exception {

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("localhost");
        //创建一个新的连接
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 同一时刻服务器只会发一条消息给消费者
        //channel.basicQos(1);

        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，false表示手动返回完成状态，true表示自动
        channel.basicConsume(QUEUE_NAME, true, consumer);

        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            // 休眠1秒
            Thread.sleep(1000);
            //下面这行注释掉表示使用自动确认模式
            //channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
