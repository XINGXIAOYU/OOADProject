package ooad.dao;

import ooad.bean.Assignment;
import ooad.bean.ModuleAssignment;
import ooad.common.exceptions.ForeignKeyConstraintException;
import ooad.common.exceptions.NoSuchEntryException;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
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
    @Rule
    public final ExpectedException exception = ExpectedException.none();


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
        ModuleAssignment moduleAssignment = moduleAssignmentDAO.get(2);
        ModuleAssignment shouldBe = new ModuleAssignment(1, 1,2);
        assertEquals(shouldBe.toString(), moduleAssignment.toString());
    }

    @Test(expected = NoSuchEntryException.class)
    public void cGetNonExist() throws Exception {
        moduleAssignmentDAO.get(Integer.MAX_VALUE);
    }

    @Test
    public void dSave() throws Exception {
        ModuleAssignment moduleAssignment = new ModuleAssignment(1, 2);
        int id = moduleAssignmentDAO.save(moduleAssignment);
        assertNotEquals(-1, id);
        ModuleAssignment moduleAssignment1 = moduleAssignmentDAO.get(id);
        assertEquals(moduleAssignment.toString(), moduleAssignment1.toString());
    }

    @Test(expected = ForeignKeyConstraintException.class)
    public void dSaveFail() throws Exception {
        moduleAssignmentDAO.save(new ModuleAssignment(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    @Test
    public void eDelete() throws Exception {
        ModuleAssignment toDelete = new ModuleAssignment(1, 1);
        int id = moduleAssignmentDAO.save(toDelete);
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
        //TODO: more
        Assignment assignment = new Assignment(1, "test assignment", "assignment for test");
        assertEquals(assignment.toString(), answer);
    }

}