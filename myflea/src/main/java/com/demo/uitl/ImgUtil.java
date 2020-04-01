package com.demo.uitl;

import com.github.tobato.fastdfs.conn.TrackerConnectionManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImgUtil {
    @Autowired
    private TrackerConnectionManager trackerConnectionManager;

    public String setImg(String url){
        int index = StringUtils.indexOf (url, "group1");
        if(index!=StringUtils.INDEX_NOT_FOUND){
            String ip= getUrl ( );
            return ip+url;
        }
        return url;
    }
    private String getUrl(){
        String url=trackerConnectionManager.getTrackerList ().get (0);
        url="http://"+url.substring (0,url.lastIndexOf (":"))+"/";
        return url;
    }
}
