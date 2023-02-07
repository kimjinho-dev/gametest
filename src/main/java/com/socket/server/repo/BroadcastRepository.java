package com.socket.server.repo;

import com.socket.server.model.ServerMessage;
import com.socket.server.model.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

// 처리된 결과가 들어오면 브로드캐스팅
@RequiredArgsConstructor
@Service
public class BroadcastRepository {

    private final SimpMessageSendingOperations messagingTemplate;

    //ENTER, READY, START, QUIT, MAKE, YES, NO, GUILTY, NOTGUILTY, RESTART
    public void userMessage(UserMessage message) {
        if (UserMessage.MessageType.ENTER.equals(message.getType())) {
        } else if (UserMessage.MessageType.READY.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 준비하셨습니다.");
        } else if (UserMessage.MessageType.TALK.equals(message.getType())) {
            message.setMessage(message.getMessage());
        } else if (UserMessage.MessageType.START.equals(message.getType())) {
            message.setMessage("게임이 시작되었습니다.");
//        } else if (UserMessage.MessageType.QUIT.equals(message.getType())) {
        } else if (UserMessage.MessageType.MAKE.equals(message.getType())) {
            message.setMessage("배심원단 구성이 완료되었습니다.");
        } else if (UserMessage.MessageType.YES.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 찬반투표 하셨습니다.");
        } else if (UserMessage.MessageType.NO.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 찬반투표 하셨습니다.");
        } else if (UserMessage.MessageType.GUILTY.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 유무죄투표 하셨습니다.");
        } else if (UserMessage.MessageType.NOTGUILTY.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 유무죄투표 하셨습니다.");
        } else if (UserMessage.MessageType.RESTART.equals(message.getType())) {
            message.setMessage("게임이 재시작 됩니다.");
        }
        messagingTemplate.convertAndSend("/sub/message/user/" + message.getRoomId(), message);
    }

    // ALL_READY, AGREE_RESULT, GUILTY_RESULT, GAME_END
    public void serverMessage(ServerMessage message) {
        messagingTemplate.convertAndSend("/sub/message/user/" + message.getRoomId(), message);
    }
}
