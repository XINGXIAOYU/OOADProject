package ooad.service;

import ooad.bean.Assignment;
import ooad.common.Role;
import ooad.common.exceptions.AuthorityException;
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

    /**
     * 测试查找检查项目
     * 数据库中存在则size>0
     */

    @Test
    public void testFindAssignment() {
        List<Assignment> assignments = assignmentService.findAssignment("test assignment");
        assert assignments.size() > 0;
        assignments = assignmentService.findAssignment("There is no such assignment");
        assert assignments.size() == 0;
    }

    /**
     * 测试所有检查项目
     * 检测名称和内容是否与数据库中测试数据一致
     */

    @Test
    public void testGetAssignments() {
        List<Assignment> assignments = assignmentService.getAssignments();
        if (assignments.size() > 0) {
            String name = (String) assignments.get(0).getName();
            String content = (String) assignments.get(0).getContent();
            assert name.equals("test assignment");
            assert content.equals("assignment for test");
        }
    }

    /**
     * 测试新建检查项目
     * 检测是否新建成功，是否添加到数据库中
     * @throws Exception
     */

    @Test
    public void testNewAssignment() throws Exception {
        assert assignmentService.newAssignment(Role.Admin, "new one", "admin new");
        List<Assignment> assignments = assignmentService.getAssignments();
        if (assignments.size() > 0) {
            String name = (String) assignments.get(assignments.size() - 1).getName();
            String content = (String) assignments.get(assignments.size() - 1).getContent();
            assert name.equals("new one");
            assert content.equals("admin new");
        }
    }

    /**
     * 检测新建检查项目的权限（只有Admin可以新建检查项目）
     * @throws Exception
     */

    @Test(expected = AuthorityException.class)
    public void testAuthority() throws Exception {
       assert assignmentService.newAssignment(Role.Company, "new one", "company new");
    }

    /**
     * 检测删除检查项目
     * @throws Exception
     */

    @Test
    public void testDeleteAssignment() throws Exception {
        assignmentService.newAssignment(Role.Admin, "delete one", "admin delete");
        List<Assignment> assignments = assignmentService.getAssignments();
        Assignment assignment = assignments.get(assignments.size()-1);
        assert assignment.getName().equals("delete one");
        assert assignment.getContent().equals("admin delete");
        assert assignmentService.deleteAssignment(Role.Admin, assignments.get(assignments.size() - 1).getId());
        List<Assignment> assignments2 = assignmentService.findAssignment(assignments.get(assignments.size() - 1).getName());
        assert assignments2.size() == 0;
    }

    /**
     * 检测删除检查项目的权限（只有Admin可以删除检查项目）
     * @throws Exception
     */
    @Test(expected = AuthorityException.class)
    public void testDeleteAssignmentAuthority() throws Exception {
        assignmentService.deleteAssignment(Role.Company, 1);

    }

}
