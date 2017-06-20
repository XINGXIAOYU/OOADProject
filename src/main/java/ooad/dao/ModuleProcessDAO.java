package ooad.dao;

import ooad.bean.Company;
import ooad.bean.ModuleProcess;
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
public class ModuleProcessDAO {
    private SessionFactory sessionFactory;
    private CompanyDAO companyDAO;
    private AssignmentDAO assignmentDAO;
    private ModuleDAO moduleDAO;

    public ModuleDAO getModuleDAO() {
        return moduleDAO;
    }

    public void setModuleDAO(ModuleDAO moduleDAO) {
        this.moduleDAO = moduleDAO;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public CompanyDAO getCompanyDAO() {
        return companyDAO;
    }

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public AssignmentDAO getAssignmentDAO() {
        return assignmentDAO;
    }

    public void setAssignmentDAO(AssignmentDAO assignmentDAO) {
        this.assignmentDAO = assignmentDAO;
    }

    public int save(ModuleProcess moduleProcess) throws ForeignKeyConstraintException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        int id = -1;
        try {
            transaction = session.beginTransaction();
            id = (Integer) session.save(moduleProcess);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            throw new ForeignKeyConstraintException("The module or company is undefined!");
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    public void update(int moduleProcessId, String date, String completed) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            ModuleProcess moduleProcess = session.get(ModuleProcess.class, moduleProcessId);
            moduleProcess.setCompany_finish_time(date);
            moduleProcess.setStatus(completed);
            session.update(moduleProcess);
            tx.commit();
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new NoSuchEntryException(moduleProcessId+"");
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

    public ModuleProcess get(int moduleProcessId) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        ModuleProcess moduleProcess = null;
        try{
            tx = session.beginTransaction();
            moduleProcess = session.get(ModuleProcess.class, moduleProcessId);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }  finally {
            session.close();
        }
        if (moduleProcess == null) {
            throw new NoSuchEntryException("idModuleProcess = " + moduleProcessId);
        }
        return moduleProcess;
    }

    public List<Company> getCompaniesOfPublishedModule(int moduleId) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Company> results = new ArrayList<Company>();
        try {
            transaction = session.beginTransaction();
            String sql = "SELECT company_id FROM moduleProcess WHERE module_id = :moduleId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            query.setParameter("moduleId", moduleId);
            List result = query.list();
            transaction.commit();
            for (Object object :
                    result) {
                Map row = (Map)object;
                int id = (Integer) row.get("company_id");
                Company company = companyDAO.getCompany(id);
                results.add(company);
            }
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public List getModuleProcesses(int moduleId) { //TODO: module.status
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List results = null;
        try {
            transaction = session.beginTransaction();
            results = session.createQuery("FROM ModuleProcess M WHERE M.module_id = :moduleId").setParameter("moduleId", moduleId).list();
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public List<ModuleProcess> getModuleProcessesOfCompany(int companyId) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<ModuleProcess> results = new ArrayList<ModuleProcess>();
        try {
            transaction = session.beginTransaction();
            String sql = "SELECT idmoduleProcess FROM moduleProcess WHERE company_id = :companyId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            query.setParameter("companyId", companyId);
            List result = query.list();
            transaction.commit();
            for (Object object :
                    result) {
                Map row = (Map)object;
                int id = (Integer) row.get("idmoduleProcess");
                ModuleProcess moduleProcess = get(id);
                results.add(moduleProcess);
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
