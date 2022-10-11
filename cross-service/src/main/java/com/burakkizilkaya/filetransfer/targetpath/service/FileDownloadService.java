package com.burakkizilkaya.filetransfer.targetpath.service;

import com.burakkizilkaya.filetransfer.targetpath.utilities.FileDownloadUtil;
import org.springframework.core.io.UrlResource;

import java.io.IOException;

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
