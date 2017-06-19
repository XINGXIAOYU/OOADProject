package ooad.service;

import ooad.bean.Company;
import ooad.bean.ModuleProcess;
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

    /**
     * 测试获得所有企业
     */
    @Test
    public void testGetCompanys() {
        List<Company> companys = companyService.getCompanys();
        Company company = new Company(1, "001", "7-11-test", "normal", "food", "retailer", "Mary", "021001", "test company");
        if (companys.size() > 0) {
            assert companys.get(0).toString().equals(company.toString());
        }
    }

    /**
     * 测试企业项目列表
     * @throws Exception
     */

    @Test
    public void testGetCModuleProcessList() throws Exception {
        List<ModuleProcess> cModules = companyService.getCModuleProcessList(Role.Company, 1);
        if (cModules.size() > 0) {
            assert cModules.get(0).getId() == 1;
            assert cModules.get(0).getModule_id() == 1;
            assert cModules.get(0).getCompany_id() == 1;
        }

    }

    /**
     * 测试企业项目列表的权限（只有企业可以）
     * @throws Exception
     */

    @Test(expected = AuthorityException.class)
    public void testGetCModuleProcessListAuthority() throws Exception {
        companyService.getCModuleProcessList(Role.Admin, 1);
    }

    /**
     * 测试企业完成模板
     * @throws Exception
     */

    @Test
    public void testCompleteStatus() throws Exception {
        List<ModuleProcess> cModules = companyService.getCModuleProcessList(Role.Company, 1);
        ModuleProcess moduleProcess = cModules.get(1);
        assert moduleProcess.getCompany_finish_time() == null;
        assert moduleProcess.getStatus().equals(ModuleProcess.UNCOMPLETED);
        boolean result = companyService.completeStatus(Role.Company, moduleProcess.getId());
        assert result == true;
        cModules = companyService.getCModuleProcessList(Role.Company, 1);
        moduleProcess = cModules.get(1);
        assert moduleProcess.getCompany_finish_time() != null;
        assert moduleProcess.getStatus().equals(ModuleProcess.COMPLETED);

    }

    /**
     * 测试企业完成模板权限（只有企业可以）
     * @throws Exception
     */

    @Test(expected = AuthorityException.class)
    public void testCompleteStatusAuthority() throws Exception {
        companyService.completeStatus(Role.Admin, 1);
    }
}
