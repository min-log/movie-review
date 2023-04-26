package com.example.moviereview.controller;

import com.example.moviereview.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    // 업로드를 위한 설정 변수
    @Value("${org.zerock.upload.path}")// application.properties 의 변수
    private String uploadPath;

    // 이미지 업로드
    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){ //여러개의 이미지 파일 업로드

        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles){ // 각 파일을 검사

            //1 . 이미지 파일만 업로드 가능
            if(uploadFile.getContentType().startsWith("image") == false){
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }


            //2. 경로와 이름을 나눠야함.
            //실제 파일 이름 ie 나 edge는 전체 경로가 전달된다.
            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1 );
            log.info("fileName : " + fileName);

            //3. 날짜 폴더를 생성
            String folderPath = makeFolder();
            
            //UUID
            String uuid = UUID.randomUUID().toString();

            //저장할 파일 이름 중간에 _ 를 이용하여 구분
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);
            try{
                //실제 이미지 저장
                uploadFile.transferTo(savePath);
                //화면에 전달할 DTO 생성
                resultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));

                //썸네일 생성
                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
                File thumbnailFile = new File(thumbnailSaveName);
                //썸네일 저장
                Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,200,200);

            }catch ( IOException e){
                e.printStackTrace();
            }

        }
        return new ResponseEntity<>(resultDTOList,HttpStatus.OK);
    }


    // 날짜 폴더 생성
    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath,folderPath);
        if (uploadPathFolder.exists() == false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }


    // 화면에 업로드 이미지 출력
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName){
        ResponseEntity<byte[]> result = null;

            try{
                String srcFileName = URLDecoder.decode(fileName,"utf-8");
                log.info("fileName :" + fileName);

                File file = new File(uploadPath + File.separator + srcFileName);
                log.info("file : " + file);

                HttpHeaders headers = new HttpHeaders();

                //MIME타입 처리
                headers.add("Content-Type" , Files.probeContentType(file.toPath()));

                // 파일 데이터처리
                result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);


            }catch ( Exception e){
                log.error(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }



        return result;

    }




    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){
        String srcFileName = null;

        try {
            srcFileName = URLDecoder.decode(fileName,"utf-8");
            File file = new File(uploadPath+File.separator+srcFileName);
            boolean result = file.delete();

            File thumbnail = new File(file.getParent(),"s_"+file.getName());
            result = thumbnail.delete();

            return new ResponseEntity<>(result,HttpStatus.OK);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


