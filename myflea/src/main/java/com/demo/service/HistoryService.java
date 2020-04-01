package com.demo.service;

import com.demo.jpa.ArticleRepository;
import com.demo.jpa.HistoryRepository;
import com.demo.pojo.Article;
import com.demo.pojo.History;
import com.demo.uitl.ImgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ImgUtil imgUtil;
    public List<Article> queryArticle(Integer userId){
        Sort.Order order = Sort.Order.desc ("createTime");
        Sort sort = Sort.by (order);
        List<History> historyByUserId = historyRepository.findHistoryByUserId (userId,sort);
        List<Integer> collect = historyByUserId.stream ( ).map (History::getArticleId).collect (Collectors.toList ( ));
        List<Article> byIdIsIn = articleRepository.findByIdIsIn (collect);
        byIdIsIn.forEach (e->{
            e.setContent ("");
            if(e.getImg ()!=null)
            {
                String[] split = e.getImg ( ).split (",");
                e.setImg (imgUtil.setImg (split[0]));
            }
        });
        return byIdIsIn;
    }
}
