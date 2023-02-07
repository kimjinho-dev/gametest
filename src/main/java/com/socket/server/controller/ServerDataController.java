package com.socket.server.controller;

import com.socket.server.model.ServerMessage;
import com.socket.server.model.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;


// 서버 데이터가 들어오는곳
@RequiredArgsConstructor
@Controller
public class ServerDataController {

    private final SimpMessageSendingOperations messagingTemplate;

    // ALL_READY, AGREE_RESULT, GUILTY_RESULT, GAME_END
    @MessageMapping("/message/sever")
    public void serverMessage(ServerMessage message) {
        if (ServerMessage.MessageType.ALL_READY.equals(message.getType())) {
            message.setMessage("준비 완료 되었습니다.");
        } else if (ServerMessage.MessageType.AGREE_RESULT.equals(message.getType())) {
            message.setMessage("찬반 투표가 완료되었습니다.");
        } else if (ServerMessage.MessageType.GUILTY_RESULT.equals(message.getType())) {
            message.setMessage("유무죄 투표가 완료되었습니다.");
        } else if (ServerMessage.MessageType.GAME_END.equals(message.getType())) {
            message.setMessage("게임이 종료되었습니다.");
        }
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}