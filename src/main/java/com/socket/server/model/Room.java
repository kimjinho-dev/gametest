package com.socket.server.model;

import lombok.*;

import java.util.*;

@Getter
@Setter
public class Room {

    private String roomId;
    private String title;
    private List<String> inUsers;
    private int readyCount;
    private int agreeCount;
    private Map<String,List<String>> agreeVote;
    private int guiltyCount;
    private Map<String,List<String>> guiltyVote;
    private Integer roomNum;
    private String password;


    public static Room create(String title, String password) {
        Room gameRoom = new Room();
        gameRoom.roomId = UUID.randomUUID().toString();
        gameRoom.title = title;
        gameRoom.inUsers = new ArrayList<String>();
        gameRoom.readyCount = 0;
        gameRoom.agreeCount = 0;
        gameRoom.agreeVote = new HashMap<String,List<String>>();
        gameRoom.agreeVote.put("찬성",new ArrayList<>());
        gameRoom.agreeVote.put("반대",new ArrayList<>());
        gameRoom.guiltyCount = 0;
        gameRoom.guiltyVote = new HashMap<String,List<String>>();
        gameRoom.guiltyVote.put("유죄",new ArrayList<>());
        gameRoom.guiltyVote.put("무죄",new ArrayList<>());
        gameRoom.password = password;
        return gameRoom;
    }
}
