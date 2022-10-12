package com.burakkizilkaya.multipart.filetransfer.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FileResponse {
    private String fileName;
    private String downloadUri;

    private long size;


}