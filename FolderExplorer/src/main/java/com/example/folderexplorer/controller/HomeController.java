package com.example.folderexplorer.controller;

import com.example.folderexplorer.helpers.HistoryHelper;
import com.example.folderexplorer.models.Folder;
import com.example.folderexplorer.service.Navigation;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final Navigation navigationService;
    private final HistoryHelper historyHelper;

    public HomeController(Navigation navigationService, HistoryHelper historyHelper) {
        this.navigationService = navigationService;
        this.historyHelper = historyHelper;
    }

    @GetMapping
    public String showRootPage(Model model, HttpSession session) {

        Folder rootFolder = navigationService.getRoot();
        historyHelper.createHistoryList(session);
        model.addAttribute("rootFolder", rootFolder);
        return "FolderContent";
    }
}
