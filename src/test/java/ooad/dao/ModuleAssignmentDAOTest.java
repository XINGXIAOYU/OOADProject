package ooad.dao;

import ooad.bean.Assignment;
import ooad.bean.ModuleAssignment;
import ooad.common.exceptions.ForeignKeyConstraintException;
import ooad.common.exceptions.NoSuchEntryException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mayezhou on 2017/6/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModuleAssignmentDAOTest {
    @Resource
    ModuleAssignmentDAO moduleAssignmentDAO;
    @Resource
    SessionFactory sessionFactory;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private static int toDelete;
    private static boolean setUpIsDone = false;

    @Before
    public void Clean() {
        if (setUpIsDone) return;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            String sql = "DELETE FROM module_assignment WHERE idmodule_assignment != 1";
            Query query = session.createSQLQuery(sql);
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);
            tx.commit();
            setUpIsDone = true;
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    @Test
    public void aGetDAO() throws Exception {
        assertNotNull(moduleAssignmentDAO);
    }

    @Test
    public void aGetSessionFactory() throws Exception {
        assertNotNull(moduleAssignmentDAO.getSessionFactory());
    }

    @Test
    public void bGetAssignmentDAO() throws Exception {
        assertNotNull(moduleAssignmentDAO.getAssignmentDAO());
    }

    @Test
    public void cGet() throws Exception {
        ModuleAssignment moduleAssignment = moduleAssignmentDAO.get(1);
        ModuleAssignment shouldBe = new ModuleAssignment(1, 1,1);
        assertEquals(shouldBe.toString(), moduleAssignment.toString());
    }

    @Test(expected = NoSuchEntryException.class)
    public void cGetNonExist() throws Exception {
        moduleAssignmentDAO.get(Integer.MAX_VALUE);
    }

    @Test
    public void dSave() throws Exception {
        ModuleAssignment moduleAssignment = new ModuleAssignment(8, 2);
        int id = moduleAssignmentDAO.save(moduleAssignment);
        assertNotEquals(-1, id);
        ModuleAssignment moduleAssignment1 = moduleAssignmentDAO.get(id);
        assertEquals(moduleAssignment.toString(), moduleAssignment1.toString());
        toDelete = id;
    }

    @Test(expected = ForeignKeyConstraintException.class)
    public void dSaveFail() throws Exception {
        moduleAssignmentDAO.save(new ModuleAssignment(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    @Test
    public void eDelete() throws Exception {
        int id = toDelete;
        moduleAssignmentDAO.delete(id);
        exception.expect(NoSuchEntryException.class);
        exception.expectMessage(id+"");
        moduleAssignmentDAO.get(id);
    }

    @Test(expected = NoSuchEntryException.class)
    public void eDeleteNonExist() throws Exception {
        moduleAssignmentDAO.delete(Integer.MAX_VALUE);
    }


    @Test
    public void cGetAssignmentsOfModule() throws Exception {
        List<Assignment> assignments = moduleAssignmentDAO.getAssignmentsOfModule(1);
        String answer = "";
        for (Assignment assignment:
             assignments) {
            answer += assignment;
        }
        Assignment assignment = new Assignment(1, "test assignment", "assignment for test");
        assertEquals(assignment.toString(), answer);
    }

}