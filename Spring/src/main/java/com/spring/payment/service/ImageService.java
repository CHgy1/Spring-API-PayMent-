package com.spring.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    private final String uploadDir = "uploads/";

    public ImageService() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    // 이미지 저장
    public String saveImage(MultipartFile image) {
        String fileName = uploadDir + UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        System.out.println(fileName);
        try {
            Path path = Paths.get(fileName);
            Files.write(path, image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
        return fileName;
    }
}
