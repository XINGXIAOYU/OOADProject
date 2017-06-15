package ooad.service;

import ooad.bean.Assignment;
import ooad.bean.Company;
import ooad.bean.Module;
import ooad.bean.ModuleProcess;
import ooad.common.Role;
import ooad.common.exceptions.AuthorityException;
import ooad.common.exceptions.NoSuchEntryException;
import ooad.service.impl.AssignmentService;
import ooad.service.impl.CompanyService;
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
    AssignmentService assignmentService;
    CompanyService companyService;

    @Test
    public void testFindModule() throws Exception {
        List<Module> modules = moduleService.findModule("test module");
        Module dbModule = modules.get(0);
        Module stdModule = new Module(1, "test module", "module for test", Module.UNPUBLISHED);
        assert dbModule.toString().equals(stdModule.toString());
    }

    @Test
    public void testGetModules() throws Exception {
        List<Module> modules = moduleService.getModules();
        if (modules.size() > 0) {
            assert modules.get(0).getId() == 1;
            assert modules.get(0).getName().equals("test module");
            assert modules.get(0).getDescription().equals("module for test");
        }
    }

    @Test
    public void testNewModule() throws Exception {
        moduleService.newModule(Role.Admin, "new module", "new module admin");
        List<Module> modules = moduleService.getModules();
        assert modules.size() > 0;
        Module addModule = modules.get(modules.size() - 1);
        assert addModule.getName().equals("new module");
        assert addModule.getDescription().equals("new module admin");

    }

    @Test(expected = AuthorityException.class)
    public void testNewModuleAuthority() throws Exception {
        moduleService.newModule(Role.Company, "new module", "new module admin");
    }

    @Test
    public void testDeleteModule() throws Exception {
        List<Module> modules = moduleService.getModules();
        moduleService.deleteModule(Role.Admin, modules.get(modules.size() - 1).getId());
        List<Module> module2 = moduleService.findModule(modules.get(modules.size() - 1).getName());
        assert module2.size() == 0;
    }

    @Test(expected = NoSuchEntryException.class)
    public void testDeleteModuleNoSuchEntry() throws Exception {
        moduleService.deleteModule(Role.Admin, -1);

    }

    @Test
    public void testModifyModule() throws Exception {
        List<Module> modules = moduleService.findModule("new module");
        Module module = modules.get(0);
        moduleService.modifyModule(Role.Admin, module.getId(), "new module", "modified_admin");
        List<Module> modules2 = moduleService.findModule("new module");
        assert modules2.size() > 0;
        assert modules2.get(0).getId() == module.getId();
        assert modules2.get(0).getName().equals("new module");
        assert modules2.get(0).getDescription().equals("modified_admin");
    }

    @Test(expected = AuthorityException.class)
    public void testModifyModuleAuthority() throws Exception {
        List<Module> modules = moduleService.findModule("new module");
        Module module = modules.get(0);
        moduleService.modifyModule(Role.Company, module.getId(), "new module", "modified_company");
    }

    @Test
    public void testGetModuleAssignments() throws Exception {
        List<Module> modules = moduleService.findModule("test module");
        Module module = modules.get(0);
        List<Assignment> assignments = moduleService.getModuleAssignments(module.getId());
        if (assignments.size() > 0) {
            assert assignments.get(0).getId() == 1;
            assert assignments.get(0).getName().equals("test assignment");
            assert assignments.get(0).getContent().equals("assignment for test");
        }
    }

    @Test(expected = NoSuchEntryException.class)
    public void testGetModuleAssignmentsNoSuchEntry() throws Exception {
        moduleService.getModuleAssignments(-1);
    }

    @Test
    public void testAddAssignmentToModule() throws Exception {
        List<Assignment> assignments = assignmentService.getAssignments();
        Assignment assignment = assignments.get(assignments.size() - 1);
        List<Module> modules = moduleService.getModules();
        Module module = modules.get(0);
        moduleService.addAssignmentToModule(Role.Admin, module.getId(), assignment.getId());
        List<Assignment> module_assignments = moduleService.getModuleAssignments(module.getId());
        Assignment assignment1 = module_assignments.get(module_assignments.size() - 1);
        assert assignment1.getId() == assignment.getId();
        assert assignment1.getName().equals(assignment.getName());
        assert assignment1.getContent().equals(assignment.getContent());
    }

    @Test(expected = AuthorityException.class)
    public void testAddAssignmentToModuleAuthority() throws Exception {
        List<Assignment> assignments = assignmentService.getAssignments();
        Assignment assignment = assignments.get(assignments.size() - 1);
        List<Module> modules = moduleService.getModules();
        Module module = modules.get(0);
        moduleService.addAssignmentToModule(Role.Company, module.getId(), assignment.getId());
    }

    @Test
    public void testDeleteAssignmentFromModule() throws Exception {
        List<Module> modules = moduleService.getModules();
        Module module = modules.get(0);
        List<Assignment> assignments = moduleService.getModuleAssignments(module.getId());
        if (assignments.size() > 0) {
            Assignment assignment = assignments.get(assignments.size() - 1);
            moduleService.deleteAssignmentFromModule(Role.Admin, module.getId(), assignment.getId());

            List<Assignment> assignments2 = moduleService.getModuleAssignments(module.getId());
            boolean hasDeletedAssignment = true;
            for (Assignment i : assignments2) {
                if (i.getId() == assignment.getId()) {
                    hasDeletedAssignment = false;
                }
            }
            assert hasDeletedAssignment == true;
        }
    }

    @Test(expected = AuthorityException.class)
    public void testDeleteAssignmentFromModuleAuthority() throws Exception {
        List<Module> modules = moduleService.getModules();
        Module module = modules.get(0);
        List<Assignment> assignments = moduleService.getModuleAssignments(module.getId());
        if (assignments.size() > 0) {
            Assignment assignment = assignments.get(assignments.size() - 1);
            moduleService.deleteAssignmentFromModule(Role.Company, module.getId(), assignment.getId());
        }
    }

    @Test(expected = NoSuchEntryException.class)
    public void testAddAssignmentToModuleNoSuchEntry() throws Exception {
        List<Module> modules = moduleService.getModules();
        Module module = modules.get(0);
        moduleService.deleteAssignmentFromModule(Role.Admin, module.getId(), -1);
    }


    @Test
    public void testPublishModule() throws Exception {
        List<Module> modules = moduleService.getModules();
        Module module = modules.get(0);
        assert module.getModuleStatus().equals(Module.UNPUBLISHED);
        List<Company> companies = companyService.getCompanys();
        Company company = companies.get(0);
        String startDate = "2017-07-01";
        String finishDate = "2017-08-01";
        assert moduleService.publishModule(Role.Admin, module.getId(), company.getId(), startDate, finishDate) == true;
        modules = moduleService.getModules();
        module = modules.get(0);
        assert module.getModuleStatus().equals(Module.PUBLISHED);
        List<Company> module_companys = moduleService.getModuleCompanys(module.getId());
        boolean hasPublished = false;
        for (Company c : module_companys) {
            if (c.getId() == company.getId()) {
                hasPublished = true;
            }
        }
        assert hasPublished == true;
    }

    @Test(expected = AuthorityException.class)
    public void testPublishModuleAuthority() throws Exception {
        String startDate = "2017-07-01";
        String finishDate = "2017-08-01";
        moduleService.publishModule(Role.Company, 2, 1, startDate, finishDate);
    }

    @Test
    public void testGetModuleCompanys() throws Exception {
        List<Module> modules = moduleService.getModules();
        Module module = modules.get(0);
        if (module.getModuleStatus().equals(Module.PUBLISHED)) {
            List<Company> module_companys = moduleService.getModuleCompanys(module.getId());
            assert module_companys.size() > 0;
        }
    }

    @Test(expected = NoSuchEntryException.class)
    public void testGetModuleCompanysNoSuchEntry() throws Exception {
        moduleService.getModuleCompanys(-1);
    }

    @Test
    public void testGetModuleProcesses() throws Exception {
        List<Module> modules = moduleService.getModules();
        Module module = modules.get(0);
        List<ModuleProcess> moduleProcesses = moduleService.getModuleProcesses(module.getId());
        if(moduleProcesses.size()>0){
            assert moduleProcesses.get(0).getId()==1;
            assert moduleProcesses.get(0).getCompany_id()==1;
            assert moduleProcesses.get(0).getModule_id()==1;
        }
    }

    @Test(expected = NoSuchEntryException.class)
    public void testGetModuleProcessesNoSuchEntry() throws Exception {
        moduleService.getModuleProcesses(-1);
    }

}
