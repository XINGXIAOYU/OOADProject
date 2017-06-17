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
    public void getDAO() throws Exception {
        assertNotNull(moduleAssignmentDAO);
    }

    @Test
    public void getSessionFactory() throws Exception {
        assertNotNull(moduleAssignmentDAO.getSessionFactory());
    }

    @Test
    public void getAssignmentDAO() throws Exception {
        assertNotNull(moduleAssignmentDAO.getAssignmentDAO());
    }

    @Test
    public void get() throws Exception {
        ModuleAssignment moduleAssignment = moduleAssignmentDAO.get(2);
        ModuleAssignment shouldBe = new ModuleAssignment(1, 1);
        assert moduleAssignment.equals(shouldBe);
    }

    @Test(expected = NoSuchEntryException.class)
    public void getNonExist() throws Exception {
        moduleAssignmentDAO.get(Integer.MAX_VALUE);
    }

    @Test
    public void save() throws Exception {
        ModuleAssignment moduleAssignment = new ModuleAssignment(1, 1);
        int id = moduleAssignmentDAO.save(moduleAssignment);
        assertNotEquals(-1, id);
        ModuleAssignment moduleAssignment1 = moduleAssignmentDAO.get(id);
        assertEquals(moduleAssignment.toString(), moduleAssignment1.toString());
    }

    @Test(expected = ForeignKeyConstraintException.class)
    public void saveFail() throws Exception {
        ModuleAssignment moduleAssignment = new ModuleAssignment(Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals(-1, moduleAssignmentDAO.save(moduleAssignment));
    }

    @Test
    public void delete() throws Exception {
        ModuleAssignment toDelete = new ModuleAssignment(1, 1);
        int id = moduleAssignmentDAO.save(toDelete);
        moduleAssignmentDAO.delete(id);
        exception.expect(NoSuchEntryException.class);
        exception.expectMessage(id+"");
        moduleAssignmentDAO.get(id);
    }

    @Test(expected = NoSuchEntryException.class)
    public void deleteNonExist() throws Exception {
        moduleAssignmentDAO.delete(Integer.MAX_VALUE);
    }


    @Test
    public void getAssignmentsOfModule() throws Exception {
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