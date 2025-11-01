package com.example.rabbitmq2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    private ProductRepository repository;
    private AuditProducer auditProducer;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        repository = mock(ProductRepository.class);
        auditProducer = mock(AuditProducer.class);
        productService = new ProductService(repository, auditProducer);
    }


    @Test
    void testCreateProduct() {
        // Arrange
        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(1200.0);

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Laptop");
        savedProduct.setPrice(1200.0);

        when(repository.save(product)).thenReturn(savedProduct);

        // Act
        Product result = productService.createProduct(product);

        // Assert
        assertNotNull(result);
        assertEquals(savedProduct.getId(), result.getId());
        assertEquals("Laptop", result.getName());

        // Verify audit message was sent
        verify(auditProducer, times(1))
                .sendAuditMessage("Created product: Laptop ($1200.0)");
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        Product p1 = new Product();
        p1.setName("Laptop");
        p1.setPrice(1200.0);

        Product p2 = new Product();
        p2.setName("Phone");
        p2.setPrice(800.0);

        List<Product> productList = Arrays.asList(p1, p2);
        when(repository.findAll()).thenReturn(productList);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals("Phone", result.get(1).getName());
    }

}
