package com.example.folderexplorer.repository;

import com.example.folderexplorer.exceptions.EntityNotFoundException;
import com.example.folderexplorer.models.Folder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateStructureRepo implements StructureRepo {

    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateStructureRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Folder getFolderById(int id) {
        try(Session session = sessionFactory.openSession()){
            Folder folder = session.get(Folder.class, id);
            if(folder == null){
                throw new EntityNotFoundException("Folder", id);
            }
            return folder;
        }
    }

    @Override
    public Folder getRoot() {
        return getFolderById(1);
        }
}
