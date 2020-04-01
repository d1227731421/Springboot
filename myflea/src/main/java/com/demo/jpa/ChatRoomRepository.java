package com.demo.jpa;


import com.demo.pojo.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Integer> {
    ChatRoom queryTopByNumberEquals(String number);
}
