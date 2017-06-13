package ooad.service;

import ooad.bean.Assignment;
import ooad.common.Role;
import ooad.common.exceptions.AuthorityException;
import ooad.common.exceptions.NoSuchEntryException;
import ooad.service.impl.AssignmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xingxiaoyu on 17/6/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class AssignmentServiceTest {
    @Resource
    AssignmentService assignmentService;

    @Test
    public void testFindAssignment() {
        Assignment dbAssignment = assignmentService.findAssignment("test assignment");
        Assignment stdAssignment = new Assignment(1, "test assignment", "assignment for test");
        assert dbAssignment.toString().equals(stdAssignment.toString());

    }

    @Test
    public void testGetAssignments() throws Exception {
        List<Assignment> assignments = assignmentService.getAssignments();
        if (assignments.size() > 0) {
            String name = (String) assignments.get(0).getName();
            String content = (String) assignments.get(0).getContent();
            assert name.equals("test assignment");
            assert content.equals("assignment for test");
        }
    }

    @Test
    public void testNewAssignment() throws Exception {
        assignmentService.newAssignment(Role.Admin, "new one", "admin new");
        List<Assignment> assignments = assignmentService.getAssignments();
        if (assignments.size() > 0) {
            String name = (String) assignments.get(assignments.size() - 1).getName();
            String content = (String) assignments.get(assignments.size() - 1).getContent();
            assert name.equals("new one");
            assert content.equals("admin new");
        }
        try {
            assignmentService.newAssignment(Role.Company, "new one", "company new");
        } catch (AuthorityException e) {
            assert e instanceof AuthorityException;
        }

    }

    @Test
    public void testDeleteAssignment() throws Exception {
        try {
            assignmentService.deleteAssignment(Role.Admin, 2);
            Assignment assignment = assignmentService.findAssignment("new one");
        } catch (NoSuchEntryException e) {
            assert e instanceof NoSuchEntryException;
        }

        try {
            assignmentService.deleteAssignment(Role.Admin, 2);
        } catch (NoSuchEntryException e) {
            assert e instanceof NoSuchEntryException;
        }
    }
}
