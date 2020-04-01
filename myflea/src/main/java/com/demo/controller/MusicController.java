package com.demo.controller;

import com.demo.pojo.Music;
import com.demo.service.MusicSevice;
import com.demo.uitl.QueryResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/music")
@CrossOrigin(origins = "http://localhost:8080",maxAge = 3600)
public class MusicController {
    @Autowired
    private MusicSevice musicSevice;
    @GetMapping("/save")
    public QueryResponseResult save(Integer tableId , Music music){
        return musicSevice.save (tableId,music);
    }
    @GetMapping("/delete")
    public QueryResponseResult delete(Integer id){
        return musicSevice.delete (id);
    }
}
