package com.burakkizilkaya.multipart.filetransfer.controller;

import com.burakkizilkaya.multipart.filetransfer.response.FileResponse;
import com.burakkizilkaya.multipart.filetransfer.service.FileDownloadService;
import com.burakkizilkaya.multipart.filetransfer.service.FileUploadService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.parseMediaType;

@Controller
@RequestMapping("/files")
public class FileTransferController {

    //private RestTemplate restTemplate;
    //private final String webUrl="http:localhost:8081/test/post";


    FileUploadService fileUploadService;
    FileDownloadService fileDownloadService;

    public FileTransferController(FileUploadService fileUploadService, FileDownloadService fileDownloadService) {
        this.fileUploadService = fileUploadService;
        this.fileDownloadService=fileDownloadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadFile(
            @RequestParam("file") MultipartFile multipartFile) throws IOException {
        //static import for HTTP CREATED
        return new ResponseEntity<>(fileUploadService.uploadFile(multipartFile), CREATED);
    }

    @GetMapping("/downloadfile/{fileCode}")
    public ResponseEntity<String> downloadFile(@PathVariable("fileCode") String fileCode) {

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
                    .body("file received.");
        }
    }

}
