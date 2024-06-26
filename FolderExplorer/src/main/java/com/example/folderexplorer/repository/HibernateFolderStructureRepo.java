package com.example.folderexplorer.repository;

import com.example.folderexplorer.exceptions.EntityNotFoundException;
import com.example.folderexplorer.models.Folder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateFolderStructureRepo implements FolderStructureRepo {

    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateFolderStructureRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Folder getFolderById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Folder folder = session.get(Folder.class, id);
            if (folder == null) {
                throw new EntityNotFoundException("Folder", id);
            }
            return folder;
        }
    }

    @Override
    public Folder getRoot() {
        return getFolderById(1);
    }

    @Override
    public Folder createFolder(Folder folder) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(folder);
            session.getTransaction().commit();
        }
        return folder;
    }

    @Override
    public void deleteFolder(Folder folder) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(folder);
            session.getTransaction().commit();
        }
    }

    @Override
    public Folder updateFolder(Folder folder) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(folder);
            session.getTransaction().commit();
        }
        return folder;
    }
}
