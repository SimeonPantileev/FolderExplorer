package com.example.folderexplorer.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Optional;

public interface FileManager {
    void fileUpload(MultipartFile file, Optional<String> fileName) throws FileNotFoundException;

}
