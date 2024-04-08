package com.example.folderexplorer.models.dtos;

public class FolderDto {
    private String folderName;
    private int ancestorFolderId;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getAncestorFolderId() {
        return ancestorFolderId;
    }

    public void setAncestorFolderId(int ancestorFolderId) {
        this.ancestorFolderId = ancestorFolderId;
    }
}
