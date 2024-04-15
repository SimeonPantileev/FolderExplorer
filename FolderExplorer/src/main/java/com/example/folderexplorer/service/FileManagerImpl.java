package com.example.folderexplorer.service;

import com.example.folderexplorer.exceptions.EntityNotFoundException;
import com.example.folderexplorer.models.File;
import com.example.folderexplorer.repository.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileManagerImpl implements FileManager {
    private static final String UPLOAD_DIR = "uploads/";
    private final FileRepo fileRepo;

    @Autowired
    public FileManagerImpl(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }

    @Override
    public void fileUpload(File file, MultipartFile multipartFile) throws FileNotFoundException {
        if (multipartFile.isEmpty()) {
            throw new FileNotFoundException();
        }
        try {
            Path path = getPath(file);
            file.setFileAddress(path.toAbsolutePath().toString());
            try {
                fileRepo.getFileByFileAddress(path.toAbsolutePath().toString());
                fileRepo.updateFile(file);
            } catch (EntityNotFoundException e) {
                multipartFile.transferTo(path);
                fileRepo.createFile(file);
            }
            System.out.println("File Saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getPath(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file.getFolder().getFolderId());
        stringBuilder.append("_");
        stringBuilder.append(file.getName());

        return Paths.get(UPLOAD_DIR, stringBuilder.toString());
    }

    @Override
    public File getFileById(int id) {
        return fileRepo.getFileById(id);
    }

    @Override
    public void deleteFile(int fileId) {
        File fileToDelete = getFileById(fileId);
        fileRepo.deleteFile(fileToDelete);
    }

    @Override
    public File renameFile(int id, String newFileName) {
        File fileToUpdate = getFileById(id);
        fileToUpdate.setName(newFileName);
        renameInUploadDirectory(fileToUpdate);
        return fileRepo.updateFile(fileToUpdate);
    }

    private void renameInUploadDirectory(File fileToRename) {
        Path newPath = getPath(fileToRename);

        java.io.File existingFile = new java.io.File(fileToRename.getFileAddress());
        if (existingFile.renameTo(newPath.toFile())) {
            fileToRename.setFileAddress(newPath.toAbsolutePath().toString());
            System.out.println("File has been successfully renamed.");
        } else {
            System.out.println("File renaming failed.");
        }
    }
}
