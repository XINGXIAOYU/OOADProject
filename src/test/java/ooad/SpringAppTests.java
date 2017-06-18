package ooad;

import ooad.bean.Assignment;
import ooad.bean.Company;
import ooad.bean.Module;
import ooad.bean.ModuleProcess;
import ooad.common.Role;
import ooad.common.exceptions.AuthorityException;
import ooad.common.exceptions.ForeignKeyConstraintException;
import ooad.common.exceptions.NoSuchEntryException;
import ooad.service.impl.AssignmentService;
import ooad.service.impl.CompanyService;
import ooad.service.impl.ModuleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class SpringAppTests {
    @Autowired
    private AssignmentService assignmentService;
    private CompanyService companyService;
    private ModuleService moduleService;

    @Test
    public void mainTest() {

        Role admin = Role.Admin;
        //企业定义检查项目
        try {
            assert assignmentService.newAssignment(admin, "main test assignment", "for main test");
            List<Assignment> assignmentList = assignmentService.getAssignments();
            assert assignmentList.size() > 0;
            assert assignmentList.get(assignmentList.size() - 1).getName().equals("main test assignment");
            assert assignmentList.get(assignmentList.size() - 1).getContent().equals("for main test");
        } catch (AuthorityException e) {
            e.printStackTrace();
        }

        //企业设置模板
        try {
            assert moduleService.newModule(admin, "main test module", "for main test");
            List<Module> moduleList = moduleService.getModules();
            assert moduleList.size() > 0;
            Module module = moduleList.get(moduleList.size() - 1);
            assert module.getName().equals("main test module");
            assert module.getDescription().equals("for main test");
            List<Assignment> assignmentList = assignmentService.getAssignments();
            if (assignmentList.size() > 0) {
                Assignment assignment = assignmentList.get(assignmentList.size() - 1);
                assert moduleService.addAssignmentToModule(admin, module.getId(), assignment.getId());
                List<Assignment> module_assignmentList = moduleService.getModuleAssignments(module.getId());
                Assignment module_assignment = module_assignmentList.get(module_assignmentList.size() - 1);
                assert module_assignment.getName().equals("main test assignment");
                assert module_assignment.getContent().equals("for main test");
            }
        } catch (AuthorityException e) {
            e.printStackTrace();
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        } catch (ForeignKeyConstraintException e) {
            e.printStackTrace();
        }

        //企业发布模板给企业
        List<Module> moduleList = moduleService.getModules();
        if (moduleList.size() > 0) {
            Module module = moduleList.get(moduleList.size() - 1);
            assert module.getName().equals("main test module");
            assert module.getDescription().equals("for main test");
            List<Company> companyList = companyService.getCompanys();
            if (companyList.size() > 0) {
                Company company = companyList.get(0);
                try {
                    assert module.getModuleStatus().equals(Module.UNPUBLISHED);
                    assert moduleService.publishModule(admin, module.getId(), company.getId(), "2016-07-01", "2017-07-01");
                    assert module.getModuleStatus().equals(Module.PUBLISHED);
                } catch (AuthorityException e) {
                    e.printStackTrace();
                } catch (ForeignKeyConstraintException e) {
                    e.printStackTrace();
                } catch (NoSuchEntryException e) {
                    e.printStackTrace();
                }
            }
        }

        //企业完成模板
        Role company = Role.Company;
        int company_id = 1;
        try {
            List<Module> company_moduleList = companyService.getCModuleList(company, company_id);
            if(company_moduleList.size()>0){
                Module company_module = company_moduleList.get(0);
                assert company_module.getName().equals("main test module");
                assert company_module.getDescription().equals("for main test");
                ModuleProcess moduleProcess = companyService.getModuleProcess(company,company_module.getId());
                assert moduleProcess.getModule_id()==company_module.getId();
                assert moduleProcess.getCompany_id()==company_id;
                assert moduleProcess.getStatus().equals(ModuleProcess.UNCOMPLETED);
                assert companyService.completeStatus(company,moduleProcess.getId());
                ModuleProcess moduleProcess_after = companyService.getModuleProcess(company,company_module.getId());
                assert moduleProcess_after.getStatus().equals(ModuleProcess.COMPLETED);
            }
        } catch (AuthorityException e) {
            e.printStackTrace();
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        }

    }

}
