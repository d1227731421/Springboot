package com.demo.service;

import com.demo.jpa.MusicRepository;
import com.demo.pojo.Music;
import com.demo.pojo.MusicTable;
import com.demo.uitl.CommonCode;
import com.demo.uitl.QueryResponseResult;
import com.demo.uitl.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MusicSevice {
    @Autowired
    private MusicRepository musicRepository;

    public QueryResponseResult save(Integer tableId, Music music){
        MusicTable musicTable=new MusicTable ();
        musicTable.setId (tableId);
        music.setMusicTable (musicTable);
        Music save = musicRepository.save (music);
        List<Music> musicList = Arrays.asList (save);
        return new QueryResponseResult (CommonCode.SUCCESS,new QueryResult<> (musicList,musicList.size ()));
    }

    public QueryResponseResult delete(Integer id){
        musicRepository.deleteById (id);
        return new QueryResponseResult (CommonCode.SUCCESS,null);
    }
}
