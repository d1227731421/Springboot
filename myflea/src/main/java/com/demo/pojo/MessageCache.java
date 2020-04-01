package com.demo.pojo;


import lombok.Data;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;
@Data
public class MessageCache {
     private  String roomId;
    private List<Message> messageList=new ArrayList<> ();

}
