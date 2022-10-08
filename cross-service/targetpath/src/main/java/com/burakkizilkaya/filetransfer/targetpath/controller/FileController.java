package com.burakkizilkaya.filetransfer.targetpath.controller;

import com.burakkizilkaya.filetransfer.targetpath.model.FileResponseDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@Controller
@RequestMapping("/test")
public class FileController {
    private static String webUrl="http://localhost:8080/files/downloadfile/";
    private static String webUrl2="http://localhost:8080/files/upload";
    private RestTemplate restTemplate;

    public FileController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getFile(@PathVariable String name){
        webUrl=String.join("",webUrl,name);
        ResponseEntity<String> response
                = restTemplate.getForEntity(webUrl, String.class);
        return ResponseEntity.ok(response.getBody());
    }

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
    private File write(MultipartFile file, String dir) throws IOException {
        Path filepath = Paths.get(dir, file.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        }

        return new File(dir);
    }


}
