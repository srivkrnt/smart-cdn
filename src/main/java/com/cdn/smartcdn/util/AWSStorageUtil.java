package com.cdn.smartcdn.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.cdn.smartcdn.dto.response.UploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Component
public class AWSStorageUtil implements StorageUtil{

    @Autowired
    @Qualifier("awsStorageClient")
    private AmazonS3 awsStorageClient;

    @Value("${cloud.aws.storage.bucketName}")
    private String bucketName;

    @Value("${cloud.aws.storage.cdnEndpoint}")
    private String cdnEndpoint;

    private static ObjectMetadata getMetaData(final MultipartFile file){
        ObjectMetadata metaData = new ObjectMetadata();

        metaData.setContentLength(file.getSize());
        metaData.setContentType(file.getContentType());
        metaData.setHeader("x-amz-acl", "public-read");

        return metaData;
    }

    @Override
    public UploadResponse uploadFile(final String fileName, final MultipartFile file) throws IOException {

        ObjectMetadata metadata = getMetaData(file);
        UploadResponse uploadResponse = new UploadResponse();
        uploadResponse.setName(fileName);

        BufferedImage bufferedImage = ImageUtil.getBufferedImageFromInputStream(file.getInputStream());
        String sizeAwareName = UploadUtil.getUploadName(fileName, bufferedImage.getHeight(), bufferedImage.getWidth());

        try {
            PutObjectResult uploadResult = awsStorageClient
                    .putObject(bucketName, sizeAwareName, file.getInputStream(), metadata);

            String link = UrlUtil.getUrl(cdnEndpoint, sizeAwareName);
            uploadResponse.setMetaData(uploadResult.getMetadata());
            uploadResponse.setCreatedAt(new Date());
            uploadResponse.setStatus("SUCCESS");
            uploadResponse.setLink(link);
            uploadResponse.setFileKey(sizeAwareName);
        } catch (IOException e) {
            uploadResponse.setStatus("FAILED");
        }

        return uploadResponse;
    }

    @Override
    public InputStream getFile(final String fileName){
        S3Object object = awsStorageClient.getObject(bucketName, fileName);
        return object.getObjectContent();
    }
}
