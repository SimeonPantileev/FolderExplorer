package com.example.folderexplorer.controller;

import com.example.folderexplorer.helpers.HistoryHelper;
import com.example.folderexplorer.models.Folder;
import com.example.folderexplorer.models.dtos.FolderDto;
import com.example.folderexplorer.service.Navigation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/folder")
public class FolderExplorerController {
    private final Navigation navigationService;
    private final HistoryHelper historyHelper;

    @Autowired
    public FolderExplorerController(Navigation navigationService, HistoryHelper historyHelper) {
        this.navigationService = navigationService;
        this.historyHelper = historyHelper;
    }

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }


    @GetMapping("/{folderId}")
    public String showCurrentFolder(@PathVariable int folderId, Model model, HttpSession session) {
        Folder currentFolder = navigationService.enterFolderById(folderId);
        historyHelper.addToHistoryList(session, currentFolder.getAncestorFolder());
        model.addAttribute("rootFolder", currentFolder);
        return "FolderContent";
    }
    @GetMapping("/backwards/{folderId}")
    public String showCurrentFolderBackwards(@PathVariable int folderId, Model model, HttpSession session) {
        Folder currentFolder = navigationService.enterFolderById(folderId);
        historyHelper.removeFromHistoryList(session, currentFolder.getAncestorFolder());
        model.addAttribute("rootFolder", currentFolder);
        return "redirect:/folder/" + folderId;
    }

    @GetMapping("{folderId}/new")
    public String showCreateFolderPage(@PathVariable int folderId, Model model) {
        Folder currentFolder = navigationService.enterFolderById(folderId);
        model.addAttribute("newFolder", new FolderDto());
        model.addAttribute("rootFolder", currentFolder);
        return "NewFolderPage";
    }
    @PostMapping("{folderId}/new")
    public String createFolder(@PathVariable int folderId,
                               @ModelAttribute("newFolder") FolderDto folderDto) {
        navigationService.createFolder(folderDto.getFolderName(), folderId);
        return "redirect:/folder/" + folderId;
    }

    @GetMapping("/{folderId}/delete/{folderToDelete}")
    public String deleteFolder(@PathVariable int folderId,
                               @PathVariable int folderToDelete) {
        navigationService.deleteFolder(folderToDelete);
        return "redirect:/folder/" + folderId;
    }

    @GetMapping("/{folderId}/rename/{folderToRename}")
    public String showRenameFolderPage(@PathVariable int folderId,
                                       @PathVariable int folderToRename,
                                       Model model) {
        Folder currentFolder = navigationService.enterFolderById(folderId);
        model.addAttribute("rootFolder", currentFolder);
        model.addAttribute("folderToRename", folderToRename);
        model.addAttribute("newFolderName", "");
        return "FolderRename";
    }

    @PostMapping("/{folderId}/rename/{folderToRename}")
    public String renameFolder(@PathVariable int folderId,
                               @PathVariable int folderToRename,
                               @ModelAttribute("newFolderName") String folderName) {
        navigationService.renameFolder(folderToRename, folderName);
        return "redirect:/folder/" + folderId;
    }

}
