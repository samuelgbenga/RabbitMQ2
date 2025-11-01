package com.example.rabbitmq2;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendAuditMessage(String message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.AUDIT_EXCHANGE,
                "", // fanout ignores routing keys
                message
        );
        System.out.println("🔹 [AuditProducer] Sent audit message: " + message);
    }
}
