package com.burakkizilkaya.multipart.filetransfer.service;

import com.burakkizilkaya.multipart.filetransfer.response.FileDownloadUtil;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.parseMediaType;

@Service
public class FileDownloadService {

    public UrlResource downloadFile(String fileCode) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();
        UrlResource resource;
        try {
            resource = downloadUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return null;
        }
        return resource;
    }

    public ResponseEntity<String> downloadFileToClient(String fileCode) {
        UrlResource resource=downloadFile(fileCode);
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

   /* public String saveFile(MultipartFile file) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Path filePath = Paths.get("Files-Upload" + "\\" + fileName);

        try {
            Files.copy(file.getInputStream(), filePath, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }
        return fileName;
    }*/
}
