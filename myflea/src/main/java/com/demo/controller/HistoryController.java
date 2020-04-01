package com.demo.controller;

import com.demo.pojo.Article;
import com.demo.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;
    @GetMapping("/find")
    public List<Article> findHistory(Integer userId){
        return historyService.queryArticle (userId);
    }
}
