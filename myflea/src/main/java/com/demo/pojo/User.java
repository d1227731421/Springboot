package com.demo.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.EAN;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "db_user")
@Setter
@Getter
@DynamicUpdate()
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username", unique = true, nullable = true, length = 24)
    private String userName;
    @Column(name = "password",  nullable = true, length = 16)
    private String password;
    @Column(name = "headportrait", nullable = true, length = 255)
    private String  headPortrait;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade=CascadeType.REMOVE,fetch=FetchType.LAZY)
    private List<CircleOfFriends> circleOfFriendsList=new ArrayList<> ();
    @OneToMany(mappedBy = "user",cascade=CascadeType.REMOVE,fetch=FetchType.EAGER)
    private List<ChatRoom> chatRooms=new ArrayList<> ();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", headPortrait='" + headPortrait + '\'' +
                ", circleOfFriendsList=" + circleOfFriendsList +
                '}';
    }
}
