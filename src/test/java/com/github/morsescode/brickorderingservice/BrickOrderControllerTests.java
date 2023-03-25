package com.github.morsescode.brickorderingservice;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.util.UUID;

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

    @Test
    public void testCreateOrder() throws Exception {
        int numBricksOrdered = 100;
        BrickOrder brickOrder = new BrickOrder();
        brickOrder.setBricksOrdered(numBricksOrdered);
        brickOrder.setOrderReference(UUID.randomUUID().toString());
        when(brickOrderingService.creatBrickOrder(numBricksOrdered)).thenReturn(brickOrder);

        mockMvc.perform(post("/api/order")
                .param("bricks", Integer.toString(numBricksOrdered)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderReference", notNullValue()))
                .andExpect(jsonPath("$.bricksOrdered", is(numBricksOrdered)));

        verify(brickOrderingService).creatBrickOrder(numBricksOrdered);
    }
}
