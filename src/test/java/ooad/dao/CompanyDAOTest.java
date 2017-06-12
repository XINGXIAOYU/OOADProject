package ooad.dao;

import ooad.bean.Company;
import ooad.common.exceptions.NoSuchEntryException;
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
public class CompanyDAOTest {
    @Resource
    CompanyDAO companyDAO;

    @Test
    public void getSessionFactory() throws Exception {
        assertNotNull(companyDAO.getSessionFactory());
    }

    @Test(expected = NoSuchEntryException.class)
    public void getNonExistCompany() throws Exception {
        companyDAO.getCompany(Integer.MAX_VALUE);
    }

    @Test
    public void getCompany() throws Exception {
        Company company = companyDAO.getCompany(1);
        assertNotNull(company);
        System.out.println(company);
    }

    @Test
    public void getCompanys() throws Exception {
        List<Company> companyList = companyDAO.getCompanys();
        for (Iterator iterator =
             companyList.iterator(); iterator.hasNext();) {
            Company company = (Company) iterator.next();
            System.out.println(company);
        }
    }

}