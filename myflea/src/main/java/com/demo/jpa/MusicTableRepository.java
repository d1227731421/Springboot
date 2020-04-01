package com.demo.jpa;


import com.demo.pojo.MusicTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicTableRepository extends JpaRepository<MusicTable,Integer> {
    List<MusicTable> findMusicTableByNameLike(String key);
}
