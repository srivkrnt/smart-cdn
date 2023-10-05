package com.cdn.smartcdn.service.impl;

import com.cdn.smartcdn.dto.helper.SizeAwareLink;
import com.cdn.smartcdn.dto.response.UploadResponse;
import com.cdn.smartcdn.entity.MediaEntity;
import com.cdn.smartcdn.repository.MediaDataAccess;
import com.cdn.smartcdn.service.IStorageService;
import com.cdn.smartcdn.util.AWSStorageUtil;
import com.cdn.smartcdn.util.GCPStorageUtil;
import com.cdn.smartcdn.util.ImageUtil;
import com.cdn.smartcdn.util.StorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class StorageService implements IStorageService {

    @Autowired
    private AWSStorageUtil awsStorageUtil;

    @Autowired
    private GCPStorageUtil gcpStorageUtil;

    @Autowired
    private MediaDataAccess mediaDataAccess;

    private StorageUtil getStorageHandler(String provider){

        if(provider.equalsIgnoreCase("GCP")){
            return gcpStorageUtil;
        }

        return awsStorageUtil;
    }

    private List<SizeAwareLink> addLinkIfNotAlreadyPresent(SizeAwareLink newLink, List<SizeAwareLink> links){
        boolean isLinkAlreadyPresent = false;

        for(SizeAwareLink sizeAwareLink: links){
            if(sizeAwareLink.getLink().equalsIgnoreCase(newLink.getLink())){
                isLinkAlreadyPresent = true;
                break;
            }
        }
        if(!isLinkAlreadyPresent){
            links.add(newLink);
        }

        return links;
    }

    private void createMediaEntry(final String fileName, final MultipartFile file, UploadResponse uploadResponse, String provider) throws IOException {
        if(!uploadResponse.getStatus().equalsIgnoreCase("SUCCESS")){
            return;
        }

        BufferedImage image = ImageUtil.getBufferedImageFromInputStream(file.getInputStream());
        SizeAwareLink link = SizeAwareLink
                .builder()
                .height(image.getHeight())
                .width(image.getWidth())
                .size(file.getSize())
                .fileKey(uploadResponse.getFileKey())
                .link(uploadResponse.getLink())
                .isPrimary(false)
                .build();

        MediaEntity mediaEntity = mediaDataAccess.retrieve(fileName);
        List<SizeAwareLink> links = new ArrayList<>();
        if(Objects.isNull(mediaEntity)) {
            mediaEntity = MediaEntity.builder()
                    .name(uploadResponse.getName())
                    .provider(provider)
                    .build();
            link.setIsPrimary(true);
        } else {
            links = mediaEntity.getLinks();
        }

        links = addLinkIfNotAlreadyPresent(link, links);
        mediaEntity.setLinks(links);
        mediaDataAccess.createOrUpdate(mediaEntity);
    }

    @Override
    public UploadResponse uploadFile(final String fileName, final MultipartFile file, final String provider) throws IOException {
        StorageUtil storageHandler = getStorageHandler(provider);
        UploadResponse uploadResponse = storageHandler.uploadFile(fileName, file);

        createMediaEntry(fileName, file, uploadResponse, provider);
        return uploadResponse;
    }

    @Override
    public InputStream getFile(final String fileName, final String provider){
        StorageUtil storageHandler = getStorageHandler(provider);
        return storageHandler.getFile(fileName);
    }
}
