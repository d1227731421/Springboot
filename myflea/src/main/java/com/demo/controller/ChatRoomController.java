package com.demo.controller;

import com.demo.pojo.ChatRoom;
import com.demo.pojo.User;
import com.demo.service.ChatRoomService;
import com.demo.uitl.QueryResponseResult;
import com.demo.uitl.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:8080",maxAge = 3600)
public class ChatRoomController {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private UploadUtil uploadUtil;
    @GetMapping("/query")
    public QueryResponseResult query(String number){
        return chatRoomService.findChat (number);
    }
    @PostMapping("/create")
    public QueryResponseResult create(MultipartFile file,Integer userid, ChatRoom chatRoom){
        User user=new User ();
        user.setId (userid);
        chatRoom.setUser (user);
        String img = uploadUtil.uploadImg (file);
        chatRoom.setImg (img);
        return chatRoomService.create(chatRoom);
    }
    @GetMapping("/join")
    public QueryResponseResult join(Integer userid,ChatRoom chatRoom){
        User user=new User ();
        user.setId (userid);
        chatRoom.setUser (user);
        return chatRoomService.create(chatRoom);
    }
}
