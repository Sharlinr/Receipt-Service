package com.example.receiptservice;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


    @Profile("!test")
    @Service
    public class RabbitService {
        public static final String exchangeName = "trava-exchange";
        public static final String queueName = "mailservice-job";
        public static final String topic = "mailservice.job";

        @Autowired
        private RabbitTemplate rabbitTemplate;

        @Bean
        Queue queue() {
            return new Queue(queueName, true);
        }

        @Bean
        TopicExchange exchange() {
            return new TopicExchange(exchangeName);
        }

        @Bean
        Binding binding(Queue queue, TopicExchange exchange) {
            return BindingBuilder.bind(queue).to(exchange).with(topic);
        }

        @Bean
        SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            container.setQueueNames(queueName);
            container.setMessageListener(new ReceiptMessage());
            container.setDefaultRequeueRejected(false);
            return container;
        }

    }

