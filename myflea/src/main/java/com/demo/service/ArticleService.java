package com.demo.service;

import com.demo.jpa.ArticleRepository;
import com.demo.pojo.Article;
import com.demo.uitl.CommonCode;
import com.demo.uitl.ImgUtil;
import com.demo.uitl.QueryResponseResult;
import com.demo.uitl.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ImgUtil imgUtil;
    public QueryResponseResult save(Article article){
        Article save = articleRepository.save (article);
        if(save.getImg ()!=null){
            String[] split = save.getImg ( ).split (",");
            save.setImg (imgUtil.setImg (split[0]));
        }
        List<Article> arrayList= new ArrayList<> ( );
        arrayList.add (save);
        return new QueryResponseResult(CommonCode.SUCCESS,new QueryResult<Article> (arrayList,0));
    }
    public QueryResponseResult delete(Integer id){
        articleRepository.deleteById (id);
        return new QueryResponseResult(CommonCode.SUCCESS,new QueryResult<Article> (new ArrayList<> ( ),0));
    }
    public QueryResponseResult findAllArticle(){
        Sort.Order order = Sort.Order.desc ("createTime");
        Sort sort = Sort.by (order);
        List<Article> all = articleRepository.findByImgIsNull ( sort);
        QueryResult<Article> result = new QueryResult<> (all,all.size ());
        return new QueryResponseResult (CommonCode.SUCCESS,result);
    }

    public QueryResponseResult findAllCartoon() {
        Sort.Order order = Sort.Order.desc ("createTime");
        Sort sort = Sort.by (order);
        List<Article> Cartoon = articleRepository.findByContentIsNull ( sort);
        Cartoon.forEach (article -> {
            String img = article.getImg ( );
            String[] split = img.split (",");
            List<String> stringList = Arrays.asList (split);
            article.setImg (imgUtil.setImg (stringList.get (0)));
        });
        QueryResult<Article> result = new QueryResult<> (Cartoon,Cartoon.size ());
        return new QueryResponseResult (CommonCode.SUCCESS,result);
    }

    public Article findCartoonById(Integer id) {
        Optional<Article> byId = articleRepository.findById (id);
        if(byId.isPresent ())
        {
            Article article = byId.get ( );
            String[] split = article.getImg ( ).split (",");
            List<String> stringList = new ArrayList<> ();
            Arrays.asList (split).forEach (s -> {
                s= imgUtil.setImg (s);

                stringList.add (s);
            });
            article.setImgList ( stringList);
           article.setImg (null);
            return article;
        }
        return null;
    }

    public QueryResponseResult findAllCartoonByKey(String key) {
        Sort.Order order = Sort.Order.desc ("createTime");
        Sort sort = Sort.by (order);
        List<Article> Cartoon = articleRepository.findByContentIsNullAndTitleLike ( sort,key);
        Cartoon.forEach (article -> {
            String img = article.getImg ( );
            String[] split = img.split (",");
            List<String> stringList = Arrays.asList (split);
            article.setImg (imgUtil.setImg (stringList.get (0)));
        });
        QueryResult<Article> result = new QueryResult<> (Cartoon,Cartoon.size ());
        return new QueryResponseResult (CommonCode.SUCCESS,result);
    }

    public QueryResponseResult findAllArticleByKey(String key) {
        Sort.Order order = Sort.Order.desc ("createTime");
        Sort sort = Sort.by (order);
        List<Article> all = articleRepository.findByImgIsNullAndTitleLike ( sort,key);
        QueryResult<Article> result = new QueryResult<> (all,all.size ());
        return new QueryResponseResult (CommonCode.SUCCESS,result);
    }
}
