package com.demo.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "db_circle_of_friends")
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CircleOfFriends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "content",columnDefinition = "text",nullable = true)
    private  String  content;
    @Column(name = "img",length = 1024,nullable = true)
    private String img;
    @Transient
    private List<String> imgList;
    @Column(name = "create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private  LocalDateTime createTime;
    @JsonIgnore
    @ManyToOne(optional=false,fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Override
    public String toString() {
        return "CircleOfFriends{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", img='" + img + '\'' +
                ", imgList=" + imgList +
                ", createTime=" + createTime +
                '}';
    }
}
