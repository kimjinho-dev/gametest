package com.socket.server.controller;

import com.socket.server.model.Room;
import com.socket.server.repo.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/game")
public class RoomController {

        private final RoomRepository chatRoomRepository;

//        @GetMapping("/room")
//        public String rooms(Model model) {
//            return "/chat/room";
//        }

        @GetMapping("/rooms")
        @ResponseBody
        public List<Room> room() {
            return chatRoomRepository.findAllRoom();
        }

        @PostMapping("/room")
        @ResponseBody
        public Room createRoom(@RequestParam String name) {
            return chatRoomRepository.createChatRoom(name);
        }

//        @GetMapping("/room/enter/{roomId}")
//        public String roomDetail(Model model, @PathVariable String roomId) {
//            model.addAttribute("roomId", roomId);
//            return "/chat/roomdetail";
//        }

        @GetMapping("/room/{roomId}")
        @ResponseBody
        public Room roomInfo(@PathVariable String roomId) {
            return chatRoomRepository.findRoomById(roomId);
        }
}
