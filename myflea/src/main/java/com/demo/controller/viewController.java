package com.demo.controller;

import com.demo.jpa.ArticleRepository;
import com.demo.jpa.HistoryRepository;
import com.demo.pojo.Article;
import com.demo.pojo.History;
import com.demo.service.ArticleService;
import com.demo.uitl.ImgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller("/view")
public class viewController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ImgUtil imgUtil;
    @Autowired
    private HistoryRepository historyRepository;
    @RequestMapping("/show/{id}/{userId}")
    public String show (@PathVariable("id") Integer id,@PathVariable("userId") Integer userId, Model model){
        Optional<Article> byId = articleRepository.findById ( (id));
        if(byId.isPresent ()){
            Article article = byId.get ( );
            article.setClickNumber (article.getClickNumber ()+1);
            articleRepository.save (article);
            saveHistory(id,userId);
            model.addAttribute ("article",article.getContent ());
        }
        return "templates";
    }
    @RequestMapping("/cartoon/{id}/{userId}")
    public String showCartoon (@PathVariable("id") Integer id,@PathVariable("userId") Integer userId, Model model){
        Optional<Article> byId = articleRepository.findById ( (id));
        if(byId.isPresent ()){
            Article article = byId.get ( );
            String[] split = article.getImg ( ).split (",");
            List<String> stringList = new ArrayList<> ();
            saveHistory(id,userId);
            Arrays.asList (split).forEach (s -> {
                s= imgUtil.setImg (s);
                stringList.add (s);
            });
            model.addAttribute ("cartoon",stringList);
        }
        return "cartoon";
    }
    public void saveHistory(Integer id,Integer userId){
        History history = null;
        History find= historyRepository.findHistoryByArticleIdAndAndUserId ((id), (userId));
        if(find!=null){
            history=find;
        }else{
            history=new History ();
        }
        history.setArticleId ( (id));
        history.setUserId ( (userId));
        history.setCreateTime (LocalDateTime.now ());
        historyRepository.save (history);
    }
}
