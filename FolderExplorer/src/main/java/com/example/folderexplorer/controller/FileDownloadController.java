package com.example.folderexplorer.controller;

import com.example.folderexplorer.models.File;
import com.example.folderexplorer.service.FileManager;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileDownloadController {
    private final FileManager fileManager;

    public FileDownloadController(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @GetMapping("/download/{fileToDownload}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileToDownload) {
        try {
            int fileId = Integer.parseInt(fileToDownload);
            File file = fileManager.getFileById(fileId);
            Path filePath = Paths.get(file.getFileAddress()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                // Log that the file exists and is about to be returned
                System.out.println("File found: " + resource.getFilename());

                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // Log that the file does not exist
                System.out.println("File not found: " + filePath.toString());

                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            // Log the exception for debugging
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
