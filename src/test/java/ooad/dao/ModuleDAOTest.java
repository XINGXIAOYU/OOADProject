package ooad.dao;

import ooad.bean.Module;
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
 * Created by mayezhou on 2017/6/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModuleDAOTest {
    @Resource
    ModuleDAO moduleDAO;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void agetModuleDao() throws Exception {
        assertNotNull(moduleDAO);
    }

    @Test
    public void bgetSessionFactory() throws Exception {
        assertNotNull(moduleDAO.getSessionFactory());
    }

    @Test
    public void bgetCompanyDAO() throws Exception {
        assertNotNull(moduleDAO.getCompanyDAO());
    }

    @Test
    public void bgetAssignmentDAO() throws Exception {
        assertNotNull(moduleDAO.getCompanyDAO());
    }

    @Test
    public void cfindModule() throws Exception {
        List<Module> modules = moduleDAO.findModule("test module");
        String answer = "";
        for (Module module:
             modules) {
            answer += module;
        }
        Module module = new Module(1, "test module", "module for test", Module.UNPUBLISHED);
        assertEquals(module.toString(), answer);
    }

    @Test
    public void cfindModuleById() throws Exception {
        Module moduleR = moduleDAO.findModule(1);
        Module module = new Module(1, "test module", "module for test", Module.UNPUBLISHED);
        assertEquals(moduleR.toString(), module.toString());
    }

    @Test(expected = NoSuchEntryException.class)
    public void cfindNonExistModule() throws Exception {
        moduleDAO.findModule(Integer.MAX_VALUE);
    }

    @Test
    public void cgetModules() throws Exception {
        List moduleList = moduleDAO.getModules();
        String answer = "";
        for (Object aModuleList : moduleList) {
            Module module = (Module) aModuleList;
            answer += module;
        }
        Module module = new Module(1, "test module", "module for test", Module.UNPUBLISHED);
        assertEquals(answer, module.toString());
    }

    @Test
    public void daddModule() throws Exception {
        Module moduleT = new Module("Test", "Test");
        int id = moduleDAO.addModule(moduleT);
        assertNotEquals(id, -1);
        Module module = moduleDAO.findModule(id);
        moduleT.setId(id);
        assertEquals(module.toString(), moduleT.toString());
    }

    @Test
    public void edeleteModule() throws Exception {
        Module toDelete = new Module("for delete", "hehe");
        int id = moduleDAO.addModule(toDelete);
        moduleDAO.deleteModule(id);
        exception.expect(NoSuchEntryException.class);
        exception.expectMessage(id+"");
        moduleDAO.findModule(id);
    }

    @Test
    public void updateModule() throws Exception {
        Module forUpdate = new Module("for update", "nothing");
        int id = moduleDAO.addModule(forUpdate);
        forUpdate.setId(id);
        moduleDAO.updateModule(id, "new update", "lala land");
        forUpdate.setName("new update");
        forUpdate.setDescription("lala land");
        Module now = moduleDAO.findModule(id);
        assertEquals(forUpdate.toString(), now.toString());
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