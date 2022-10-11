package com.burakkizilkaya.filetransfer.targetpath.controller;

import com.burakkizilkaya.filetransfer.targetpath.model.FileResponseDto;
<<<<<<< HEAD
import com.burakkizilkaya.filetransfer.targetpath.service.FileDownloadService;
import com.burakkizilkaya.filetransfer.targetpath.service.FileUploadService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
=======
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
>>>>>>> c3036d3a482389395ca201c246e20f68855c108a
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

<<<<<<< HEAD
import java.io.*;
=======
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
>>>>>>> c3036d3a482389395ca201c246e20f68855c108a
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

<<<<<<< HEAD
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.parseMediaType;
=======
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
>>>>>>> c3036d3a482389395ca201c246e20f68855c108a

@Controller
@RequestMapping("/test")
public class FileController {
<<<<<<< HEAD
    private static String webUrl = "http://localhost:8080/files/downloadfile/";
    private static String webUrl2 = "http://localhost:8080/files/upload";
    private RestTemplate restTemplate;
    private final FileUploadService fileUploadService;
    private final FileDownloadService fileDownloadService;

    public FileController(RestTemplate restTemplate,
                          FileUploadService fileUploadService,
                          FileDownloadService fileDownloadService) {
        this.restTemplate = restTemplate;
        this.fileUploadService = fileUploadService;
        this.fileDownloadService = fileDownloadService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getFile(@PathVariable String name) {
        webUrl = String.join("", webUrl, name);
=======
    private static String webUrl="http://localhost:8080/files/downloadfile/";
    private static String webUrl2="http://localhost:8080/files/upload";
    private RestTemplate restTemplate;

    public FileController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getFile(@PathVariable String name){
        webUrl=String.join("",webUrl,name);
>>>>>>> c3036d3a482389395ca201c246e20f68855c108a
        ResponseEntity<String> response
                = restTemplate.getForEntity(webUrl, String.class);
        return ResponseEntity.ok(response.getBody());
    }
<<<<<<< HEAD
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
                    .body("file received!!!!.");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<FileResponseDto> uploadFile(@RequestParam("file") MultipartFile multipartFile)
            throws IOException {
        //static import for HTTP CREATED
        return new ResponseEntity<>(fileUploadService.uploadFile(multipartFile), CREATED);
    }

=======

    @PostMapping("/upload")
    public ResponseEntity<FileResponseDto> uploadFile(@RequestParam("file") MultipartFile multipartFile)
            throws IOException{

        Path tempFile = Files.createTempFile(null, null);

        Files.write(tempFile, multipartFile.getBytes());
        File fileToSend = tempFile.toFile();

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        //File file= write(multipartFile,"C:/myfiles");
        File newFile = new File("/myfiles/ornek1.txt");

        parameters.add("file",newFile);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "multipart/form-data");


        HttpEntity httpEntity = new HttpEntity<>(parameters, headers);


        ResponseEntity<FileResponseDto> response
                = restTemplate.postForEntity(webUrl2,httpEntity, FileResponseDto.class);

        FileResponseDto fileResponseDto=response.getBody();
        return new ResponseEntity<>(fileResponseDto, HttpStatus.CREATED);
    }
>>>>>>> c3036d3a482389395ca201c246e20f68855c108a
    private File write(MultipartFile file, String dir) throws IOException {
        Path filepath = Paths.get(dir, file.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        }

        return new File(dir);
    }


}
