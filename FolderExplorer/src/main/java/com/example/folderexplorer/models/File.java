package com.example.folderexplorer.models;

import jakarta.persistence.*;

@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private int fileId;
    @Column(name = "file_name")
    private String name;
    @ManyToOne()
    @JoinColumn(name = "placement_id", referencedColumnName = "folder_id")
    private Folder folder;
    @Column(name = "file_address")
    private String fileAddress;

    public File() {
    }

    public File(String name, Folder folder) {
        this.name = name;
        this.folder = folder;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }
}
