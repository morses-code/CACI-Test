package com.github.morsescode.brickorderingservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BrickOrderController.class)
public class BrickOrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrickOrderingService brickOrderingService;

    @MockBean
    private BrickOrderRepository brickOrderRepository;

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
    public void testCreateOrder() throws Exception {
        // Given
        when(brickOrderingService.createBrickOrder(numBricksOrdered)).thenReturn(brickOrder);

        // When/Then
        mockMvc.perform(post("/api/order")
                .param("bricks", Integer.toString(numBricksOrdered)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderReference", notNullValue()))
                .andExpect(jsonPath("$.bricksOrdered", is(numBricksOrdered)));

        verify(brickOrderingService).createBrickOrder(numBricksOrdered);
    }

    @Test
    public void testGetOrder() throws Exception {
        // Given
        when(brickOrderingService.getBrickOrderByOrderReference(anyString())).thenReturn(brickOrder);

        // When/Then
        mockMvc.perform(get("/api/order")
                .param("orderReference", brickOrder.getOrderReference()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderReference", is(brickOrder.getOrderReference())))
                .andExpect(jsonPath("$.bricksOrdered", is(numBricksOrdered)));

        verify(brickOrderingService).getBrickOrderByOrderReference(brickOrder.getOrderReference());
    }

    @Test
    public void testGetAllOrders() throws Exception {
        // Given
        BrickOrder anotherOrder = new BrickOrder();
        anotherOrder.setBricksOrdered(10);
        anotherOrder.setOrderReference(UUID.randomUUID().toString());

        List<BrickOrder> brickOrders = new ArrayList<BrickOrder>();
        brickOrders.add(brickOrder);
        brickOrders.add(anotherOrder);

        when(brickOrderingService.getAllBrickOrders()).thenReturn(brickOrders);

        // When/Then
        mockMvc.perform(get("/api/order/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].bricksOrdered", is(brickOrder.getBricksOrdered())))
                .andExpect(jsonPath("$[0].orderReference", is(brickOrder.getOrderReference())))
                .andExpect(jsonPath("$[1].bricksOrdered", is(anotherOrder.getBricksOrdered())))
                .andExpect(jsonPath("$[1].orderReference", is(anotherOrder.getOrderReference())));

        verify(brickOrderingService).getAllBrickOrders();
    }

    @Test
    public void testUpdateBrickOrder() throws Exception {
        // Given
        String newOrderReference = "test-reference";
        int bricksOrdered = 10;
        BrickOrder updatedOrder = new BrickOrder();
        updatedOrder.setOrderReference(newOrderReference);
        updatedOrder.setBricksOrdered(bricksOrdered);
        when(brickOrderingService.updateBrickOrder(newOrderReference, bricksOrdered)).thenReturn(updatedOrder);

        // When/Then
        mockMvc.perform(post("/api/order/update")
                .param("orderReference", newOrderReference)
                .param("bricks", String.valueOf(bricksOrdered)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderReference", is(newOrderReference)))
                .andExpect(jsonPath("$.bricksOrdered", is(bricksOrdered)));

        verify(brickOrderingService).updateBrickOrder(newOrderReference, bricksOrdered);
    }

    @Test
    public void testOrderDispatched() throws Exception {
        // Given
        BrickOrder dispatchedBrickOrder = new BrickOrder();
        dispatchedBrickOrder.setOrderReference(UUID.randomUUID().toString());
        dispatchedBrickOrder.setBricksOrdered(numBricksOrdered);
        dispatchedBrickOrder.setIsDispatched(true);

        when(brickOrderingService.orderDispatched(dispatchedBrickOrder.getOrderReference()))
                .thenReturn(dispatchedBrickOrder);

        // When/Then
        mockMvc.perform(post("/api/order/dispatch")
                .param("orderReference", dispatchedBrickOrder.getOrderReference()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bricksOrdered", is(numBricksOrdered)))
                .andExpect(jsonPath("$.orderReference", is(dispatchedBrickOrder.getOrderReference())))
                .andExpect(jsonPath("$.isDispatched", is(true)));

        verify(brickOrderingService).orderDispatched(dispatchedBrickOrder.getOrderReference());
    }

    @Test
    public void testOrderDispatchedWhenOrderReferenceIsNullReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/order/dispatch"))
                .andExpect(status().isBadRequest());
    }
}
