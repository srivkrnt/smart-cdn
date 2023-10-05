package com.cdn.smartcdn.controller;

import com.cdn.smartcdn.dto.response.UploadResponse;
import com.cdn.smartcdn.entity.MediaEntity;
import com.cdn.smartcdn.service.impl.MediaService;
import com.cdn.smartcdn.service.impl.StorageService;
import com.mongodb.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class MediaController {

    @Autowired
    MediaService mediaService;

    @Autowired
    private StorageService storageService;

    @PostMapping("/upload/")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String fileName,
            @RequestParam("provider") String provider) throws IOException {

        UploadResponse uploadResponse = storageService.uploadFile(fileName, file, provider);
        return new ResponseEntity<>(uploadResponse, HttpStatus.OK);
    }

    @GetMapping("/media")
    public ResponseEntity<?> list(@Nullable @RequestParam("name") String name){
        List<MediaEntity> mediaEntity = mediaService.list(name);

        return new ResponseEntity<>(mediaEntity, HttpStatus.OK);
    }

    @GetMapping("/media/{name}")
    public ResponseEntity<?> retrieve(
            @PathVariable("name") String name,
            @Nullable @RequestParam("height") Integer height,
            @Nullable @RequestParam("width") Integer width) throws Exception {
        MediaEntity mediaEntity = mediaService.retrieve(name, height, width);

        return new ResponseEntity<>(mediaEntity, HttpStatus.OK);
    }

}
