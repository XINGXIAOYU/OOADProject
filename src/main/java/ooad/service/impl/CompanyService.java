package ooad.service.impl;

import ooad.bean.Company;
import ooad.bean.Module;
import ooad.bean.ModuleAssignment;
import ooad.common.CompleteStatus;
import ooad.dao.CompanyDAO;
import ooad.dao.ModuleDAO;
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
    @Resource
    ModuleDAO moduleDAO;

    @Override
    public List<Company> getCompanys() {
        return companyDAO.getCompanys();
    }

    @Override
    public List<Module> getCModuleList(int company_id) {
        return moduleDAO.getCModuleList(company_id);
    }

    @Override
    public Boolean completeStatus(int module_process_id) {
        LocalDate todayLocalDate = LocalDate.now(ZoneId.of("America/Montreal"));
        Date date= Date.valueOf(todayLocalDate);
        boolean result = moduleDAO.updateStatus(module_process_id,date, CompleteStatus.Completed);
        return result;
    }
}
