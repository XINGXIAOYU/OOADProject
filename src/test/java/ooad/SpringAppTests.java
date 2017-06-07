package ooad;

import ooad.bean.Assignment;
import ooad.dao.AssignmentDAO;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class SpringAppTests {
    @Autowired
    private HelloService helloService;

    @Test
    public void testSayHello() {
        Assert.assertEquals("Hello world!", helloService.sayHello());
        AssignmentDAO assignmentDAO = new AssignmentDAO();
        Assignment assignment = new Assignment("test", "test content");
        assignmentDAO.save(assignment);
        assignmentDAO.getAll();
    }
}
