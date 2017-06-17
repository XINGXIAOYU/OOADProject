package ooad.dao;

import ooad.bean.Company;
import ooad.bean.ModuleProcess;
import ooad.common.exceptions.ForeignKeyConstraintException;
import ooad.common.exceptions.NoSuchEntryException;
import ooad.common.util.StringToSqlDate;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.util.locale.StringTokenIterator;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mayezhou on 2017/6/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModuleProcessDAOTest {
    @Resource
    ModuleProcessDAO moduleProcessDAO;

    @Test
    public void aaGetModuleProcessDAO() throws Exception {
        assertNotNull(moduleProcessDAO);
    }

    @Test
    public void aGetModuleDAO() throws Exception {
        assertNotNull(moduleProcessDAO.getModuleDAO());
    }

    @Test
    public void aaGetSessionFactory() throws Exception {
        assertNotNull(moduleProcessDAO.getSessionFactory());
    }

    @Test
    public void aGetCompanyDAO() throws Exception {
        assertNotNull(moduleProcessDAO.getCompanyDAO());
    }

    @Test
    public void aGetAssignmentDAO() throws Exception {
        assertNotNull(moduleProcessDAO.getAssignmentDAO());
    }

    @Test
    public void bGet() throws Exception {
        ModuleProcess moduleProcessR = moduleProcessDAO.get(1);
        ModuleProcess moduleProcess = new ModuleProcess(1, 1, 1, "2017-06-11", "2017-07-11", null, ModuleProcess.UNCOMPLETED);
        assertEquals(moduleProcess.toString(), moduleProcessR.toString());
    }

    @Test(expected = NoSuchEntryException.class)
    public void bGetNonExist() throws Exception {
        moduleProcessDAO.get(Integer.MAX_VALUE);
    }

    @Test
    public void cSave() throws Exception {
        ModuleProcess moduleProcess = new ModuleProcess(1, 1, "2017-01-01", "2017-12-31");
        int id = moduleProcessDAO.save(moduleProcess);
        assertNotEquals(-1, id);
        moduleProcess.setId(id);
        ModuleProcess theOne = moduleProcessDAO.get(id);
        assertEquals(moduleProcess.toString(), theOne.toString());
    }

    @Test(expected = ForeignKeyConstraintException.class)
    public void cSaveFail() throws Exception {
        ModuleProcess moduleProcess = new ModuleProcess(Integer.MAX_VALUE, Integer.MAX_VALUE, "2017-01-01", "2017-12-31");
        moduleProcessDAO.save(moduleProcess);
    }

    @Test
    public void update() throws Exception {
        moduleProcessDAO.update(1, "2017-6-17", ModuleProcess.COMPLETED);
        ModuleProcess moduleProcess = new ModuleProcess(1, 1, 1, "2017-06-11", "2017-07-11", "2017-06-17", ModuleProcess.COMPLETED);
        ModuleProcess moduleProcessR = moduleProcessDAO.get(1);
        assertEquals(moduleProcess.toString(), moduleProcessR.toString());
    }

    @Test(expected = NoSuchEntryException.class)
    public void updateFail() throws Exception {
        moduleProcessDAO.update(Integer.MAX_VALUE, "2017-06-17", ModuleProcess.COMPLETED);
    }

    @Test
    public void getCompaniesOfPublishedModule() throws Exception {
        List<Company> companyList = moduleProcessDAO.getCompaniesOfPublishedModule(1);
        String answer = "";
        for (Company company :
                companyList) {
            answer += company;
        }
        Company company = new Company(1, "001", "7-11-test", "normal", "food", "retailer", "Mary", "021001", "test company");
        assertEquals(company.toString(), answer);
        companyList = moduleProcessDAO.getCompaniesOfPublishedModule(Integer.MAX_VALUE);
        assertEquals(0, companyList.size());
    }

    @Test
    public void getModuleProcesses() throws Exception {
        List<ModuleProcess> moduleProcessList = moduleProcessDAO.getModuleProcesses(1);
        assertEquals(1, moduleProcessList.size());
        //TODO
    }

    @Test
    public void getModulesOfCompany() throws Exception {
        //TODO
    }

}