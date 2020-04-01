package com.demo.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "db_music_table")
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class MusicTable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name" ,length = 16)
    private String name;
    @Column(name = "img" ,length = 255)
    private String img;
    @OneToMany(mappedBy = "musicTable", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Music> musicList;
}
