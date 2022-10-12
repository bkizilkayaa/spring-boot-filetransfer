package com.burakkizilkaya.multipart.filetransfer.controller;

import com.burakkizilkaya.multipart.filetransfer.response.FileResponse;
import com.burakkizilkaya.multipart.filetransfer.service.FileDownloadService;
import com.burakkizilkaya.multipart.filetransfer.service.FileUploadService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
    public ResponseEntity<?> uploadFileToService(
            @RequestParam("file") MultipartFile multipartFile) throws IOException {
        return new ResponseEntity<>(fileUploadService.uploadFileToService(multipartFile), CREATED);

    }

    @GetMapping("/downloadFile/client/{fileCode}")
    public ResponseEntity<?> downloadFileToClient(@PathVariable("fileCode") String fileCode) throws IOException {
        return new ResponseEntity<>(fileDownloadService.downloadFileToClient(fileCode).getBody(), HttpStatus.OK);
    }

    @GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) throws IOException {
        UrlResource resource=fileDownloadService.downloadFile(fileCode);
        if(resource==null){
            return new ResponseEntity<>("not found",NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(resource.getFilename()+" retreived properly", HttpStatus.OK);
        }

    }

}
