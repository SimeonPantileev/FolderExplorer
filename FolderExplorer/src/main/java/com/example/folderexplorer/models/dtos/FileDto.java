package com.example.folderexplorer.models.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public class FileDto {
    private Optional<String> name;
    private int folderId;

    public FileDto() {
    }
    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }
}
