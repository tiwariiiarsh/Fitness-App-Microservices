package com.fitness.aiservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration   // 👉 Ye class Spring ko batati hai ki yahan RabbitMQ related beans define honge
public class RabbitMqConfig {

    // 👉 Queue ka naam application.yml se uthaya ja raha hai
    // example: rabbitmq.queue.name = activity.queue
    @Value("${rabbitmq.queue.name}")
    private String queue;

    // 👉 Exchange ka naam application.yml se
    // example: rabbitmq.exchange.name = fitness.exchange
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    // 👉 Routing key application.yml se
    // example: rabbitmq.routing.key = activity.tracking
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    // 👉 Queue bean create karta hai
    // true = durable (RabbitMQ restart hone ke baad bhi queue rahegi)
    @Bean
    public Queue activityQueue() {
        return new Queue(queue, true);
    }

    // 👉 Direct Exchange create karta hai
    // DirectExchange routing key ke basis par message forward karta hai
    @Bean
    public DirectExchange activityExchange() {
        return new DirectExchange(exchange);
    }

    // 👉 Queue ko Exchange ke sath bind karta hai using routing key
    // Matlab: jo message "activity.tracking" key ke sath aayega
    // wahi activity.queue me jayega
    @Bean
    public Binding activityBinding(
            Queue activityQueue,
            DirectExchange activityExchange
    ) {
        return BindingBuilder
                .bind(activityQueue)          // queue bind ho rahi hai
                .to(activityExchange)         // kis exchange se
                .with(routingKey);            // kis routing key par
    }

    // 👉 Java Object <-> JSON conversion ke liye
    // Producer: Object → JSON
    // Consumer: JSON → Object
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
