package com.burakkizilkaya.multipart.filetransfer.service;

import com.burakkizilkaya.multipart.filetransfer.response.FileResponse;
import com.burakkizilkaya.multipart.filetransfer.response.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileUploadService {

    public FileResponse uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
        FileResponse response=new FileResponse();
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);

             // if(!filecode.isEmpty()){
             //   restTemplate.postForEntity(webUrl,String.class);
             //}

        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);
        return response;
    }
}