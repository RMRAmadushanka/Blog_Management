package com.example.backend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class ImageUploadService {

    @Autowired
    private AmazonS3 amazonS3;

    private String bucketName = "your-bucket-name";  // AWS S3 Bucket Name

    public String uploadImage(MultipartFile file) throws IOException {
        // Convert the MultipartFile to a File
        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        Files.copy(file.getInputStream(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Upload the file to S3
        amazonS3.putObject(new PutObjectRequest(bucketName, file.getOriginalFilename(), tempFile));

        // Generate the S3 URL for the uploaded file
        return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
    }
}

