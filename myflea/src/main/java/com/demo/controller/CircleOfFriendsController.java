package com.demo.controller;

import com.demo.pojo.CircleOfFriends;
import com.demo.service.CircleOfFriendsService;
import com.demo.uitl.QueryResponseResult;
import com.demo.uitl.UploadUtil;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RequestMapping("/cof")
public class CircleOfFriendsController {
    @Autowired
    private CircleOfFriendsService circleOfFriendsService;

    @Autowired
    private UploadUtil uploadUtil;

    @PostMapping("/save")
    public QueryResponseResult save(MultipartFile file, Integer userId, CircleOfFriends circleOfFriends) {
        String img = uploadUtil.uploadImgFull (file);
        circleOfFriends.setImg (img);
        circleOfFriends.setCreateTime (LocalDateTime.now ( ));
        return circleOfFriendsService.save (userId, circleOfFriends);
    }
    @GetMapping("/saveNoImg")
    public QueryResponseResult saveNoImg(Integer userId, CircleOfFriends circleOfFriends) {
        circleOfFriends.setCreateTime (LocalDateTime.now ( ));
        return circleOfFriendsService.save (userId, circleOfFriends);
    }

    @GetMapping("/delete")
    public QueryResponseResult delete(Integer id) {
        return circleOfFriendsService.delete (id);
    }

    @GetMapping("/query")
    public QueryResponseResult query(Integer userId) {
        return circleOfFriendsService.query (userId);
    }
}
