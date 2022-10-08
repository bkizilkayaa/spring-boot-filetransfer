package com.burakkizilkaya.multipart.filetransfer.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class FileResponse {
    private String fileName;
    private String downloadUri;
    private long size;
}
