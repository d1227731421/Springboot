package com.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "db_article")
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title",length = 255,nullable = true)
    private String title;
    @Column(name = "article_from",length = 255,nullable = true)
    private String from;
    @Column(name = "content",columnDefinition = "text",nullable = true)
    private String content;
    @Column(name = "img",columnDefinition = "text",nullable = true)
    private String img;
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @Column(name = "click_number",length = 16,nullable = true)
    private Integer clickNumber;
    @Transient
    private List<String > imgList;
}
