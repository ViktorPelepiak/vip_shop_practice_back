package com.vip.shop.services.impl;

import com.vip.shop.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
@Transactional
@PropertySource("/image.properties")
public class ImageServiceImpl implements ImageService {

    private final Environment environment;
    private final String imageFilePath;
    private final String imageFileExtension;

    @Autowired
    public ImageServiceImpl(Environment environment) {
        this.environment = environment;

        this.imageFilePath = environment.getProperty("new.image.path");
        this.imageFileExtension = environment.getProperty("new.image.extension");
    }


    @Override
    public String saveImage(byte[] imageByteArray, String imageName) {
        try {
            String fileName = imageName + this.imageFileExtension;
            OutputStream out = new FileOutputStream(imageFilePath + fileName);
            out.write(imageByteArray);
            out.flush();
            out.close();

            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
