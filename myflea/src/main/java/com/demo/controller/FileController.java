package com.demo.controller;

import com.demo.uitl.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "http://localhost:8080",maxAge = 3600)
public class FileController {
    @Autowired
    private UploadUtil uploadUtil;

    @PostMapping("/img")
    public String postImg(MultipartFile file){
        return uploadUtil.uploadImgFull  (file);
    }
    @PostMapping("/mp3")
    public String postFile(MultipartFile file){
        return uploadUtil.uploadMusic (file);
    }

    @PostMapping("/litImg")
    public String postIitImg(MultipartFile file){
        return uploadUtil.uploadImg (file);
    }

}
