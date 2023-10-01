package com.example.paymong.httptest.controller;

import com.example.paymong.socketTest.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/testhttp")
public class TestController {
    private final WebSocketService webSocketService;
    @GetMapping("")
    public ResponseEntity<Object> getTest() {
        log.info("getTest - Call");
        try{
            webSocketService.send(100,100);
        }catch (Exception e){
            ;
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
