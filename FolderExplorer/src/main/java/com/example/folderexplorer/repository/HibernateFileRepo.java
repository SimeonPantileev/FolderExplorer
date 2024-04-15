package com.example.folderexplorer.repository;

import com.example.folderexplorer.exceptions.EntityNotFoundException;
import com.example.folderexplorer.models.File;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateFileRepo implements FileRepo {
    private final SessionFactory sessionFactory;

    public HibernateFileRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public File getFileByFileAddress(String fileAddress) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM File WHERE fileAddress = :file_address";
            Query<File> query = session.createQuery(hql, File.class);
            query.setParameter("file_address", fileAddress);
            File file = query.uniqueResult();
            if (file == null) {
                throw new EntityNotFoundException("File", "address", fileAddress);
            }
            return file;
        }
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
    public File createFile(File file) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(file);
            session.getTransaction().commit();
        }
        return file;
    }

    @Override
    public void deleteFile(File file) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(file);
            session.getTransaction().commit();
        }
    }

    @Override
    public File updateFile(File file) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(file);
            session.getTransaction().commit();
        }
        return file;
    }
}
