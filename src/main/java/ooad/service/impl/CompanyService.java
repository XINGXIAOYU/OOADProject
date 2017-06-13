package ooad.service.impl;

import ooad.bean.Company;
import ooad.bean.Module;
import ooad.bean.ModuleProcess;
import ooad.common.CompleteStatus;
import ooad.common.Role;
import ooad.common.exceptions.AuthorityException;
import ooad.common.exceptions.NoSuchEntryException;
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
    public List<Module> getCModuleList(Role role,int company_id) throws AuthorityException {
        if( !role.equals(Role.Company) ) {
            throw new AuthorityException(role);
        }
        return moduleDAO.getCModuleList(company_id);
    }

    @Override
    public Boolean completeStatus(Role role,int module_process_id) throws AuthorityException {
        if( !role.equals(Role.Company) ) {
            throw new AuthorityException(role);
        }
        LocalDate todayLocalDate = LocalDate.now(ZoneId.of("America/Montreal"));
        Date date= Date.valueOf(todayLocalDate);
        boolean result = moduleDAO.updateStatus(module_process_id,date, CompleteStatus.Completed);
        return result;
    }

    @Override
    public ModuleProcess getModuleProcess(Role role,int module_process_id)throws AuthorityException{
        if( !role.equals(Role.Company) ) {
            throw new AuthorityException(role);
        }
        return moduleDAO.getModuleProcess(module_process_id);
    }
}
