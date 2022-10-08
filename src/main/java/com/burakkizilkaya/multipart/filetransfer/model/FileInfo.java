package com.burakkizilkaya.multipart.filetransfer.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FileInfo {
    private String fileName;
    private String downloadUri;
    private long size;


    //Constructor
    public FileInfo(String fileName, String downloadUri, long size) {
        this.fileName = fileName;
        this.downloadUri = downloadUri;
        this.size = size;
    }
}
