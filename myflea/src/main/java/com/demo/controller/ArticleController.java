package com.demo.controller;

import com.demo.pojo.Article;
import com.demo.service.ArticleService;
import com.demo.uitl.QueryResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/article")
@CrossOrigin(origins = "http://localhost:8080",maxAge = 3600)
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/save")
    public QueryResponseResult save(Article article){
        if (article.getClickNumber ()==null){
            article.setClickNumber (0);
        }
        if (article.getCreateTime ()==null){
            article.setCreateTime (LocalDateTime.now ());
        }
        article.setContent (article.getContent ().replace("\t","&emsp;"));
        return articleService.save (article);
    }
    @PostMapping("/saveCartoon")
    public QueryResponseResult saveCartoon(Article article){
        System.out.println ("article = " + article);
        if (article.getCreateTime ()==null){
            article.setCreateTime (LocalDateTime.now ());
        }
//        if (article.getClickNumber ()==null){
//            article.setClickNumber (0);
//        }

        return articleService.save (article);
    }
    @GetMapping("/delete")
    public QueryResponseResult delete(Integer id){
        return articleService.delete (id);
    }
    @GetMapping("/findAllArticle")
    public QueryResponseResult findAllArticle(){
        return articleService.findAllArticle ();
    }
    @GetMapping("/findAllCartoon")
    public QueryResponseResult findAllCartoon(){
        return articleService.findAllCartoon ();
    }
    @GetMapping("/findCartoonById")
    public Article findCartoonById(Integer id){
        return articleService.findCartoonById (id);
    }
    @GetMapping("/findAllArticle/key")
    public QueryResponseResult findAllArticleByKey(String key){
        key="%"+key+"%";
        return articleService.findAllArticleByKey (key);
    }
    @GetMapping("/findAllCartoon/key")
    public QueryResponseResult findAllCartoonByKey(String key){
        key="%"+key+"%";
        return articleService.findAllCartoonByKey (key);
    }
}
