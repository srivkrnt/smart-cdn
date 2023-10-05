package com.cdn.smartcdn.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UploadUtil {

    public static String getUploadName(String fileName, Integer height, Integer width){
        return fileName + "-" + height + "x" + width;
    }
}
