package com.example.folderexplorer.controller;

import com.example.folderexplorer.helpers.HistoryHelper;
import com.example.folderexplorer.models.Folder;
import com.example.folderexplorer.service.Navigation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping
    public String showRootPage(Model model, HttpSession session){

        Folder rootFolder = navigationService.getRoot();
        historyHelper.createHistoryList(session);
        model.addAttribute("rootFolder", rootFolder);
        return "HomePage";
    }

    @GetMapping("/{id}")
    public String showCurrentFolder(@PathVariable int id, Model model, HttpSession session){
        Folder currentFolder = navigationService.enterFolderById(id);
        historyHelper.addToHistoryList(session, currentFolder.getAncestorFolder());
        model.addAttribute("rootFolder", currentFolder);
        return "HomePage";
    }

    //ToDo change mapping to post?
    @GetMapping("/backwards/{id}")
    public String showCurrentFolderBackwards(@PathVariable int id, Model model, HttpSession session){
        Folder currentFolder = navigationService.enterFolderById(id);
        historyHelper.removeFromHistoryList(session, currentFolder.getAncestorFolder());
        model.addAttribute("rootFolder", currentFolder);
        return "HomePage";
    }


}
