package ooad.dao;

import org.hibernate.SessionFactory;

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

}
