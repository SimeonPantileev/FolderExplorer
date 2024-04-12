package com.example.folderexplorer.helpers;

import com.example.folderexplorer.models.dtos.FileDto;
import com.example.folderexplorer.models.File;
import com.example.folderexplorer.service.Navigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FileMapper {
    private final Navigation navigation;

    @Autowired
    public FileMapper(Navigation navigation) {
        this.navigation = navigation;
    }

    public File fromDto(FileDto dto){
        File file = new File();
        file.setName(dto.getName().orElse("New Folder"));
        //ToDo think of removing the use of the service here
        file.setFolder(navigation.enterFolderById(dto.getFolderId()));
        return file;
    }
}
