package com.burakkizilkaya.filetransfer.targetpath.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FileResponseDto {
    private String fileName;
    private String downloadUri;
    private long size;

    public FileResponseDto(String fileName, String downloadUri, long size) {
        this.fileName = fileName;
        this.downloadUri = downloadUri;
        this.size = size;
    }
}
