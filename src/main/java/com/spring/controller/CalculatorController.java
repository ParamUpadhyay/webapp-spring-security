package com.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.spring.model.CalculatorDetails;

@RestController
public class CalculatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorController.class);

    @PostMapping("/add")
    public CalculatorDetails add(@RequestBody CalculatorDetails request) {
        int result = request.getFirstOperandValue() + request.getSecondOperandValue();
        LOGGER.info("Performing addition operation");
        return new CalculatorDetails(request.getFirstOperandValue(), request.getSecondOperandValue(), "+", result);
    }

    @PostMapping("/subtract")
    public CalculatorDetails subtract(@RequestBody CalculatorDetails request) {
        int result = request.getFirstOperandValue() - request.getSecondOperandValue();
        LOGGER.info("Performing subtraction operation");
        return new CalculatorDetails(request.getFirstOperandValue(), request.getSecondOperandValue(), "-", result);
    }

    @PostMapping("/multiply")
    public CalculatorDetails multiply(@RequestBody CalculatorDetails request) {
        int result = request.getFirstOperandValue() * request.getSecondOperandValue();
        LOGGER.info("Performing multiplication operation");
        return new CalculatorDetails(request.getFirstOperandValue(), request.getSecondOperandValue(), "*", result);
    }

    @PostMapping("/divide")
    public CalculatorDetails divide(@RequestBody CalculatorDetails request) {
        if (request.getSecondOperandValue() == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        int result = request.getFirstOperandValue() / request.getSecondOperandValue();
        LOGGER.info("Performing division operation");
        return new CalculatorDetails(request.getFirstOperandValue(), request.getSecondOperandValue(), "/", result);
    }
}
