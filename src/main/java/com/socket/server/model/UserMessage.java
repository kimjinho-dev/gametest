package com.socket.server.model;

import lombok.*;

@Getter
@Setter
public class UserMessage {

    // 유저의 입력받는 타입 : 입장, 준비, 시작, 퇴장(강퇴), 배심원단 구성, 찬성, 반대, 유죄, 무죄, 게임재시작
    public enum MessageType {
        TALK, ENTER, READY, START, QUIT, MAKE, YES, NO, GUILTY, NOTGUILTY, RESTART
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
}