package com.compass.ecommercechallenge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ReportController {

    @GetMapping("/totalOrders")
    public void totalOrders(){

    }
}
