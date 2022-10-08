package com.burakkizilkaya.multipart.filetransfer.service;

import com.burakkizilkaya.multipart.filetransfer.response.FileDownloadUtil;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
}
