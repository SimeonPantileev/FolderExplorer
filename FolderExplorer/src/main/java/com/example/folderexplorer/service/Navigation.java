package com.example.folderexplorer.service;

import com.example.folderexplorer.models.Folder;

public interface Navigation {
    Folder getRoot();

    Folder enterFolderById(int folderId);

    Folder createFolder(String folderName, int ancestorId);

    void deleteFolder(int folderId);

    Folder renameFolder(int folderId, String folderName);
}
