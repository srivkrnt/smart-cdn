package com.cdn.smartcdn.service;

import com.cdn.smartcdn.entity.MediaEntity;

import java.util.List;

public interface IMediaService {

    public List<MediaEntity> list(String name);

    public MediaEntity retrieve(String name, Integer height, Integer width) throws Exception;
}
