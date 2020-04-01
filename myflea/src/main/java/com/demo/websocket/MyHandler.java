package com.demo.websocket;

import com.demo.jpa.UserRepository;
import com.demo.pojo.ChatRoom;
import com.demo.pojo.Message;
import com.demo.pojo.MessageCache;
import com.demo.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public  class MyHandler extends TextWebSocketHandler {
    @Autowired
    private UserRepository userRepository;
    private static Map<String , List<Map<String,WebSocketSession>>> roomSession=new HashMap<>();
    private static Map<String, List<MessageCache>> cacheMap=new HashMap<> ();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)throws Exception
    {

        String path=session.getUri().getRawPath().substring(4);
        String wxId= path.substring (path.lastIndexOf ("/")+1);
        ObjectMapper mapper = new ObjectMapper ();
        System.out.println ("message = " + message.getPayload ());
        Message  messageClass= mapper.readValue (message.getPayload ( ), Message.class);
        Map<String, WebSocketSession> socketSessionMap = roomSession.get (messageClass.getRoomId ( )).get (0);
        Set<String> strings = socketSessionMap.keySet ( );
        for (String string : strings) {
            if (!string.equals (wxId)){
                WebSocketSession webSocketSession = socketSessionMap.get (string);
                if (webSocketSession!=null){
                    Thread.sleep (500);
                    webSocketSession.sendMessage (message);
                }else {
                    List<MessageCache> messageCaches=null;
                    if(!cacheMap.containsKey (string)){
                        messageCaches=new ArrayList<> ();
                        cacheMap.put (string,messageCaches);
                    }
                    messageCaches = cacheMap.get (string);
                    List<MessageCache> collect = messageCaches.stream ( ).filter (item -> {
                        return item.getRoomId ( ).equals (messageClass.getRoomId ( ));
                    }).collect (Collectors.toList ( ));
                    if (collect.size ()>0){
                        collect.get (0).getMessageList ().add (messageClass);
                    }else {
                        MessageCache cache=new MessageCache ();
                        cache.setRoomId (messageClass.getRoomId ());
                        cache.getMessageList ().add (messageClass);
                        messageCaches.add (cache);
                    }
//                    messageCache.getMessageList ( ).add (mapper.readValue (message.getPayload (),Message.class));


                }
            }
        }
        System.out.println (roomSession );
    }
    @Override
    public  void afterConnectionEstablished(WebSocketSession session ) throws IOException, InterruptedException {
        if(session.getUri ()!=null){
            ObjectMapper objectMapper=new ObjectMapper ();
            String path=session.getUri().getRawPath().substring(4);
            String wxId= path.substring (path.lastIndexOf ("/")+1);
            HashMap<String, WebSocketSession> hashMap = new HashMap<> ( );
            hashMap.put (wxId,session);
            Optional<User> byId = userRepository.findById (Integer.parseInt (wxId));
            if(byId.isPresent ()){
                User user = byId.get ( );
                List<ChatRoom> chatRooms = user.getChatRooms ( );
                if (chatRooms.size ()>0){
                    for (ChatRoom chatRoom : chatRooms) {
                        String number = chatRoom.getNumber ( );
                        if(!roomSession.containsKey (number)){
                            List<Map<String,WebSocketSession>> mapList=new ArrayList<> ();
                            mapList.add (hashMap);
                            roomSession.put (chatRoom.getNumber (),mapList);
                        }else {
                            List<Map<String, WebSocketSession>> mapList = roomSession.get (chatRoom.getNumber ( ));
                            Map<String, WebSocketSession> socketSessionMap = mapList.get (0);
                             socketSessionMap.put (wxId,session);
                        }
                    }
                }
                if(cacheMap.containsKey (wxId)){
                    List<MessageCache> messageCaches = cacheMap.get (wxId);
                    if(messageCaches.size ()>0){
                        session.sendMessage (new TextMessage (objectMapper.writeValueAsBytes (messageCaches)));
                        cacheMap.remove (wxId);
                    }
                }else {
                    Message message = new Message ( );
                    message.setMessage ("初始化");
                    MessageCache mcache = new MessageCache ( );
                    mcache.getMessageList ().add (message);
                    List<MessageCache> init=new ArrayList<> ();
                    init.add (mcache);
                    session.sendMessage (new TextMessage (objectMapper.writeValueAsBytes (init)));
                }
            }

        }
        System.out.println (roomSession );
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String path=session.getUri().getRawPath().substring(4);
        String wxId= path.substring (path.lastIndexOf ("/")+1);
        Optional<User> byId = userRepository.findById (Integer.parseInt (wxId));
        if (byId.isPresent ()){
            User user = byId.get ( );
            List<ChatRoom> chatRooms = user.getChatRooms ( );
            for (ChatRoom chatRoom : chatRooms) {
                List<Map<String, WebSocketSession>> mapList = roomSession.get (chatRoom.getNumber ( ));
                Map<String, WebSocketSession> socketSessionMap = mapList.get (0);
                socketSessionMap.put (wxId,null);
            }
            log.debug ("",roomSession);
        }
    }
}
