package com.example.rabbitmq2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuditProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private AuditProducer auditProducer;

    @Test
    void shouldSendAuditMessageToExchange() {
        // given
        String message = "Created product: Laptop ($1200.0)";

        // when
        auditProducer.sendAuditMessage(message);

        // then
        verify(rabbitTemplate, times(1))
                .convertAndSend(RabbitMQConfig.AUDIT_EXCHANGE, "", message);
    }
}