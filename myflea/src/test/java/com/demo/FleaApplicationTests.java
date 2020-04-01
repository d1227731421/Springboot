package com.demo;

import com.demo.jpa.CircleOfFriendsRepository;
import com.demo.jpa.UserRepository;
import com.demo.pojo.CircleOfFriends;
import com.demo.pojo.User;
import com.demo.service.CircleOfFriendsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FleaApplicationTests {

    @Autowired
    private CircleOfFriendsService circleOfFriendsService;
    @Test
    public void contextLoads() {
        String str="http://172.18.0.3/group1/M00/00/02/rBIAA150cqeAEED5ACki0gnP8aM90.jpeg";
        String substring = str.substring (str.indexOf ("group1"));
        System.out.println ("substring = " + substring);
    }

}
