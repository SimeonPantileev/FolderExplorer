package com.example.folderexplorer.repository;

import com.example.folderexplorer.models.Folder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StructureRepoImpl implements StructureRepo{

    private final List<Folder> structure;

    public StructureRepoImpl() {
        structure = new ArrayList<>();
        Folder folderFirst = new Folder(1, "Root folder");
        Folder folderSecond = new Folder(2, "A nested folder");
        Folder folderThird = new Folder(3, "Child folder 1");
        Folder folderFourth = new Folder(4, "Child folder 2");

        folderFirst.getFolders().add(folderSecond);
        folderSecond.getFolders().add(folderThird);
        folderSecond.getFolders().add(folderFourth);

        folderSecond.setAncestorFolder(folderFirst);
        folderThird.setAncestorFolder(folderSecond);
        folderFourth.setAncestorFolder(folderSecond);

        structure.add(folderFirst);
        structure.add(folderSecond);
        structure.add(folderThird);
        structure.add(folderFourth);
    }

    @Override
    public Folder getFolderById(int id) {
        return structure.stream()
                .filter(folder1 -> folder1.getFolderId() == id)
                .findFirst().orElseThrow();
    }

    @Override
    public Folder getRoot() {
        return structure.get(0);
    }
}
