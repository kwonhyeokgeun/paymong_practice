package com.example.paymong.socketTest.handler;

import com.example.paymong.socketTest.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler  extends TextWebSocketHandler {
    // https://github.com/kwonhyeokgeun/PayMong/blob/master/backend/management/src/main/java/com/paymong/management/global/socket/handler/WebSocketHandler.java
    private final WebSocketService webSocketService;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("연결 댐 "); //session.getHandshakeHeaders().get("mongid"));

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message){
        String payload = message.getPayload();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        log.info("연결 끊김~ {}", status.getCode());
    }

}
