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
 * Created by mayezhou on 2017/6/17.
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
    public void getDAO() throws Exception {
        assertNotNull(moduleDAO);
    }

    @Test
    public void getSessionFactory() throws Exception {
        assertNotNull(moduleDAO.getSessionFactory());
    }

    @Test
    public void get() throws Exception {
        Module moduleR = moduleDAO.get(1);
        Module module = new Module(1, "test module", "module for test", Module.UNPUBLISHED);
        assertEquals(moduleR.toString(), module.toString());
    }

    @Test(expected = NoSuchEntryException.class)
    public void getNonExist() throws Exception {
        moduleDAO.get(Integer.MAX_VALUE);
    }

    @Test
    public void getModulesByName() throws Exception {
        List<Module> modules = moduleDAO.getModulesByName("test module");
        String answer = "";
        for (Module module:
                modules) {
            answer += module;
        }
        //TODO: more
        Module module = new Module(1, "test module", "module for test", Module.UNPUBLISHED);
        assertEquals(module.toString(), answer);
        modules = moduleDAO.getModulesByName("NonExist");
        assertNull(modules);
    }

    @Test
    public void getAll() throws Exception {
        List moduleList = moduleDAO.getAll();
        String answer = "";
        for (Object aModuleList : moduleList) {
            Module module = (Module) aModuleList;
            answer += module;
        }
        //TODO: more
        Module module = new Module(1, "test module", "module for test", Module.UNPUBLISHED);
        assertEquals(answer, module.toString());
    }

    @Test
    public void save() throws Exception {
        Module moduleT = new Module("Test", "Test");
        int id = moduleDAO.save(moduleT);
        assertNotEquals(id, -1);
        Module module = moduleDAO.get(id);
        moduleT.setId(id);
        assertEquals(module.toString(), moduleT.toString());
    }

    @Test
    public void delete() throws Exception {
        Module toDelete = new Module("for delete", "hehe");
        int id = moduleDAO.save(toDelete);
        moduleDAO.delete(id);
        exception.expect(NoSuchEntryException.class);
        exception.expectMessage(id+"");
        moduleDAO.get(id);
    }

    @Test(expected = NoSuchEntryException.class)
    public void deleteNonExist() throws Exception {
        moduleDAO.delete(Integer.MAX_VALUE);
    }

    @Test
    public void update() throws Exception {
        Module forUpdate = new Module("for update", "nothing");
        int id = moduleDAO.save(forUpdate);
        forUpdate.setId(id);
        moduleDAO.update(id, "new update", "lala land");
        forUpdate.setName("new update");
        forUpdate.setDescription("lala land");
        Module now = moduleDAO.get(id);
        assertEquals(forUpdate.toString(), now.toString());
    }

    @Test(expected = NoSuchEntryException.class)
    public void updateNonExist() throws Exception {
        moduleDAO.update(Integer.MAX_VALUE, "new update", "lala land");
    }

    @Test
    public void update1() throws Exception {
        //TODO
    }

}