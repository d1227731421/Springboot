package com.demo.jpa;


import com.demo.pojo.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music,Integer> {
}
