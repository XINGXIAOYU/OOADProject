package ooad.service;

import ooad.bean.Assignment;
import ooad.common.Role;
import ooad.common.exceptions.AuthorityException;
import ooad.common.exceptions.NoSuchEntryException;
import ooad.service.impl.AssignmentService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xingxiaoyu on 17/6/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AssignmentServiceTest {
    @Resource
    AssignmentService assignmentService;

    @Test
    public void testFindAssignment() {
        List<Assignment> assignments = assignmentService.findAssignment("test assignment");
        assert assignments.size() > 0;
        assignments = assignmentService.findAssignment("There is no such assignment");
        assert assignments.size() == 0;
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
    }

    @Test(expected = AuthorityException.class)
    public void testAuthority() throws Exception {
        assignmentService.newAssignment(Role.Company, "new one", "company new");
    }

    @Test
    public void ztestDeleteAssignment() throws Exception {
        assignmentService.deleteAssignment(Role.Admin, 2);
        List<Assignment> assignments = assignmentService.findAssignment("new one");
        assert assignments.size() == 0;
    }

    @Test(expected = NoSuchEntryException.class)
    public void zztestDeleteNonExistAssignment() throws Exception {
        assignmentService.deleteAssignment(Role.Admin, 2);
    }
}
