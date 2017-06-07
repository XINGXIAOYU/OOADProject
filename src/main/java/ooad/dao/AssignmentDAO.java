package ooad.dao;

import org.hibernate.*;
import ooad.bean.Assignment;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mayezhou on 2017/6/7.
 */
public class AssignmentDAO {
    @Resource
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        System.out.println(sessionFactory);
        this.sessionFactory = sessionFactory;
        System.out.println(this.sessionFactory);
        System.out.println("test");
    }

    public int save(Assignment assignment) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        int id = -1;
        try {
            transaction = session.beginTransaction();
            id = (Integer) session.save(assignment);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    public List<Assignment> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List results = null;
        try {
            transaction = session.beginTransaction();
            results = session.createQuery("FROM Assignment").list();
            for (Iterator iterator =
                 results.iterator(); iterator.hasNext();){
                Assignment assignment = (Assignment) iterator.next();
                System.out.println(assignment);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

}
