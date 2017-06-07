package dao;

import org.hibernate.SessionFactory;

import javax.annotation.Resource;

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
        this.sessionFactory = sessionFactory;
    }

}
