package com.github.morsescode.brickorderingservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
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

    private BrickOrder brickOrder;
    private int numBricksOrdered;

    @BeforeEach
    public void setUp() {
        numBricksOrdered = 100;
        brickOrder = new BrickOrder();
        brickOrder.setBricksOrdered(numBricksOrdered);
        brickOrder.setOrderReference(UUID.randomUUID().toString());
    }

    @Test
    public void testCreateOrder() {
        // Given
        when(brickOrderRepository.save(any(BrickOrder.class))).thenReturn(brickOrder);

        // When
        BrickOrder result = brickOrderingService.createBrickOrder(numBricksOrdered);

        // Then
        assertNotNull(result.getOrderReference());
        assertEquals(numBricksOrdered, result.getBricksOrdered());
        verify(brickOrderRepository).save(any(BrickOrder.class));
    }

    @Test
    public void testGetOrder() {
        // Given
        when(brickOrderRepository.findByOrderReference(anyString())).thenReturn(brickOrder);

        // When
        BrickOrder result = brickOrderingService.getBrickOrderByOrderReference(brickOrder.getOrderReference());

        // Then
        assertNotNull(result);
        assertEquals(brickOrder.getId(), result.getId());
        assertEquals(brickOrder.getBricksOrdered(), result.getBricksOrdered());
        assertEquals(brickOrder.getOrderReference(), result.getOrderReference());
        verify(brickOrderRepository).findByOrderReference(anyString());
    }

    @Test
    public void testGetAllOrders() {
        // Given
        BrickOrder anotherOrder = new BrickOrder();

        List<BrickOrder> brickOrders = new ArrayList<BrickOrder>();
        brickOrders.add(brickOrder);
        brickOrders.add(anotherOrder);
        when(brickOrderRepository.findAll()).thenReturn(brickOrders);

        // When
        List<BrickOrder> result = brickOrderingService.getAllBrickOrders();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(brickOrderRepository).findAll();
    }
}