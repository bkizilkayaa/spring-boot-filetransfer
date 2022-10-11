package com.burakkizilkaya.multipart.filetransfer.controller;

import com.burakkizilkaya.multipart.filetransfer.response.FileResponse;
import com.burakkizilkaya.multipart.filetransfer.service.FileDownloadService;
import com.burakkizilkaya.multipart.filetransfer.service.FileUploadService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.parseMediaType;

@Controller
@RequestMapping("/files")
public class FileTransferController {

    private RestTemplate restTemplate;
    private final String webUrl="http://localhost:8081/test";


    FileUploadService fileUploadService;
    FileDownloadService fileDownloadService;

    public FileTransferController(RestTemplate restTemplate, FileUploadService fileUploadService,
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

    @GetMapping("/downloadFile/{fileCode}")
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
    @GetMapping("/getandsave/{fileName}")
    ResponseEntity<String> getAndSaveFile(@PathVariable String fileName) throws IOException{
        try {
            Resource resource=
                    restTemplate.getForObject(
                            webUrl+"/downloadFile/"+fileName,Resource.class);
            //UrlResource resource=fileDownloadService.downloadFile(fileName);
            String contentType = "application/octet-stream";
            String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
            Path path= Paths.get("Files-Upload").toAbsolutePath().normalize();
            Files.createDirectories(Paths.get("Files-Upload"));
            Files.copy(resource.getInputStream(),
                    Paths.get(path+"\\"+fileName),
                    REPLACE_EXISTING);
            if(resource.getFilename()!=null){
                return ResponseEntity.ok()
                        .contentType(parseMediaType(contentType))
                        .header(CONTENT_DISPOSITION, headerValue)
                        .body("file uploaded successfully.");
            }
        }
        catch (Exception e){
            return new ResponseEntity<>("file not found",NOT_FOUND);
        }
         return null;
    }

}
