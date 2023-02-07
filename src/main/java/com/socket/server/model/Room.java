package com.socket.server.model;

import lombok.*;

import java.util.*;

@Getter
@Setter
public class Room {

    private String roomId;
    private String name;
    private List<String> inUsers;
    private int readyCount;
    private int agreeCount;
    private Map<String,List<String>> agreeVote;
    private int guiltyCount;
    private Map<String,List<String>> guiltyVote;


    public static Room create(String name) {
        Room gameRoom = new Room();
        gameRoom.roomId = UUID.randomUUID().toString();
        gameRoom.name = name;
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
        return gameRoom;
    }
}
