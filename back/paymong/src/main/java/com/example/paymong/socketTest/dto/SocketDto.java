package com.example.paymong.socketTest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocketDto {
    private Long id;
    private WebSocketSession session;
}
