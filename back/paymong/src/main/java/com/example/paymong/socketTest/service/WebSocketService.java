package com.example.paymong.socketTest.service;

import com.example.paymong.socketTest.dto.SocketDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketService {
    // https://github.com/kwonhyeokgeun/PayMong/blob/master/backend/management/src/main/java/com/paymong/management/global/socket/service/WebSocketService.java

    private Map<Long, SocketDto[]> members;
    @PostConstruct
    private void init(){
        members = new HashMap<>();
    }

    public void enter(Long id, int type, WebSocketSession session){
        SocketDto socketDto = SocketDto.builder()
                .id(id)
                .session(session)
                .build();
        if(members.containsKey(id)){
            members.get(id)[type]=socketDto;
        }else{
            SocketDto[] socketDtos = new SocketDto[2];
            socketDtos[type] = socketDto;
            members.put(id, socketDtos);
        }

        SocketDto[] socketDtos = members.get(id);
        log.info(id + "의 소캣 상태 : " + (socketDtos[0]!=null)+ ",  " + (socketDtos[1]!=null));
    }

    public void send(int a1, int a2) throws IOException {
        Long id = 1L;
        SocketDto[] socketDtos = members.get(id);
        if(socketDtos==null){
            log.info("socketDtos null");
            return;
        }
        TextMessage message = new TextMessage(a1 + " "+a2);
        for(int i=0; i<2; i++){
            SocketDto socketDto = socketDtos[i];
            if(socketDto!=null){
                socketDto.getSession().sendMessage(message);
            }
        }
    }

    public void out(Long id, int type){
        if(!members.containsKey(id)) return;
        members.get(id)[type]=null;
        boolean isEmpty=true;
        for(int i=0; i<2; i++){
            if(type==i)continue;
            if(members.get(id)[i]!=null){
                isEmpty=false;
                break;
            }
        }
        if(isEmpty) members.remove(id);

        SocketDto[] socketDtos = members.get(id);
        if(socketDtos!=null)
            log.info(id + "의 소캣 상태 : " + (socketDtos[0]!=null)+ ",  " + (socketDtos[1]!=null));
        else
            log.info(id + "의 소캣 상태 : " + false+ ",  " + false);
    }
}
