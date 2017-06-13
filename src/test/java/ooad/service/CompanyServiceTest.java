package ooad.service;

import ooad.bean.Company;
import ooad.bean.Module;
import ooad.bean.ModuleProcess;
import ooad.common.CompleteStatus;
import ooad.common.Role;
import ooad.common.exceptions.AuthorityException;
import ooad.service.impl.CompanyService;
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
public class CompanyServiceTest {
    @Resource
    CompanyService companyService;

    @Test
    public void testGetCompanys() {
        List<Company> companys = companyService.getCompanys();
        Company company = new Company(1, "001", "7-11-test", "normal", "food", "retailer", "Mary", "021001", "test company");
        if (companys.size() > 0) {
            assert companys.get(0).toString().equals(company.toString());
        }
    }

    @Test
    public void testGetCModuleList() throws Exception {
        try {
            List<Module> cModules = companyService.getCModuleList(Role.Admin, 1);
        } catch (AuthorityException e) {
            assert e instanceof AuthorityException;
        }

        List<Module> cModules = companyService.getCModuleList(Role.Company, 1);
        if (cModules.size() > 0) {
            assert cModules.get(0).getId() == 1;
            assert cModules.get(0).getName().equals("module test");
            assert cModules.get(0).getDescription().equals("test for module");
        }

    }

    @Test
    public void testCompleteStatus() throws Exception {
        try {
            boolean result = companyService.completeStatus(Role.Admin, 1);
        } catch (AuthorityException e) {
            assert e instanceof AuthorityException;
        }
        boolean result = companyService.completeStatus(Role.Company, 1);
        assert result == true;
        ModuleProcess moduleProcess = companyService.getModuleProcess(Role.Company, 1);
        assert moduleProcess.getFinish_time() != null;
        assert moduleProcess.getStatus() == CompleteStatus.Completed;

    }
}
