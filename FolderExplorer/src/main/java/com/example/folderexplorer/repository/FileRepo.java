package com.example.folderexplorer.repository;

import com.example.folderexplorer.models.File;

public interface FileRepo {

    File getFileByFileAddress(String fileAddress);

    File getFileById(int fileId);

    File createFile(File file);

    void deleteFile(File file);

    File updateFile(File file);
}
