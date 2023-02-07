package com.socket.server.model;

import lombok.*;

@Getter
@Setter
public class ServerMessage {

    // 서버에게 직접 받는 타입 : 준비완료, 찬반투표완료, 유무죄투표완료, 게임종료
    public enum MessageType {
        ALL_READY, AGREE_RESULT, GUILTY_RESULT, GAME_END
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
}
