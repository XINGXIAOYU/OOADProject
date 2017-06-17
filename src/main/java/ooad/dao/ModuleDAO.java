package ooad.dao;

import ooad.bean.Module;
import ooad.common.exceptions.ForeignKeyConstraintException;
import ooad.common.exceptions.NoSuchEntryException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by mayezhou on 2017/6/7.
 */
public class ModuleDAO {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List getModulesByName(String moduleName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List results = null;
        try {
            transaction = session.beginTransaction();
            results = session.createQuery("FROM Module M WHERE M.name = :moduleName").setParameter("moduleName", moduleName).list();
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public List getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List results = null;
        try {
            transaction = session.beginTransaction();
            results = session.createQuery("FROM Module").list();
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public int save(Module module) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        int id = -1;
        try {
            transaction = session.beginTransaction();
            id = (Integer) session.save(module);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    public void delete(int moduleId) throws NoSuchEntryException, ForeignKeyConstraintException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Module module = session.get(Module.class, moduleId);
            session.delete(module);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } catch (PersistenceException e) {
            Throwable throwable = e.getCause();
            if (throwable instanceof ConstraintViolationException) {
                throw new ForeignKeyConstraintException("The module is published, cannot be deleted!");
            } else {
                throw e;
            }
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("attempt to create delete event with null entity")) {
                throw new NoSuchEntryException();
            } else {
                throw e;
            }
        } finally {
            session.close();
        }
    }

    public void update(int moduleId, String moduleName, String moduleContent) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Module module = session.get(Module.class, moduleId);
            module.setName(moduleName);
            module.setDescription(moduleContent);
            session.update(module);
            tx.commit();
        } catch (NullPointerException e) {
            throw new NoSuchEntryException(moduleId+"");
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("attempt to create delete event with null entity")) {
                throw new NoSuchEntryException(moduleId+"");
            } else {
                throw e;
            }
        } finally {
            session.close();
        }
    }

    public void update(int moduleId, String status) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Module module = session.get(Module.class, moduleId);
            module.setModuleStatus(status);
            session.update(module);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("attempt to create delete event with null entity")) {
                throw new NoSuchEntryException();
            } else {
                throw e;
            }
        } finally {
            session.close();
        }
    }

    public Module get(int id) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Module module = null;
        try {
            transaction = session.beginTransaction();
            module = session.get(Module.class, id);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        if (module == null) {
            throw new NoSuchEntryException(id+"");
        }
        return module;
    }

}
