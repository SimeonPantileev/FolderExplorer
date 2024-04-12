package com.example.folderexplorer.controller;

import com.example.folderexplorer.helpers.FileMapper;
import com.example.folderexplorer.models.File;
import com.example.folderexplorer.models.Folder;
import com.example.folderexplorer.models.dtos.FileDto;
import com.example.folderexplorer.service.FileManager;
import com.example.folderexplorer.service.Navigation;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.util.Optional;

@Controller
@RequestMapping("folder/{id}/file")
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
    public String showFileUploadPage(@PathVariable int id, Model model, HttpSession session){
        Folder currentFolder = navigationService.enterFolderById(id);
        model.addAttribute("rootFolder", currentFolder);
        return "AddFilePage";
    }
    @PostMapping("/upload")
    public String handleFileUpload(/*@RequestBody FileDto dto,*/
                                   @RequestParam("file") MultipartFile fileContent,
                                   @RequestParam("fileName") Optional<String> fileName,
                                   Model model,
                                   @PathVariable int id) {
        try{
            File file = new File(fileName.orElse("New File"), navigationService.enterFolderById(id));
            fileManager.fileUpload(file, fileContent);
            model.addAttribute("rootFolder", navigationService.enterFolderById(id));
            return "redirect:/folder/" + id;
        } catch (FileNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
