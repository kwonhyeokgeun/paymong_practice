package com.example.paymong.httptest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    public void getTest(){
        Integer a1 = (int)(Math.random()*100);
        Integer a2 = (int)(Math.random()*100);
        log.info("생성 랜덤값 : ", a1 + ", "+a2);

    }
}
