package com.demo.jpa;


import com.demo.pojo.History;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History,Integer> {
    History findHistoryByArticleIdAndAndUserId(Integer articleId,Integer userId);
    List<History> findHistoryByUserId(Integer userId, Sort sort);
}
