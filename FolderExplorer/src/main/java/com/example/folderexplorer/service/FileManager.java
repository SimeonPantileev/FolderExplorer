package com.example.folderexplorer.service;

import com.example.folderexplorer.models.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface FileManager {
    void fileUpload(File file, MultipartFile multipartFile) throws FileNotFoundException;

    File getFileById(int id);

    void deleteFile(int fileId);

    File renameFile(int id, String newFileName);
}
