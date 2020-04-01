package com.demo.jpa;


import com.demo.pojo.CircleOfFriends;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CircleOfFriendsRepository extends JpaRepository<CircleOfFriends,Integer> {
 List<CircleOfFriends> queryCircleOfFriendsByUserId(Integer userId , Sort sort);
}
