package ooad.service.impl;

import ooad.bean.Company;
import ooad.bean.Module;
import ooad.common.CompleteStatus;
import ooad.dao.CompanyDAO;
import ooad.service.ICompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * Created by xingxiaoyu on 17/6/7.
 */

@Service
public class CompanyService implements ICompanyService {
    @Resource
    CompanyDAO companyDAO;

    @Override
    public List<Company> getCompanys() {
        return companyDAO.getCompanys();
    }

    @Override
    public List<Module> getCModuleList(int company_id) {
        return companyDAO.getCModuleList(company_id);
    }

    @Override
    public Boolean completeStatus(int module_id) {
        LocalDate todayLocalDate = LocalDate.now(ZoneId.of("America/Montreal"));
        Date date= Date.valueOf(todayLocalDate);
        boolean result = companyDAO.updateStatus(module_id,date, CompleteStatus.Completed);
        return result;
    }
}
