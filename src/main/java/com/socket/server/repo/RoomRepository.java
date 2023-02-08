package com.socket.server.repo;

import com.socket.server.model.Room;
import com.socket.server.model.RoomDto;
import com.socket.server.model.ServerMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import com.socket.server.repo.BroadcastRepository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Repository
public class RoomRepository  {

    private Map<String, Room> chatRoomMap;
    private List<RoomDto> roomList;
    private final BroadcastRepository broadcastRepository;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<RoomDto> findAllRoom() {
        roomList = new ArrayList<>();
        for ( String key : chatRoomMap.keySet() ) {
            RoomDto roomDto = new RoomDto();
            roomDto.setRoomId(chatRoomMap.get(key).getRoomId());
            roomDto.setTitle(chatRoomMap.get(key).getTitle());
            roomDto.setUserCount(chatRoomMap.get(key).getInUsers().size());
            roomDto.setRoomNum(chatRoomMap.get(key).getRoomNum());
            roomDto.setLock(!chatRoomMap.get(key).getPassword().equals(""));
            roomList.add(roomDto);
        }
        return roomList;
    }

    public Room findRoomById(String id) {
        return chatRoomMap.get(id);
    }

    public Room createChatRoom(String title, String password) {
        Room room = Room.create(title,password);
        List<Integer> allRoomNums = chatRoomMap.values().stream().map(Room::getRoomNum).collect(Collectors.toList());;
        List<Integer> roomNumList = IntStream.range(1, 101)
                .boxed()
                .collect(Collectors.toList());
        roomNumList.removeAll(allRoomNums);
        Random rand = new Random();
        room.setRoomNum(roomNumList.get(rand.nextInt(roomNumList.size())));
        chatRoomMap.put(room.getRoomId(),room);
        return room;
    }

    public void setUserEnterInfo(String roomId, String userId) {
        chatRoomMap.get(roomId).getInUsers().add(userId);
    }

    // 룸Id값으로 입장해 있는 채팅방 유저리스트 조회
    public List<String> getUserEnterRoomId(String roomId) {
        return chatRoomMap.get(roomId).getInUsers();
    }

    // 룸Id값이랑 맵핑된 유저 리스트에서 삭제
    public void removeUserEnterInfo(String roomId, String userid) {
        chatRoomMap.get(roomId).getInUsers().remove(userid);
    }

    public void setUserReadyInfo(String roomId, String userId) {
        int c = chatRoomMap.get(roomId).getReadyCount() + 1;
        chatRoomMap.get(roomId).setReadyCount(c);
        if (chatRoomMap.get(roomId).getReadyCount() == 5) {
            ServerMessage serverMessage = new ServerMessage();
            serverMessage.setType(ServerMessage.MessageType.ALL_READY);
            serverMessage.setRoomId(roomId);
            serverMessage.setSender("공지");
            serverMessage.setMessage("모든 유저 준비가 완료되었습니다");
//            messagingTemplate.convertAndSend("/sub/message/user/" + serverMessage.getRoomId(),serverMessage);
            broadcastRepository.serverMessage(serverMessage);
            chatRoomMap.get(roomId).setReadyCount(0);
        }
    }

    public void setAgreeInfo(String roomId, String userId) {
        chatRoomMap.get(roomId).getAgreeVote().get("찬성").add(userId);
        chatRoomMap.get(roomId).setAgreeCount(chatRoomMap.get(roomId).getAgreeCount() + 1);
        if (chatRoomMap.get(roomId).getAgreeCount() == 6) {
            ServerMessage serverMessage = new ServerMessage();
            serverMessage.setType(ServerMessage.MessageType.AGREE_RESULT);
            serverMessage.setRoomId(roomId);
            serverMessage.setSender("공지");
            serverMessage.setMessage(chatRoomMap.get(roomId).getAgreeVote().toString());
//            messagingTemplate.convertAndSend("/sub/message/user/" + roomId, serverMessage);
            broadcastRepository.serverMessage(serverMessage);
            chatRoomMap.get(roomId).setAgreeCount(0);
            chatRoomMap.get(roomId).setAgreeVote(new HashMap<String,List<String>>());
            chatRoomMap.get(roomId).getAgreeVote().put("찬성",new ArrayList<>());
            chatRoomMap.get(roomId).getAgreeVote().put("반대",new ArrayList<>());
        }
    }

    public void setDisagreeInfo(String roomId, String userId) {
        chatRoomMap.get(roomId).getAgreeVote().get("반대").add(userId);
        chatRoomMap.get(roomId).setAgreeCount(chatRoomMap.get(roomId).getAgreeCount() + 1);
        if (chatRoomMap.get(roomId).getAgreeCount() == 6) {
            ServerMessage serverMessage = new ServerMessage();
            serverMessage.setType(ServerMessage.MessageType.AGREE_RESULT);
            serverMessage.setRoomId(roomId);
            serverMessage.setSender("공지");
            serverMessage.setMessage(chatRoomMap.get(roomId).getAgreeVote().toString());
//            messagingTemplate.convertAndSend("/sub/message/user/" + serverMessage.getRoomId(),serverMessage);
            broadcastRepository.serverMessage(serverMessage);
            chatRoomMap.get(roomId).setAgreeCount(0);
            chatRoomMap.get(roomId).setAgreeVote(new HashMap<String,List<String>>());
            chatRoomMap.get(roomId).getAgreeVote().put("찬성",new ArrayList<>());
            chatRoomMap.get(roomId).getAgreeVote().put("반대",new ArrayList<>());
        }
    }

    public void setGuiltyInfo(String roomId, String userId) {
        chatRoomMap.get(roomId).getGuiltyVote().get("유죄").add(userId);
        chatRoomMap.get(roomId).setGuiltyCount(chatRoomMap.get(roomId).getGuiltyCount() + 1);
        if (chatRoomMap.get(roomId).getGuiltyCount() == 2) {
            ServerMessage serverMessage = new ServerMessage();
            serverMessage.setType(ServerMessage.MessageType.GUILTY_RESULT);
            serverMessage.setRoomId(roomId);
            serverMessage.setSender("공지");
            serverMessage.setMessage(chatRoomMap.get(roomId).getGuiltyVote().toString());
//            messagingTemplate.convertAndSend("/sub/message/user/" + serverMessage.getRoomId(),serverMessage);
            broadcastRepository.serverMessage(serverMessage);
            chatRoomMap.get(roomId).setGuiltyCount(0);
            chatRoomMap.get(roomId).getGuiltyVote().put("유죄",new ArrayList<>());
            chatRoomMap.get(roomId).getGuiltyVote().put("무죄",new ArrayList<>());
        }
    }

    public void setNotguiltyInfo(String roomId, String userId) {
        chatRoomMap.get(roomId).getGuiltyVote().get("무죄").add(userId);
        chatRoomMap.get(roomId).setGuiltyCount(chatRoomMap.get(roomId).getGuiltyCount() + 1);
        if (chatRoomMap.get(roomId).getGuiltyCount() == 2) {
            ServerMessage serverMessage = new ServerMessage();
            serverMessage.setType(ServerMessage.MessageType.GUILTY_RESULT);
            serverMessage.setRoomId(roomId);
            serverMessage.setSender("공지");
            serverMessage.setMessage(chatRoomMap.get(roomId).getGuiltyVote().toString());
//            messagingTemplate.convertAndSend("/sub/message/user/" + serverMessage.getRoomId(),serverMessage);
            broadcastRepository.serverMessage(serverMessage);
            chatRoomMap.get(roomId).setGuiltyCount(0);
            chatRoomMap.get(roomId).setGuiltyVote(new HashMap<String,List<String>>());
            chatRoomMap.get(roomId).getGuiltyVote().put("유죄",new ArrayList<>());
            chatRoomMap.get(roomId).getGuiltyVote().put("무죄",new ArrayList<>());
        }
    }
}