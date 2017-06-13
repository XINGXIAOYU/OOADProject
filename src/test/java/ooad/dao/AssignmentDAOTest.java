package ooad.dao;

import ooad.bean.Assignment;
import ooad.common.exceptions.NoSuchEntryException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mayezhou on 2017/6/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AssignmentDAOTest {
    @Resource
    AssignmentDAO assignmentDAO;

    @Test
    public void agetAssignmentDAO() throws Exception{
        assertNotNull(assignmentDAO);
    }

    @Test
    public void agetSessionFactory() throws Exception {
        assertNotNull(assignmentDAO.getSessionFactory());
    }

    @Test
    public void baddAssignment() throws Exception {
        Assignment assignment = new Assignment("Test2", "Test");
        int newID = assignmentDAO.addAssignment(assignment);
        assertNotEquals(newID, -1);
    }

    @Test
    public void cgetAssignments() throws Exception {//TODO: must assert?
        List<Assignment> assignmentList = assignmentDAO.getAssignments();
        for (Assignment assignment : assignmentList) {
            System.out.println(assignment);
        }
    }

    @Test(expected = NoSuchEntryException.class)
    public void deleteNonExistAssignment() throws Exception {
        assignmentDAO.deleteAssignment(Integer.MAX_VALUE);
    }

    @Test
    public void deleteAssignment() throws Exception {
        int toDelete = assignmentDAO.addAssignment(new Assignment("Delete", "For Delete only"));
        assignmentDAO.deleteAssignment(toDelete);
    }

    @Test
    public void searchAssignment() throws Exception {
        Assignment assignment = assignmentDAO.searchAssignment(1);
        System.out.println(assignment);
        Assignment shouldBeAssignment = new Assignment(1, "test assignment", "assignment for test");
        assertEquals(shouldBeAssignment.toString(), assignment.toString());
    }

    @Test
    public void searchAssignmentByNameNone() throws Exception {
        List<Assignment> assignments = assignmentDAO.searchAssignment("Test");
        assertEquals(assignments.size(), 0);
    }

    @Test
    public void searchAssignmentByName() throws Exception {
        List<Assignment> assignments = assignmentDAO.searchAssignment("test assignment");
        String answer = "";
        for (Object o :
                assignments) {
            Assignment assignment = (Assignment) o;
            answer += assignment;
        }
        Assignment shouldBeAssignment = new Assignment(1, "test assignment", "assignment for test");
        assertEquals(answer, shouldBeAssignment.toString());
    }

}