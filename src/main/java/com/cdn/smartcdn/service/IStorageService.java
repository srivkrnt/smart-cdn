package com.cdn.smartcdn.service;

import com.cdn.smartcdn.dto.response.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface IStorageService {
    public UploadResponse uploadFile(final String fileName, final MultipartFile file, final String provider) throws IOException;
    public InputStream getFile(final String fileName, final String provider);
}
