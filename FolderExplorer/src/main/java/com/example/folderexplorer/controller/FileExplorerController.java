package com.example.folderexplorer.controller;

import com.example.folderexplorer.helpers.FileMapper;
import com.example.folderexplorer.models.File;
import com.example.folderexplorer.models.Folder;
import com.example.folderexplorer.service.FileManager;
import com.example.folderexplorer.service.Navigation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.util.Optional;

@Controller
@RequestMapping("folder/{folderId}/file")
public class FileExplorerController {
    private final Navigation navigationService;
    private final FileManager fileManager;
    private final FileMapper fileMapper;

    public FileExplorerController(Navigation navigationService, FileManager fileManager, FileMapper fileMapper) {
        this.navigationService = navigationService;
        this.fileManager = fileManager;
        this.fileMapper = fileMapper;
    }

    @GetMapping("/upload")
    public String showFileUploadPage(@PathVariable int folderId, Model model) {
        Folder currentFolder = navigationService.enterFolderById(folderId);
        model.addAttribute("rootFolder", currentFolder);
        return "AddFilePage";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile fileContent,
                                   @RequestParam("fileName") Optional<String> fileName,
                                   Model model,
                                   @PathVariable int folderId) {
        try {
            File file = fileMapper.toFile(fileName);
            file.setFolder(navigationService.enterFolderById(folderId));
            fileManager.fileUpload(file, fileContent);
            model.addAttribute("rootFolder", navigationService.enterFolderById(folderId));
            return "redirect:/folder/" + folderId;
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/delete/{fileToDelete}")
    public String deleteFile(@PathVariable int folderId, @PathVariable int fileToDelete) {
        fileManager.deleteFile(fileToDelete);
        return "redirect:/folder/" + folderId;
    }

    @GetMapping("/rename/{fileToUpdateId}")
    public String showRenamePage(@PathVariable int folderId,
                                 @PathVariable int fileToUpdateId,
                                 Model model) {
        Folder currentFolder = navigationService.enterFolderById(folderId);
        File fileToUpdate = fileManager.getFileById(fileToUpdateId);
        model.addAttribute("rootFolder", currentFolder);
        model.addAttribute("fileToUpdate", fileToUpdate);
        model.addAttribute("newFileName", "");
        return "FileRename";
    }

    @PostMapping("/rename/{fileToUpdateId}")
    public String renameFile(@PathVariable int folderId,
                             @PathVariable int fileToUpdateId,
                             @ModelAttribute("newFileName") String fileName) {
        fileManager.renameFile(fileToUpdateId, fileName);
        return "redirect:/folder/" + folderId;
    }
}
