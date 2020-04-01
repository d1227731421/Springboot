package com.demo.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "db_chat_room")
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "number", nullable = true, length = 255)
    private String number;
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Column(name = "img", nullable = true, length = 255)
    private String img;
    @JsonIgnore
    @ManyToOne(optional=false,fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
}
