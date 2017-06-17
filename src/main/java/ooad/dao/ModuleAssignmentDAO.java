package ooad.dao;

import ooad.bean.Assignment;
import ooad.bean.ModuleAssignment;
import ooad.common.exceptions.ForeignKeyConstraintException;
import ooad.common.exceptions.NoSuchEntryException;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by mayezhou on 2017/6/17.
 */
public class ModuleAssignmentDAO {
    private SessionFactory sessionFactory;
    private AssignmentDAO assignmentDAO;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public AssignmentDAO getAssignmentDAO() {
        return assignmentDAO;
    }

    public void setAssignmentDAO(AssignmentDAO assignmentDAO) {
        this.assignmentDAO = assignmentDAO;
    }

    public int save(ModuleAssignment newEntry) throws ForeignKeyConstraintException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        int id = -1;
        try {
            transaction = session.beginTransaction();
            id = (Integer) session.save(newEntry);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            throw new ForeignKeyConstraintException("The module or assignment is undefined!");
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    public void delete(int id) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            ModuleAssignment moduleAssignment = session.get(ModuleAssignment.class, id);
            session.delete(moduleAssignment);
            tx.commit();
        } catch (HibernateException e) {
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

    public ModuleAssignment get(int moduleAssignmentId) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        ModuleAssignment moduleAssignment = null;
        try{
            tx = session.beginTransaction();
            moduleAssignment = session.get(ModuleAssignment.class, moduleAssignmentId);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }  finally {
            session.close();
        }
        if (moduleAssignment == null) {
            throw new NoSuchEntryException("idModuleAssignment = " + moduleAssignmentId);
        }
        return moduleAssignment;
    }

    public List<Assignment> getAssignmentsOfModule(int moduleId) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Assignment> results = new ArrayList<Assignment>();
        try {
            transaction = session.beginTransaction();
            String sql = "SELECT assignment_id FROM module_assignment WHERE module_id = :moduleId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            query.setParameter("moduleId", moduleId);
            List result = query.list();
            transaction.commit();
            for (Object object :
                    result) {
                Map row = (Map)object;
                int id = (Integer) row.get("assignment_id");
                Assignment assignment = assignmentDAO.searchAssignment(id);
                results.add(assignment);
            }
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }
}
