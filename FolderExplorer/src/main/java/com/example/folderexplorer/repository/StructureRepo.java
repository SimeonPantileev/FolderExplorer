package com.example.folderexplorer.repository;

import com.example.folderexplorer.models.Folder;

public interface StructureRepo {
    Folder getFolderById(int id);
    Folder getRoot();
}
