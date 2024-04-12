package com.example.folderexplorer.repository;

import com.example.folderexplorer.models.Folder;
import com.example.folderexplorer.models.File;

public interface FileRepo {

    File getFileByFileAddress(String fileAddress);
    File getFileById(int fileId);
    void createFile(File file);
    void deleteFile(File file);
}
