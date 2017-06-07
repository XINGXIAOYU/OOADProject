package ooad;

import ooad.bean.Assignment;
import ooad.dao.AssignmentDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        HelloService helloService = context.getBean(HelloService.class);
        System.out.println(helloService.sayHello());
        AssignmentDAO assignmentDAO = new AssignmentDAO();
        System.out.println(assignmentDAO.getSessionFactory());
        Assignment assignment = new Assignment("test", "test content");
        assignmentDAO.save(assignment);
        assignmentDAO.getAll();
    }
}
