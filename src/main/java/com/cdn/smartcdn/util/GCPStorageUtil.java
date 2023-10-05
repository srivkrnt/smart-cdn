package com.cdn.smartcdn.util;

import com.cdn.smartcdn.dto.response.UploadResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FilterInputStream;
import java.io.InputStream;

@Component
public class GCPStorageUtil implements StorageUtil{

    @Override
    public UploadResponse uploadFile(final String fileName, final MultipartFile file){

       return new UploadResponse();
    }

    @Override
    public InputStream getFile(final String fileName){
        return null;
    }
}
