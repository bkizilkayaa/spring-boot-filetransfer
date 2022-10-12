package com.burakkizilkaya.filetransfer.targetpath.service;

import com.burakkizilkaya.filetransfer.targetpath.model.FileResponseDto;
import com.burakkizilkaya.filetransfer.targetpath.utilities.FileUploadUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileUploadService {
    private String webUrl2 = "http://localhost:8080/files/upload";
    private final RestTemplate restTemplate;

    public FileUploadService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FileResponseDto uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
        FileResponseDto response=new FileResponseDto();
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);

        // if(!filecode.isEmpty()){
        //   restTemplate.postForEntity(webUrl,String.class);
        //}

        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);
        return response;
    }

    public ResponseEntity<String> uploadFileToServer(MultipartFile multipartFile) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file",multipartFile.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                webUrl2, // real endpoint
                requestEntity,
                String.class
        );
        return responseEntity;
    }
}
