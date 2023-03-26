package com.github.morsescode.brickorderingservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/order")
public class BrickOrderController {

    @Autowired
    private BrickOrderingService brickOrderingService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BrickOrder createBrickOrder(@RequestParam int bricks) {
        return brickOrderingService.createBrickOrder(bricks);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BrickOrder getBrickOrder(@RequestParam String orderReference) {
        return brickOrderingService.getBrickOrderByOrderReference(orderReference);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<BrickOrder> getAllBrickOrders() {
        return brickOrderingService.getAllBrickOrders();
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateBrickOrder(@RequestParam String orderReference, int bricks) {
        try {
            BrickOrder brickOrder = brickOrderingService.getBrickOrderByOrderReference(orderReference);
            if (brickOrder.getIsDispatched()) {
                throw new IllegalArgumentException("Order already dispatched");
            }
            brickOrder.setBricksOrdered(bricks);
            return ResponseEntity.ok(brickOrderingService.updateBrickOrder(orderReference, bricks));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/dispatch", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> orderDispatched(@RequestParam String orderReference) {
        try {
            BrickOrder brickOrder = brickOrderingService.orderDispatched(orderReference);
            return ResponseEntity.ok(brickOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
