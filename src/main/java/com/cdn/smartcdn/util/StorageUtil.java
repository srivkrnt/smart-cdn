package com.cdn.smartcdn.util;

import com.cdn.smartcdn.dto.response.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public interface StorageUtil {

    public UploadResponse uploadFile(final String fileName, final MultipartFile file) throws IOException;

    public InputStream getFile(final String fileName);
}
