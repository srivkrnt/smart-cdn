package com.cdn.smartcdn.util;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.imgscalr.Scalr;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Component
public class ImageUtil {

    public static BufferedImage getBufferedImageFromInputStream(InputStream originalImageInputStream) throws IOException {
        return ImageIO.read(originalImageInputStream);
    }

    public static BufferedImage resizeImage(InputStream inputStream, int targetHeight, int targetWidth) throws Exception {
        BufferedImage originalImage = getBufferedImageFromInputStream(inputStream);
        return Scalr.resize(
                originalImage,
                Scalr.Method.AUTOMATIC,
                Scalr.Mode.AUTOMATIC,
                targetWidth,
                targetHeight,
                Scalr.OP_ANTIALIAS
        );
    }

    public static MultipartFile getMultipartFileFromBufferedImage(String fileName, BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);

        return new MockMultipartFile(fileName, byteArrayOutputStream.toByteArray());
    }
}
