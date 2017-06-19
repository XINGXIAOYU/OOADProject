package ooad.service;

import ooad.bean.Assignment;
import ooad.common.Role;
import ooad.common.exceptions.AuthorityException;
import ooad.service.impl.AssignmentService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
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

/**
 * Created by xingxiaoyu on 17/6/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AssignmentServiceTest {
    @Resource
    AssignmentService assignmentService;
    @Resource
    SessionFactory sessionFactory;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private static boolean setUpIsDone = false;

    @Before
    public void Clean() {
        if (setUpIsDone) return;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            String sql = "DELETE FROM assignment WHERE idassignment != 1";
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
    public void testFindAssignment() {
        List<Assignment> assignments = assignmentService.findAssignment("test assignment");
        assert assignments.size() > 0;
        assignments = assignmentService.findAssignment("There is no such assignment");
        assert assignments.size() == 0;
    }

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
    public void testZDeleteAssignment() throws Exception {//after testNewAssignment()
        List<Assignment> assignments = assignmentService.getAssignments();
        assignmentService.deleteAssignment(Role.Admin, assignments.get(assignments.size() - 1).getId());
        List<Assignment> assignments2 = assignmentService.findAssignment(assignments.get(assignments.size() - 1).getName());
        assert assignments2.size() == 0;
    }


    @Test(expected = AuthorityException.class)
    public void testDeleteAssignmentAuthority() throws Exception {
        assignmentService.deleteAssignment(Role.Company, 1);

    }

}
