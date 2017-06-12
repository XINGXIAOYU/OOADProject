package ooad.dao;

import ooad.bean.Assignment;
import ooad.bean.Module;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mayezhou on 2017/6/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class ModuleDAOTest {
    @Resource
    ModuleDAO moduleDAO;

    @Test
    public void getModuleDao() throws Exception {
        assertNotNull(moduleDAO);
    }

    @Test
    public void getSessionFactory() throws Exception {
        assertNotNull(moduleDAO.getSessionFactory());
    }

    @Test
    public void getCompanyDAO() throws Exception {
        assertNotNull(moduleDAO.getCompanyDAO());
    }

    @Test
    public void getAssignmentDAO() throws Exception {
        assertNotNull(moduleDAO.getCompanyDAO());
    }

    @Test
    public void findModule() throws Exception {

    }

    @Test
    public void getModules() throws Exception {
        List moduleList = moduleDAO.getModules();
        for (Object aModuleList : moduleList) {
            Module module = (Module) aModuleList;
            System.out.println(module);
        }
    }

    @Test
    public void addModule() throws Exception {
        Module module = new Module("Test", "Test");
        int id = moduleDAO.addModule(module);
        assertNotEquals(id, -1);
    }

    @Test
    public void deleteModule() throws Exception {
    }

    @Test
    public void updateModule() throws Exception {
    }

    @Test
    public void addAssignmentToModule() throws Exception {
    }

    @Test
    public void deleteAssignmentToModule() throws Exception {
    }

    @Test
    public void getModuleAssignments() throws Exception {
    }

    @Test
    public void addModuleCompany() throws Exception {
    }

    @Test
    public void getModuleCompanys() throws Exception {
    }

    @Test
    public void getModuleProcesses() throws Exception {
    }

    @Test
    public void getCModuleList() throws Exception {
    }

    @Test
    public void findModule1() throws Exception {
    }

    @Test
    public void updateStatus() throws Exception {
    }

}