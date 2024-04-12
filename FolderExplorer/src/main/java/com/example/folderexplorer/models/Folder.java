package com.example.folderexplorer.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "folders")
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id")
    private int folderId;
    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinTable(
            name = "folders_structure",
            joinColumns = @JoinColumn(name = "ancestor_id"),
            inverseJoinColumns = @JoinColumn(name = "folder_id"))
    private List<Folder> folders;

    @ManyToOne()
    @JoinTable(
            name = "folders_structure",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "ancestor_id"))
    private Folder ancestorFolder;

    @OneToMany(mappedBy = "folder", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<File> files;


    public Folder() {
    }

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

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Folder)) return false;
        Folder folder = (Folder) o;
        return getName().equals(folder.getName());
    }
}
