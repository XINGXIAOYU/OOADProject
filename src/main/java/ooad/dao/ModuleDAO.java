package ooad.dao;

import ooad.bean.*;
import ooad.common.exceptions.NoSuchEntryException;
import org.hibernate.*;

import java.sql.Date;
import java.util.*;

/**
 * Created by mayezhou on 2017/6/7.
 */
public class ModuleDAO {
    private SessionFactory sessionFactory;
    private AssignmentDAO assignmentDAO;
    private CompanyDAO companyDAO;

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

    public List findModule(String moduleName) {
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

    public List getModules() {
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

    public int addModule(Module module) {
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

    public void deleteModule(int moduleId) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Module module = session.get(Module.class, moduleId);
            session.delete(module);
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

    public void updateModule(int moduleId, String moduleName, String moduleContent) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Module module = session.get(Module.class, moduleId);
            module.setName(moduleName);
            module.setDescription(moduleContent);
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


    public int addAssignmentToModule(ModuleAssignment newEntry) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        int id = -1;
        try {
            transaction = session.beginTransaction();
            id = (Integer) session.save(newEntry);//TODO: foreign key constraint, throw exception
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    public void deleteAssignmentToModule(int id) throws NoSuchEntryException {
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

    public List<Assignment> getModuleAssignments(int moduleId) throws NoSuchEntryException {
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
        if (results.size() == 0) {
            throw new NoSuchElementException("No assignment attached to this module!");
        }
        return results;
    }

    public boolean addModuleCompany(ModuleProcess moduleProcess) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        int id = -1;
        try {
            transaction = session.beginTransaction();
            id = (Integer) session.save(moduleProcess);//TODO: foreign key constraint
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id!=-1;
    }

    public List<Company> getModuleCompanys(int moduleId) throws NoSuchEntryException {
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

    public List<Module> getCModuleList(int companyId) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Module> results = new ArrayList<Module>();
        try {
            transaction = session.beginTransaction();
            String sql = "SELECT module_id FROM moduleProcess WHERE company_id = :companyId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            query.setParameter("companyId", companyId);
            List result = query.list();
            transaction.commit();
            for (Object object :
                    result) {
                Map row = (Map)object;
                int id = (Integer) row.get("module_id");
                Module module= findModule(id);
                results.add(module);
            }
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public Module findModule(int id) throws NoSuchEntryException {
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

    public void updateStatus(int moduleProcessId, Date date, String completed) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            ModuleProcess moduleProcess = session.get(ModuleProcess.class, moduleProcessId);
            moduleProcess.setCompany_finish_time(date);
            moduleProcess.setStatus(completed);
            session.update(moduleProcess);
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

    //TODO: invoke by service
    public void updateStatus(int moduleProcessId, String banned) throws NoSuchEntryException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            ModuleProcess moduleProcess = session.get(ModuleProcess.class, moduleProcessId);
            moduleProcess.setStatus(banned);
            session.update(moduleProcess);
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

    public ModuleProcess getModuleProcess(int moduleProcessId) throws NoSuchEntryException {
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
}
