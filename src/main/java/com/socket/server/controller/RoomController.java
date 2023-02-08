package com.socket.server.controller;

import com.socket.server.model.Room;
import com.socket.server.repo.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import com.socket.server.model.RoomDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/game")
public class RoomController {

        private final RoomRepository chatRoomRepository;


        @GetMapping("/rooms")
        @ResponseBody
        public List<RoomDto> room() {
            return chatRoomRepository.findAllRoom();
        }

        @PostMapping("/room")
        @ResponseBody
        public Room createRoom(@RequestParam String title, String password) {
            return chatRoomRepository.createChatRoom(title,password);
        }

        @GetMapping("/room/{roomId}")
        @ResponseBody
        public Room roomInfo(@PathVariable String roomId) {
            return chatRoomRepository.findRoomById(roomId);
        }
}
