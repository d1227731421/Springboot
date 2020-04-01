package com.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "db_music")
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class Music  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = true, length = 64)
    private String name;
    @Column(name = "url", nullable = true, length = 128)
    private String url;
    @ManyToOne(optional=false)
    @JsonIgnore
    @JoinColumn(name="muisic_table_id")
    private MusicTable musicTable;
}
