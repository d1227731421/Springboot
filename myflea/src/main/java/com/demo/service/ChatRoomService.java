package com.demo.service;

import com.demo.jpa.ChatRoomRepository;
import com.demo.pojo.ChatRoom;
import com.demo.uitl.CommonCode;
import com.demo.uitl.ImgUtil;
import com.demo.uitl.QueryResponseResult;
import com.demo.uitl.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ImgUtil imgUtil;
    public QueryResponseResult findChat(String number){
        ChatRoom chatRoom = chatRoomRepository.queryTopByNumberEquals (number);
        if(chatRoom!=null)
        {
            chatRoom.setImg (imgUtil.setImg (chatRoom.getImg ()));
            List<ChatRoom> chatRooms = Arrays.asList (chatRoom);
            return new QueryResponseResult (CommonCode.SUCCESS,new QueryResult<ChatRoom> (chatRooms,chatRooms.size ()));
        }
        return new QueryResponseResult (CommonCode.FAIL,null);
    }

    public QueryResponseResult create(ChatRoom chatRoom) {
        String img = chatRoom.getImg ( );
        String substring = img.substring (img.indexOf ("group1"));
        chatRoom.setImg (substring);
        ChatRoom save = chatRoomRepository.save (chatRoom);
        if(save!=null){
            chatRoom.setImg (imgUtil.setImg (chatRoom.getImg ()));
            List<ChatRoom> chatRooms = Arrays.asList (save);
            return new QueryResponseResult (CommonCode.SUCCESS,new QueryResult<ChatRoom> (chatRooms,chatRooms.size ()));
        }
        return new QueryResponseResult (CommonCode.FAIL,null);
    }
}
