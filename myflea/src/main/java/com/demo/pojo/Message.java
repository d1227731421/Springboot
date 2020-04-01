package com.demo.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
public class Message {
    private String id;
    private String roomId;
    private String message;
    private boolean back;
    private String avatarUrl;
   public Message(){

   }
    public Message(String id, String roomId, String message, boolean back, String avatarUrl) {
        this.id = id;
        this.roomId = roomId;
        this.message = message;
        this.back = back;
        this.avatarUrl = avatarUrl;
    }

    public Message(String roomId, String message) {
        this.id = UUID.randomUUID ().toString ();
        this.roomId = roomId;
        this.message = message;
        this.back=false;
    }
}
