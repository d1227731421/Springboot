package com.demo.service;

import com.demo.jpa.MusicRepository;
import com.demo.jpa.MusicTableRepository;
import com.demo.pojo.Music;
import com.demo.pojo.MusicTable;
import com.demo.uitl.CommonCode;
import com.demo.uitl.ImgUtil;
import com.demo.uitl.QueryResponseResult;
import com.demo.uitl.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MusicTableService {
    @Autowired
    private MusicTableRepository musicTableRepository;
    @Autowired
    private ImgUtil imgUtil;
    public QueryResponseResult save(MusicTable musicTable){
        MusicTable save = musicTableRepository.save (musicTable);
        List<MusicTable> musicTables = Arrays.asList (save);
        return new QueryResponseResult (CommonCode.SUCCESS,new QueryResult<> (musicTables,musicTables.size ()));
    }
    public QueryResponseResult findAll(){
        List<MusicTable> all = musicTableRepository.findAll ( );
        all.forEach (musicTable -> {
            List<Music> musicList = musicTable.getMusicList ( );
            musicList.forEach (item->{
                item.setUrl (imgUtil.setImg (item.getUrl ()));
            });
            musicTable.setImg (imgUtil.setImg (musicTable.getImg ()));
        });
        return new QueryResponseResult (CommonCode.SUCCESS,new QueryResult<> (all,all.size ()));
    }
    public QueryResponseResult delete(Integer id){
        musicTableRepository.deleteById (id);
        return new QueryResponseResult (CommonCode.SUCCESS,null);
    }

    public QueryResponseResult findById(Integer id) {
        Optional<MusicTable> byId = musicTableRepository.findById (id);
        if(byId.isPresent ()){
            MusicTable musicTable = byId.get ( );
            musicTable.setImg (imgUtil.setImg (musicTable.getImg ()));
            List<Music> musicList = musicTable.getMusicList ( );
            musicList.forEach (item->{
                item.setUrl (imgUtil.setImg (item.getUrl ()));
            });
            List<MusicTable> musicTables = Arrays.asList (musicTable);
            return new QueryResponseResult (CommonCode.SUCCESS,new QueryResult<> (musicTables,musicTables.size ()));
        }
        return null;
    }

    public QueryResponseResult findKey(String key) {
        List<MusicTable> all = musicTableRepository.findMusicTableByNameLike ( key);
        all.forEach (musicTable -> {
            List<Music> musicList = musicTable.getMusicList ( );
            musicList.forEach (item->{
                item.setUrl (imgUtil.setImg (item.getUrl ()));
            });
            musicTable.setImg (imgUtil.setImg (musicTable.getImg ()));
        });
        return new QueryResponseResult (CommonCode.SUCCESS,new QueryResult<> (all,all.size ()));
    }
}
