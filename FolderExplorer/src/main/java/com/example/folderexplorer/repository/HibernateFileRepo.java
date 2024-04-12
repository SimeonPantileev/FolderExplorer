package com.example.folderexplorer.repository;

import com.example.folderexplorer.exceptions.EntityNotFoundException;
import com.example.folderexplorer.models.Folder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.example.folderexplorer.models.File;

@Repository
public class HibernateFileRepo implements FileRepo{
    private final SessionFactory sessionFactory;

    public HibernateFileRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public File getFileByFileAddress(String fileAddress) {
        return null;
    }

    @Override
    public File getFileById(int fileId) {
        try (Session session = sessionFactory.openSession()) {
            File file = session.get(File.class, fileId);
            if (file == null) {
                throw new EntityNotFoundException("File", fileId);
            }
            return file;
        }
    }

    @Override
    public void createFile(File file) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(file);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteFile(File file) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(file);
            session.getTransaction().commit();
        }
    }
}
