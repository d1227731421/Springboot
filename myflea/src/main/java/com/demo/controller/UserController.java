package com.demo.controller;

import com.demo.pojo.User;
import com.demo.service.UserService;
import com.demo.uitl.QueryResponseResult;
import com.demo.uitl.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8080",maxAge = 3600)
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UploadUtil uploadUtil;
    @GetMapping ("/save")
    public QueryResponseResult save(User user){
        return userService.saveAndUpdate (user);
    }

    @GetMapping("/delete")
    public QueryResponseResult delete(Integer id){
        return userService.delete (id);
    }
    @GetMapping("/find")
    public QueryResponseResult findAll(){
        return userService.findAll ();
    }
    @GetMapping("/find/key")
    public QueryResponseResult findAllKey(String key){
        key="%"+key+"%";
        return userService.findAllKey (key);
    }
    @GetMapping("/login")
    public QueryResponseResult login(String username,String password){
        return userService.login (username, password);
    }
    @GetMapping("/findbyid")
    public QueryResponseResult queryById(Integer id){
        return userService.queryById (id);
    }
    @GetMapping("sureName")
    QueryResponseResult sureName(String userName){
        return userService.sureName (userName);
    }
    @GetMapping("sureNameById")
    QueryResponseResult sureNameById(Integer id,String userName){
        return userService.sureNameById (id,userName);
    }

}
