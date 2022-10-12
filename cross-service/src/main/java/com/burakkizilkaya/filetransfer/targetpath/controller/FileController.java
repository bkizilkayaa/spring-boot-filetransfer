package com.burakkizilkaya.filetransfer.targetpath.controller;

import com.burakkizilkaya.filetransfer.targetpath.model.FileResponseDto;
import com.burakkizilkaya.filetransfer.targetpath.service.FileDownloadService;
import com.burakkizilkaya.filetransfer.targetpath.service.FileUploadService;
import com.burakkizilkaya.filetransfer.targetpath.utilities.FileDownloadUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpStatus.*;

@Controller
@CrossOrigin("http://localhost:8080")
@RequestMapping("/test")
public class FileController {
    private String webUrl = "http://localhost:8080/files/downloadFile/";
    private String webUrl2 = "http://localhost:8080/files/upload";

    private String webUrlDownloadFile = "http://localhost:8080/files/downloadFile/";

    private RestTemplate restTemplate;
    private final FileUploadService fileUploadService;
    private final FileDownloadService fileDownloadService;
    private final FileDownloadUtil fileDownloadUtil;

    public FileController(RestTemplate restTemplate,
                          FileUploadService fileUploadService,
                          FileDownloadService fileDownloadService,
                          FileDownloadUtil fileDownloadUtil) {
        this.restTemplate = restTemplate;
        this.fileUploadService = fileUploadService;
        this.fileDownloadService = fileDownloadService;
        this.fileDownloadUtil = fileDownloadUtil;
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getFile(@PathVariable String name) {
        //webUrlDownloadFile = String.join("", webUrlDownloadFile, name);
        //webUrlDownloadFile+=name;
        ResponseEntity<String> response
                = restTemplate.getForEntity(webUrl+name, String.class);
        return ResponseEntity.ok(response.getBody());
    }
    @GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
        Resource resource=null;
        try {
            resource = fileDownloadUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile)
            throws IOException {
        //static import for HTTP CREATED
        //return new ResponseEntity<>(fileUploadService.uploadFile(multipartFile), CREATED);
        return new ResponseEntity<>(fileUploadService.uploadFileToServer(multipartFile), CREATED);
    }

    @PostMapping("/uploadlocal")
    public ResponseEntity<?> uploadFileToLocal(@RequestParam("file") MultipartFile multipartFile)
            throws IOException {
        //static import for HTTP CREATED
        //return new ResponseEntity<>(fileUploadService.uploadFile(multipartFile), CREATED);
        return new ResponseEntity<>(fileUploadService.uploadFile(multipartFile), CREATED);
    }






}
