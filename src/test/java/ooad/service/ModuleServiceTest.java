package ooad.service;

import ooad.bean.Module;
import ooad.service.impl.ModuleService;
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
public class ModuleServiceTest {
    @Resource
    ModuleService moduleService;

    @Test
    public void testFindModule() throws Exception {
        List<Module> modules = moduleService.findModule("module test");
        Module dbModule = modules.get(0);
        Module stdModule = new Module(1,"module test","test for module", Module.UNPUBLISHED);
        assert  dbModule.toString().equals(stdModule.toString());
    }

    @Test
    public void testGetModules() throws Exception {

    }

    @Test
    public void testNewModule() throws Exception {

    }

    @Test
    public void testDeleteModule() throws Exception {

    }

    @Test
    public void testModifyModule() throws Exception {

    }

    @Test
    public void testAddAssignmentToModule() throws Exception {

    }

    @Test
    public void testDeleteAssignmentFromModule() throws Exception {

    }

    @Test
    public void testGetModuleAssignments() throws Exception {

    }

    @Test
    public void testPublishModule() throws Exception {

    }

    @Test
    public void testGetModuleCompanys() throws Exception {

    }

    @Test
    public void testGetModuleProcesses() throws Exception {


    }
}
