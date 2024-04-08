package com.example.folderexplorer.models;

import java.util.ArrayList;
import java.util.List;

public class Folder {
    private int folderId;
    private String name;
    private List<Folder> folders;
    private Folder ancestorFolder;

    public Folder(String name) {
        this.name = name;
        folders = new ArrayList<>();
    }

    public Folder(int folderId, String name) {
        this.folderId = folderId;
        this.name = name;
        folders = new ArrayList<>();
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }

    public Folder getAncestorFolder() {
        return ancestorFolder;
    }

    public void setAncestorFolder(Folder ancestorFolder) {
        this.ancestorFolder = ancestorFolder;
    }
}
