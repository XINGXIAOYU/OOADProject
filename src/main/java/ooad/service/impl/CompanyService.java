package ooad.service.impl;

import ooad.bean.Module;
import ooad.dao.CompanyDAO;
import org.springframework.stereotype.Service;
import ooad.service.ICompanyService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xingxiaoyu on 17/6/7.
 */

@Service
public class CompanyService implements ICompanyService {
    @Resource
    CompanyDAO companyDAO;

    @Override
    public List<Module> getCModuleList(int company_id) {
        return null;
    }

    @Override
    public Boolean completeStatus(int module_id) {
        return null;
    }
}
