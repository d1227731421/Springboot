package com.demo.jpa;


import com.demo.pojo.Article;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
    List<Article> findByImgIsNull(Sort sort);
    List<Article> findByContentIsNull(Sort sort);
    List<Article> findByIdIsIn(List<Integer> ids);
    List<Article> findByContentIsNullAndTitleLike(Sort sort,String key);
    List<Article> findByImgIsNullAndTitleLike(Sort sort,String key);
 }
