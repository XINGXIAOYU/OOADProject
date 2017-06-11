package ooad.dao;

import ooad.common.exceptions.NoSuchEntryException;
import org.hibernate.*;
import ooad.bean.Assignment;

import java.util.Iterator;
import java.util.List;

/**
 * Created by mayezhou on 2017/6/7.
 */
public class AssignmentDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean addAssignment(Assignment assignment) {
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
        return id!=-1;
    }

    public List<Assignment> getAssignments() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List results = null;
        try {
            transaction = session.beginTransaction();
            results = session.createQuery("FROM Assignment").list();
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }


    public boolean deleteAssignment(int assignmentId) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Assignment assignment = session.get(Assignment.class, assignmentId);
            session.delete(assignment);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
            return true;
        }
    }

    public Assignment searchAssignment(String assignmentName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List results = null;
        try {
            transaction = session.beginTransaction();
            results = session.createQuery("FROM Assignment A WHERE A.name = :assignmentName").setParameter("assignmentName", assignmentName).list();
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (Assignment) results.get(0);
    }

    public Assignment searchAssignment(int id) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Assignment assignment = null;
        try {
            transaction = session.beginTransaction();
            assignment = session.get(Assignment.class, id);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        if (assignment == null) {
            throw new NoSuchEntryException();
        }
        return assignment;
    }
}
