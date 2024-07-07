package com.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/secured")
    public String test() {
        return "Hello World ";
    }
}
