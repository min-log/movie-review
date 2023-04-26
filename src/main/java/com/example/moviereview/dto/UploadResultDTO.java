package com.example.moviereview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {
    private String fileName;
    private String uuid;
    private String folderPath;

    // 화면에서 가져올 원본 이미지
    public String getImageURL(){
        try {
            return URLEncoder.encode(folderPath + "/" +uuid+"_"+fileName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    // 화면에서 가져올  썸네일이미지
    public String getThumbnailURL(){
        try{
            return URLEncoder.encode(folderPath + "/s_" + uuid + "_" +fileName,"utf-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }

}
