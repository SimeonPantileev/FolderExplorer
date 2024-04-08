package com.example.folderexplorer.service;

import com.example.folderexplorer.models.Folder;
import com.example.folderexplorer.repository.StructureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NavigationImpl implements Navigation {

    private final StructureRepo structureRepo;

    @Autowired
    public NavigationImpl(StructureRepo structureRepo) {
        this.structureRepo = structureRepo;
    }

    @Override
    public Folder getRoot() {
        return structureRepo.getRoot();
    }

    @Override
    public Folder enterFolderById(int folderId) {
        return structureRepo.getFolderById(folderId);
    }

    @Override
    public Folder createFolder(String folderName, int ancestorId) {
        Folder newFolder = new Folder(folderName);
        Folder ancestorFolder = structureRepo.getFolderById(ancestorId);
        newFolder.setAncestorFolder(ancestorFolder);

        structureRepo.createFolder(newFolder);
        return newFolder;
    }

    @Override
    public void deleteFolder(int folderId) {
        Folder folderToDelete = structureRepo.getFolderById(folderId);
        structureRepo.deleteFolder(folderToDelete);
    }
}
