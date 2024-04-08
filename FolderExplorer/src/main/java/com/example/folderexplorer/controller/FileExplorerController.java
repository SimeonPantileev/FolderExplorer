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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class FileExplorerController {
    private final Navigation navigationService;
    private final HistoryHelper historyHelper;

    @Autowired
    public FileExplorerController(Navigation navigationService, HistoryHelper historyHelper) {
        this.navigationService = navigationService;
        this.historyHelper = historyHelper;
    }

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping
    public String showRootPage(Model model, HttpSession session) {

        Folder rootFolder = navigationService.getRoot();
        historyHelper.createHistoryList(session);
        model.addAttribute("rootFolder", rootFolder);
        return "HomePage";
    }

    @GetMapping("/{id}")
    public String showCurrentFolder(@PathVariable int id, Model model, HttpSession session) {
        Folder currentFolder = navigationService.enterFolderById(id);
        historyHelper.addToHistoryList(session, currentFolder.getAncestorFolder());
        model.addAttribute("rootFolder", currentFolder);
        return "HomePage";
    }

    //ToDo change mapping to post?
    @GetMapping("/backwards/{id}")
    public String showCurrentFolderBackwards(@PathVariable int id, Model model, HttpSession session) {
        Folder currentFolder = navigationService.enterFolderById(id);
        historyHelper.removeFromHistoryList(session, currentFolder.getAncestorFolder());
        model.addAttribute("rootFolder", currentFolder);
        return "HomePage";
    }

    @GetMapping("{id}/new")
    public String showCreateFolderPage (@PathVariable int id, Model model, HttpSession session) {
        Folder currentFolder = navigationService.enterFolderById(id);
        model.addAttribute("newFolder", new FolderDto());
        model.addAttribute("rootFolder", currentFolder);
        return "NewFolderPage";
    }

    //ToDo add mapper inseatd of mapping logic in controller
    @PostMapping("{id}/new")
    public String createFolder(@PathVariable int id,
                               @ModelAttribute("newFolder") FolderDto folderDto,
                               BindingResult bindingResult,
                               Model model,
                               HttpSession session) {
        folderDto.setAncestorFolderId(id);
        navigationService.createFolder(folderDto.getFolderName(), folderDto.getAncestorFolderId());
        return "redirect:/home/" + id;
    }

    @GetMapping("/{id}/delete/{folderToDelete}")
    public String deleteFolder(@PathVariable int id, @PathVariable int folderToDelete, Model model, HttpSession session){
        navigationService.deleteFolder(folderToDelete);
        return "redirect:/home/" + id;
    }

}
