package com.example.folderexplorer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileManagerImpl implements FileManager {
    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public void fileUpload(MultipartFile file, Optional<String> fileName) throws FileNotFoundException {
        if (file.isEmpty()) {
            throw new FileNotFoundException();
        }

        try {
            // Build the path to where the file will be saved
            String newFileName = fileName.orElse("New File");
            Path path = Paths.get(UPLOAD_DIR, newFileName);

//            // Save the file
            file.transferTo(path);

//            Files.copy(file.getInputStream(), path);

            System.out.println("File Saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
