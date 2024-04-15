package com.example.folderexplorer.helpers;

import com.example.folderexplorer.models.File;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class FileMapper {
    public File toFile(Optional<String> name) {
        String currentName = "New File";
        if (name.isPresent() && name.get().length() != 0) {
            currentName = name.get();
        }
        File file = new File();
        file.setName(currentName);
        return file;
    }
}
