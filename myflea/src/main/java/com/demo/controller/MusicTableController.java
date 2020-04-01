package com.demo.controller;

import com.demo.pojo.MusicTable;
import com.demo.service.MusicTableService;
import com.demo.uitl.ImgUtil;
import com.demo.uitl.QueryResponseResult;
import com.demo.uitl.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tableMusic")
@CrossOrigin(origins = "http://localhost:8080",maxAge = 3600)
public class MusicTableController {
    @Autowired
    private UploadUtil uploadUtil;
    @Autowired
    private MusicTableService musicTableService;
    @Autowired
    private ImgUtil imgUtil;
    @GetMapping("/save")
    public QueryResponseResult save( MusicTable musicTable){
        musicTable.setImg (musicTable.getImg ().substring (musicTable.getImg ().indexOf ("group1")));
        return musicTableService.save (musicTable);
    }
    @GetMapping("/find")
    public QueryResponseResult findAll( ){
        return musicTableService.findAll ();
    }
    @GetMapping("/find/key")
    public QueryResponseResult findKey(String key ){
        key="%"+key+"%";
        return musicTableService.findKey (key);
    }
    @GetMapping("/delete")
    public QueryResponseResult delete( Integer id){
        return musicTableService.delete (id);
    }
    @GetMapping("/findById")
    public QueryResponseResult findById(Integer id){
        return musicTableService.findById (id);
    }
}
