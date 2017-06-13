package ooad.dao;

import ooad.bean.Company;
import ooad.common.exceptions.NoSuchEntryException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompanyDAOTest {
    @Resource
    CompanyDAO companyDAO;

    @Test
    public void agetSessionFactory() throws Exception {
        assertNotNull(companyDAO.getSessionFactory());
    }

    @Test
    public void agetCompanyDAO() throws Exception {
        assertNotNull(companyDAO);
    }

    @Test(expected = NoSuchEntryException.class)
    public void getNonExistCompany() throws Exception {
        companyDAO.getCompany(Integer.MAX_VALUE);
    }

    @Test
    public void getCompany() throws Exception {
        Company company = companyDAO.getCompany(1);
        assertNotNull(company);
        Company companyT = new Company(1, "001", "7-11-test", "normal", "food", "retailer", "Mary", "021001", "test company");
        assertEquals(companyT.toString(), company.toString());
    }

    @Test
    public void getCompanys() throws Exception {
        List<Company> companyList = companyDAO.getCompanys();
        String answer = "";
        for (Company company : companyList) {
            answer += company;
        }
        Company companyT = new Company(1, "001", "7-11-test", "normal", "food", "retailer", "Mary", "021001", "test company");
        assertEquals(answer, companyT.toString());
    }

}