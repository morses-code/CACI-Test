package com.github.morsescode.brickorderingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
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
        return brickOrderingService.creatBrickOrder(bricks);
    }
}