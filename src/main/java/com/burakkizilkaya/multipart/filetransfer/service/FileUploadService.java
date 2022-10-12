package com.burakkizilkaya.multipart.filetransfer.service;

import com.burakkizilkaya.multipart.filetransfer.response.FileResponse;
import com.burakkizilkaya.multipart.filetransfer.response.FileUploadUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileUploadService {
    private String webUrl="http://localhost:8081/test/uploadlocal";
    private final RestTemplate restTemplate;

    public FileUploadService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FileResponse uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
        FileResponse response=new FileResponse();
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);

             // if(!filecode.isEmpty()){
             //   restTemplate.postForEntity(webUrl,String.class);
             //}

        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);
        return response;
    }

    public ResponseEntity<String> uploadFileToService(MultipartFile multipartFile) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file",multipartFile.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                webUrl, // real endpoint
                requestEntity,
                String.class
        );
        return responseEntity;

    }


}