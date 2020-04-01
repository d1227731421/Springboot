package com.demo.uitl;

import com.github.tobato.fastdfs.conn.TrackerConnectionManager;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
public class UploadUtil {
    @Autowired
    private  FastFileStorageClient storageClient;
    @Autowired
    private  ThumbImageConfig thumbImageConfig;

    public  String uploadImg(MultipartFile multipartFile) {
        try {
            String type = multipartFile.getContentType ( ).substring (multipartFile.getContentType ( ).lastIndexOf ("/") + 1);
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage (multipartFile.getInputStream (),multipartFile.getSize (),type,null);
            String thumbImagePath = thumbImageConfig.getThumbImagePath (storePath.getFullPath ( ));
            return thumbImagePath;
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        return null;
    }
    public  String uploadImgFull(MultipartFile multipartFile) {
        try {
            String type = multipartFile.getContentType ( ).substring (multipartFile.getContentType ( ).lastIndexOf ("/") + 1);
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage (multipartFile.getInputStream (),multipartFile.getSize (),type,null);
            return storePath.getFullPath ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        return null;
    }
    public  String uploadMusic(MultipartFile multipartFile) {
        try {
            String type = multipartFile.getContentType ( ).substring (multipartFile.getContentType ( ).lastIndexOf ("/") + 1);
            StorePath storePath = storageClient.uploadFile (multipartFile.getInputStream (),multipartFile.getSize (),type,null);
            return storePath.getFullPath ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        return null;
    }

}
