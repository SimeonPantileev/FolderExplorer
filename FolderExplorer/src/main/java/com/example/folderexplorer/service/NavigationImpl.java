package com.example.folderexplorer.service;

import com.example.folderexplorer.models.Folder;
import com.example.folderexplorer.repository.FolderStructureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NavigationImpl implements Navigation {

    private final FolderStructureRepo folderStructureRepo;

    @Autowired
    public NavigationImpl(FolderStructureRepo folderStructureRepo) {
        this.folderStructureRepo = folderStructureRepo;
    }

    @Override
    public Folder getRoot() {
        return folderStructureRepo.getRoot();
    }

    @Override
    public Folder enterFolderById(int folderId) {
        return folderStructureRepo.getFolderById(folderId);
    }

    @Override
    public Folder createFolder(String folderName, int ancestorId) {
        Folder newFolder = new Folder(folderName);
        Folder ancestorFolder = folderStructureRepo.getFolderById(ancestorId);
        newFolder.setAncestorFolder(ancestorFolder);

        folderStructureRepo.createFolder(newFolder);
        return newFolder;
    }

    @Override
    public void deleteFolder(int folderId) {
        Folder folderToDelete = enterFolderById(folderId);
        folderStructureRepo.deleteFolder(folderToDelete);
    }

    @Override
    public Folder renameFolder(int folderId, String folderName) {
        Folder folderToUpdate = enterFolderById(folderId);
        folderToUpdate.setName(folderName);
        return folderStructureRepo.updateFolder(folderToUpdate);
    }
}
