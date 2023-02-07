package com.socket.server.controller;

import com.socket.server.model.ServerMessage;
import com.socket.server.model.UserMessage;
import com.socket.server.repo.BroadcastRepository;
import com.socket.server.repo.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 유저 데이터가 들어오는곳
@RequiredArgsConstructor
@Controller
@Slf4j
public class UserDataController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final RoomRepository roomRepository;
    private final BroadcastRepository broadcastRepository;

    //ENTER, READY, START, QUIT, MAKE, YES, NO, GUILTY, NOTGUILTY, RESTART
    @MessageMapping("/message/user")
    public void userMessage(UserMessage message) {
        if (UserMessage.MessageType.ENTER.equals(message.getType())) {
            roomRepository.setUserEnterInfo(message.getRoomId(), message.getSender());
//            message.setMessage(roomRepository.getUserEnterRoomId(message.getRoomId()).toString());
            Map<String,Integer> userDegree = new HashMap<String,Integer>();
            List<String> users = roomRepository.getUserEnterRoomId(message.getRoomId());
            int degree = 0;
            if (users.size() != 0) {
                degree = 360 / users.size();
            }
            for (int i=0; i<users.size();i++) {
                    userDegree.put(users.get(i),-180-(degree*i));
            }
            message.setMessage(userDegree.toString());
            broadcastRepository.userMessage(message);
        } else if (UserMessage.MessageType.TALK.equals(message.getType())) {
            broadcastRepository.userMessage(message);
        } else if (UserMessage.MessageType.READY.equals(message.getType())) {
            roomRepository.setUserReadyInfo(message.getRoomId(), message.getSender());
            broadcastRepository.userMessage(message);
        } else if (UserMessage.MessageType.START.equals(message.getType())) {
            broadcastRepository.userMessage(message);
        } else if (UserMessage.MessageType.QUIT.equals(message.getType())) {
            roomRepository.removeUserEnterInfo(message.getRoomId(), message.getSender());
            Map<String,Integer> userDegree = new HashMap<String,Integer>();
            List<String> users = roomRepository.getUserEnterRoomId(message.getRoomId());
            int degree = 0;
            if (users.size() != 0) {
                degree = 360 / users.size();
            }
            for (int i=0; i<users.size();i++) {
                userDegree.put(users.get(i),-180-(degree*i));
            }
            message.setMessage(userDegree.toString());
            broadcastRepository.userMessage(message);
        } else if (UserMessage.MessageType.MAKE.equals(message.getType())) {
            broadcastRepository.userMessage(message);
        } else if (UserMessage.MessageType.YES.equals(message.getType())) {
            roomRepository.setAgreeInfo(message.getRoomId(), message.getSender());
            broadcastRepository.userMessage(message);
        } else if (UserMessage.MessageType.NO.equals(message.getType())) {
            roomRepository.setDisagreeInfo(message.getRoomId(), message.getSender());
            broadcastRepository.userMessage(message);
        } else if (UserMessage.MessageType.GUILTY.equals(message.getType())) {
            roomRepository.setGuiltyInfo(message.getRoomId(), message.getSender());
            broadcastRepository.userMessage(message);
        } else if (UserMessage.MessageType.NOTGUILTY.equals(message.getType())) {
            roomRepository.setNotguiltyInfo(message.getRoomId(), message.getSender());
            broadcastRepository.userMessage(message);
        } else if (UserMessage.MessageType.RESTART.equals(message.getType())) {
            broadcastRepository.userMessage(message);
        }
    }
}