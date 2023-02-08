package com.socket.server.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {
    private String roomId;
    private String title;
    private Integer userCount;
    private Integer roomNum;
    private boolean isLock;
}
