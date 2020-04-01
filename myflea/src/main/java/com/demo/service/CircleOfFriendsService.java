package com.demo.service;

import com.demo.jpa.CircleOfFriendsRepository;
import com.demo.pojo.CircleOfFriends;
import com.demo.pojo.User;
import com.demo.uitl.CommonCode;
import com.demo.uitl.ImgUtil;
import com.demo.uitl.QueryResponseResult;
import com.demo.uitl.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CircleOfFriendsService {
    @Autowired
    private CircleOfFriendsRepository circleOfFriendsRepository;
    @Autowired
    private ImgUtil imgUtil;

    public QueryResponseResult save(Integer userId, CircleOfFriends circleOfFriends) {
        User user = new User ( );
        user.setId (userId);
        circleOfFriends.setUser (user);
        CircleOfFriends save = circleOfFriendsRepository.save (circleOfFriends);
        return new QueryResponseResult (CommonCode.SUCCESS, new QueryResult<> (Arrays.asList (save), 0));
    }

    public QueryResponseResult delete(Integer id) {
        circleOfFriendsRepository.deleteById (id);
        return new QueryResponseResult (CommonCode.SUCCESS, null);
    }

    public QueryResponseResult query(Integer userId) {
        Sort.Order order = Sort.Order.desc ("createTime");
        Sort sort = Sort.by (order);
        List<CircleOfFriends> all = circleOfFriendsRepository.queryCircleOfFriendsByUserId (userId,sort);
        all.forEach (circleOfFriends -> {
            circleOfFriends.setImg (imgUtil.setImg (circleOfFriends.getImg ()));
        });
        return new QueryResponseResult (CommonCode.SUCCESS, new QueryResult<CircleOfFriends> (all, all.size ( )));
    }
}
