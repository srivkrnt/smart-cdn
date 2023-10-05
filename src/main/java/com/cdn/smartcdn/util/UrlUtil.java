package com.cdn.smartcdn.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlUtil {

    public static String getUrl(String basePath, String ...pathList){

        StringBuilder urlBuilder = new StringBuilder().append(basePath);
        for(String path: pathList){
            urlBuilder.append("/").append(path);
        }

        return urlBuilder.toString();
    }
}
