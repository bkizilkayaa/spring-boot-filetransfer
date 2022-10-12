package com.burakkizilkaya.multipart.filetransfer.controller;

import com.burakkizilkaya.multipart.filetransfer.response.FileResponse;
import com.burakkizilkaya.multipart.filetransfer.service.FileDownloadService;
import com.burakkizilkaya.multipart.filetransfer.service.FileUploadService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.parseMediaType;

@Controller
@RequestMapping("/files")
public class FileTransferController {

    private final RestTemplate restTemplate;
    private String webUrl="http://localhost:8081/test/uploadlocal";


    FileUploadService fileUploadService;
    FileDownloadService fileDownloadService;

    public FileTransferController(RestTemplate restTemplate,
                                  FileUploadService fileUploadService,
                                  FileDownloadService fileDownloadService) {
        this.restTemplate = restTemplate;
        this.fileUploadService = fileUploadService;
        this.fileDownloadService=fileDownloadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadFile(
            @RequestParam("file") MultipartFile multipartFile) throws IOException {
        //static import for HTTP CREATED
        return new ResponseEntity<>(fileUploadService.uploadFile(multipartFile), CREATED);
    }
    @PostMapping("/upload/client")
    public ResponseEntity<String> uploadFileToService(
            @RequestParam("file") MultipartFile multipartFile) throws IOException {

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

    @GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<String> downloadFile(@PathVariable("fileCode") String fileCode) throws IOException {
        UrlResource resource=fileDownloadService.downloadFile(fileCode);
        if (resource == null) {
            return new ResponseEntity<>("file not found",NOT_FOUND);
        }
        else{
            String contentType = "application/octet-stream";
            String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
            return ResponseEntity.ok()
                    .contentType(parseMediaType(contentType))
                    .header(CONTENT_DISPOSITION, headerValue)
                    .body(resource.getFilename()+" retreived properly");
        }
    }

}
