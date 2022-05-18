package com.example.receiptservice;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.adapter.AbstractAdaptableMessageListener;

public class ReceiptMessage extends AbstractAdaptableMessageListener {


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        byte[] payload = message.getBody();

    }
}
