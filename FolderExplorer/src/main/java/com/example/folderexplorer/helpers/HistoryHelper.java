package com.example.folderexplorer.helpers;

import com.example.folderexplorer.models.Folder;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public class HistoryHelper {

    public void createHistoryList(HttpSession session) {
        Stack<Folder> history = new Stack<>();
        session.setAttribute("historyList", history);
    }

    @SuppressWarnings("unchecked")
    public void addToHistoryList(HttpSession session, Folder folder) {
        if (session.getAttribute("historyList") == null) {
            createHistoryList(session);
        }
        Stack<Folder> folderStack = (Stack<Folder>) session.getAttribute("historyList");
        folderStack.push(folder);
        session.setAttribute("historyList", folderStack);
    }

    @SuppressWarnings("unchecked")
    public void removeFromHistoryList(HttpSession session, Folder folder){
        if (session.getAttribute("historyList") == null) {
            createHistoryList(session);
            return;
        }
        Stack<Folder> folderStack = (Stack<Folder>) session.getAttribute("historyList");
        while(folderStack.peek() != folder){
            folderStack.pop();
            if(folderStack.size() == 0){
                break;
            }
        }
        session.setAttribute("historyList", folderStack);
    }


}