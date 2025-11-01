package com.example.rabbitmq2;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final AuditProducer auditProducer;

    public Product createProduct(Product product) {

        Product saved = repository.save(product);
        // Send audit log to RabbitMQ
        auditProducer.sendAuditMessage("Created product: " + saved.getName() + " ($" + saved.getPrice() + ")");
        return saved;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }
}
