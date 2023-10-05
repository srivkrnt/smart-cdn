package com.cdn.smartcdn.service.impl;

import com.cdn.smartcdn.dto.helper.SizeAwareLink;
import com.cdn.smartcdn.entity.MediaEntity;
import com.cdn.smartcdn.exceptions.ImageNotFoundException;
import com.cdn.smartcdn.repository.MediaDataAccess;
import com.cdn.smartcdn.service.IMediaService;
import com.cdn.smartcdn.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Service
public class MediaService implements IMediaService {

    @Autowired
    MediaDataAccess mediaDataAccess;

    @Autowired
    StorageService storageService;

    @Override
    public List<MediaEntity> list(String name){
        return mediaDataAccess.list(name);
    }

    private void uploadResizedFile(String name,
                                   String primaryFileKey,
                                   Integer height,
                                   Integer width,
                                   String provider) throws Exception {
        InputStream inputStream = storageService.getFile(primaryFileKey, provider);

        BufferedImage resizedImage = ImageUtil.resizeImage(inputStream, height, width);
        MultipartFile resizedMultipartFile = ImageUtil.getMultipartFileFromBufferedImage(name, resizedImage);
        storageService.uploadFile(name, resizedMultipartFile, provider);
    }

    private Boolean checkSizeAvailable(SizeAwareLink sizeAwareLink, Integer height, Integer width){
        if(Objects.isNull(height) || Objects.isNull(width)){
            return true;
        }

        return sizeAwareLink.getHeight().equals(height) && sizeAwareLink.getWidth().equals(width);
    }

    @Override
    public MediaEntity retrieve(String name, Integer height, Integer width) throws Exception {
        MediaEntity mediaEntity = mediaDataAccess.retrieve(name);
        if(Objects.isNull(mediaEntity)){
            throw new ImageNotFoundException("Media Not found");
        }
        List<SizeAwareLink> links = mediaEntity.getLinks();

        boolean isSizeAvailable = false;
        String primaryFileKey = "";

        for(SizeAwareLink sizeAwareLink: links){
            if (checkSizeAvailable(sizeAwareLink, height, width)) {
                isSizeAvailable = true;
                break;
            }
            if(sizeAwareLink.getIsPrimary()){
                primaryFileKey = sizeAwareLink.getFileKey();
            }
        }

        if(!isSizeAvailable){
            uploadResizedFile(name, primaryFileKey, height, width, mediaEntity.getProvider());
        }

        mediaEntity = mediaDataAccess.retrieve(name);
        return mediaEntity;
    }
}
