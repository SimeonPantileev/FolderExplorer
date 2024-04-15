package com.example.folderexplorer.repository;

import com.example.folderexplorer.models.Folder;

public interface FolderStructureRepo {
    Folder getFolderById(int id);

    Folder getRoot();

    Folder createFolder(Folder folder);

    void deleteFolder(Folder folder);

    Folder updateFolder(Folder folder);
}
