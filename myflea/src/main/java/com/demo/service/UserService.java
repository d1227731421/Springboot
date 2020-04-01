package com.demo.service;

import com.demo.jpa.UserRepository;
import com.demo.pojo.ChatRoom;
import com.demo.pojo.User;
import com.demo.uitl.CommonCode;
import com.demo.uitl.ImgUtil;
import com.demo.uitl.QueryResponseResult;
import com.demo.uitl.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImgUtil imgUtil;

    public QueryResponseResult saveAndUpdate(User user) {
        String group1 = user.getHeadPortrait ( ).substring (user.getHeadPortrait ( ).indexOf ("group1"));
        user.setHeadPortrait (group1);
        User save = userRepository.save (user);
        QueryResult<User> queryResult = new QueryResult<> ( );
        if (save != null) {
            save.setHeadPortrait (imgUtil.setImg (save.getHeadPortrait ( )));
            queryResult.getList ( ).add (save);
            return new QueryResponseResult (CommonCode.SUCCESS, queryResult);
        }
        return new QueryResponseResult (CommonCode.FAIL, queryResult);
    }

    public QueryResponseResult delete(Integer id) {
        userRepository.deleteById (id);
        return new QueryResponseResult (CommonCode.SUCCESS, new QueryResult ( ));
    }

    public QueryResponseResult findAll() {
        List<User> all = userRepository.findAll ( );
        all.forEach (user -> {
            user.setHeadPortrait (imgUtil.setImg (user.getHeadPortrait ( )));
        });
        return new QueryResponseResult (CommonCode.SUCCESS, new QueryResult<User> (all, all.size ( )));
    }

    public QueryResponseResult login(String username, String password) {
        User user = userRepository.findUserByUserNameAndPassword (username, password);
        if (user != null) {
            user.setHeadPortrait (imgUtil.setImg (user.getHeadPortrait ( )));
            List<ChatRoom> chatRooms = user.getChatRooms ( );
            chatRooms.forEach (chatRoom -> {
                chatRoom.setImg (imgUtil.setImg (chatRoom.getImg ( )));
            });
            List<User> userList = Arrays.asList (user);
            return new QueryResponseResult (CommonCode.SUCCESS, new QueryResult<> (userList, 1));
        } else {
            return new QueryResponseResult (CommonCode.FAIL, null);
        }
    }

    public QueryResponseResult queryById(Integer id) {
        Optional<User> byId = userRepository.findById (id);
        if (byId.isPresent ( )) {
            User user = byId.get ( );
            user.setHeadPortrait (imgUtil.setImg (user.getHeadPortrait ( )));
            List<ChatRoom> chatRooms = user.getChatRooms ( );
            chatRooms.forEach (chatRoom -> {
                chatRoom.setImg (imgUtil.setImg (chatRoom.getImg ( )));
            });
            List<User> userList = Arrays.asList (user);
            return new QueryResponseResult (CommonCode.SUCCESS, new QueryResult<> (userList, 1));
        }
        return new QueryResponseResult (CommonCode.FAIL, null);
    }

    public QueryResponseResult sureName(String userName) {
        boolean exists = userRepository.existsByUserName (userName);
        if (exists) {
            return new QueryResponseResult (CommonCode.FAIL, null);
        }
        return new QueryResponseResult (CommonCode.SUCCESS, null);
    }

    public QueryResponseResult sureNameById(Integer id, String userName) {
        User user = userRepository.findUserByUserName (userName);
        if (user != null) {
            if (user.getId ( ).equals (id)) {
                return new QueryResponseResult (CommonCode.SUCCESS, null);
            }
            return new QueryResponseResult (CommonCode.FAIL, null);
        }

        return new QueryResponseResult (CommonCode.SUCCESS, null);
    }

    public QueryResponseResult findAllKey(String key) {
        List<User> all = userRepository.findUserByUserNameIsLike (key );
        all.forEach (user -> {
            user.setHeadPortrait (imgUtil.setImg (user.getHeadPortrait ( )));
        });
        return new QueryResponseResult (CommonCode.SUCCESS, new QueryResult<User> (all, all.size ( )));
    }
}
