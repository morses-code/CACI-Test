package com.github.morsescode.brickorderingservice;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrickOrderingService {
    @Autowired
    private BrickOrderRepository brickOrderRepository;

    public BrickOrder createBrickOrder(int bricksOrdered) {
        BrickOrder brickOrder = new BrickOrder();
        brickOrder.setBricksOrdered(bricksOrdered);
        brickOrder.setOrderReference(UUID.randomUUID().toString());
        return brickOrderRepository.save(brickOrder);
    }
}
