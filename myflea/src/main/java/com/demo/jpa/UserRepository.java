package com.demo.jpa;


import com.demo.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUserNameAndPassword(String username,String password);
    Optional<User> findById(Integer id);
    boolean existsByUserName(String userName);
    User findUserByUserName(String userName);
    List<User> findUserByUserNameIsLike(String key);

}
