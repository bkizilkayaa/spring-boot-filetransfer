package com.burakkizilkaya.multipart.filetransfer.service;

import com.burakkizilkaya.multipart.filetransfer.response.FileDownloadUtil;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
