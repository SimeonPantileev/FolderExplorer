package com.example.folderexplorer.service;

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
            // Build the path to where the file will be saved
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(file.getFolder().getFolderId());
            stringBuilder.append("_");
            stringBuilder.append(file.getName());
//            String newFileName = fileName.orElse("New File");
            Path path = Paths.get(UPLOAD_DIR, stringBuilder.toString());
            file.setFileAddress(path.toAbsolutePath().toString());

//            // Save the file
            multipartFile.transferTo(path);

            fileRepo.createFile(file);


            System.out.println("File Saved!");
        } catch (IOException e) {
            //ToDo maybe catch the exception in controller and give proper http response?
            e.printStackTrace();
        }
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
}
