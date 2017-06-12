package ooad.dao;

import ooad.bean.Company;
import ooad.common.exceptions.NoSuchEntryException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayezhou on 2017/6/7.
 */
public class CompanyDAO {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Company getCompany(int id) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Company company = null;
        try {
            transaction = session.beginTransaction();
            company = session.get(Company.class, id);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        if (company == null) {
            throw new NoSuchEntryException();
        }
        return company;
    }

    public List<Company> getCompanys(){
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Company> results = new ArrayList<Company>();
        try {
            transaction = session.beginTransaction();
            results = session.createQuery("FROM Company" ).list();
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
