package com.github.morsescode.brickorderingservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BrickOrderingServiceTests {

    @Mock
    private BrickOrderRepository brickOrderRepository;

    @InjectMocks
    private BrickOrderingService brickOrderingService;

    @Test
    public void testCreateOrder() {
        // Given
        int numBricksOrdered = 100;
        BrickOrder brickOrder = new BrickOrder();
        brickOrder.setBricksOrdered(numBricksOrdered);
        brickOrder.setOrderReference(UUID.randomUUID().toString());
        when(brickOrderRepository.save(any(BrickOrder.class))).thenReturn(brickOrder);

        // When
        BrickOrder result = brickOrderingService.createBrickOrder(numBricksOrdered);

        // Then
        assertNotNull(result.getOrderReference());
        assertEquals(numBricksOrdered, result.getBricksOrdered());
        verify(brickOrderRepository).save(any(BrickOrder.class));
    }
}