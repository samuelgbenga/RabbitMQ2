package com.example.rabbitmq2;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Main exchange for audit
    public static final String AUDIT_EXCHANGE = "audit.exchange";
    public static final String AUDIT_QUEUE = "audit.queue";

    @Bean
    public FanoutExchange auditExchange() {
        return new FanoutExchange(AUDIT_EXCHANGE);
    }

    @Bean
    public Queue auditQueue() {
        return new Queue(AUDIT_QUEUE, true);
    }

    @Bean
    public Binding auditBinding(Queue auditQueue, FanoutExchange auditExchange) {
        return BindingBuilder.bind(auditQueue).to(auditExchange);
    }
}
