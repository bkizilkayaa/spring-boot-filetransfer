package com.burakkizilkaya.filetransfer.targetpath.service;

import com.burakkizilkaya.filetransfer.targetpath.model.FileResponseDto;
import com.burakkizilkaya.filetransfer.targetpath.utilities.FileUploadUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileUploadService {
    public FileResponseDto uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
        FileResponseDto response=new FileResponseDto();
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
