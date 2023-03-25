package com.github.morsescode.brickorderingservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public BrickOrder updatBrickOrder(@RequestParam String orderReference, int bricks) {
        return brickOrderingService.updateBrickOrder(orderReference, bricks);
    }
}
