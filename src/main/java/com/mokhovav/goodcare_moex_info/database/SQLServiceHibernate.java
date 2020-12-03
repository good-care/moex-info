package com.mokhovav.goodcare_moex_info.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SQLServiceHibernate implements SQLService {

    private final SessionFactory sessionFactory;

    public SQLServiceHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int save(Object object) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int id = (int) session.save(object);
        transaction.commit();
        session.close();
        return id;
    }

    @Override
    public Object update(Object object) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Object obj = session.merge(object);
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public boolean delete(Object object) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(object);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Object getById(int id, Class c) {
        Session session = sessionFactory.openSession();
        Object result = session.get(c, id);
        session.close();
        return result;
    }

    @Override
    public Object findObject(String text) {
        Session session = sessionFactory.openSession();
        List<?> list = session.createQuery(text).list();
        session.close();
        return list.isEmpty() ? null : list.get(0);
    }


}
