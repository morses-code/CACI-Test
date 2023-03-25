package com.github.morsescode.brickorderingservice;

import java.util.List;
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

    public BrickOrder getBrickOrderByOrderReference(String orderReference) {
        return brickOrderRepository.findByOrderReference(orderReference);
    }

    public List<BrickOrder> getAllBrickOrders() {
        return brickOrderRepository.findAll();
    }

    public BrickOrder updateBrickOrder(String orderReference, int bricksOrdered) {
        BrickOrder brickOrder = getBrickOrderByOrderReference(orderReference);
        brickOrder.setBricksOrdered(bricksOrdered);
        return brickOrderRepository.save(brickOrder);
    }
}
