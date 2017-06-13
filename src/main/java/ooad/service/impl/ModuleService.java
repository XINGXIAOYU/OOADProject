package ooad.service.impl;

import ooad.bean.*;
import ooad.common.Role;
import ooad.common.exceptions.AuthorityException;
import ooad.common.exceptions.NoSuchEntryException;
import ooad.common.util.StringToSqlDate;
import ooad.dao.ModuleDAO;
import ooad.service.IModuleService;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

/**
 * Created by xingxiaoyu on 17/6/7.
 */
public class ModuleService implements IModuleService {
    @Resource
    ModuleDAO moduleDAO;

    @Override
    public List<Module> findModule(String module_name) {
        try {
            return moduleDAO.findModule(module_name);
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Module> getModules() {
//        return moduleDAO.getModules(x);
        return null;
    }

    @Override
    public Boolean newModule(Role role,String module_name, String module_content)throws AuthorityException {
        if( !role.equals(Role.Admin) ) {
            throw new AuthorityException(role);
        }
        Module newModule = new Module();
        newModule.setName(module_name);
        newModule.setDescription(module_content);
        int result = moduleDAO.addModule(newModule);
        return result != -1;
    }

    @Override
    public Boolean deleteModule(Role role,int module_id)throws AuthorityException {
        if( !role.equals(Role.Admin) ) {
            throw new AuthorityException(role);
        }
        boolean result = moduleDAO.deleteModule(module_id);
        return result;
    }

    @Override
    public Boolean modifyModule(Role role,int module_id, String module_name, String module_content)throws AuthorityException {
        if( !role.equals(Role.Admin) ) {
            throw new AuthorityException(role);
        }
        boolean result = moduleDAO.updateModule(module_id, module_name, module_content);
        return result;
    }

    @Override
    public Boolean addAssignmentToModule(Role role,int module_id, int assignment_id)throws AuthorityException {
        if( !role.equals(Role.Admin) ) {
            throw new AuthorityException(role);
        }
        ModuleAssignment newEntry = new ModuleAssignment(module_id, assignment_id);
        boolean result = moduleDAO.addAssignmentToModule(newEntry);
        return result;
    }

    @Override
    public Boolean deleteAssignmentFromModule(Role role,int module_id, int assignment_id)throws AuthorityException {
        if( !role.equals(Role.Admin) ) {
            throw new AuthorityException(role);
        }
        boolean result = moduleDAO.deleteAssignmentToModule(module_id, assignment_id);
        return result;
    }

    @Override
    public List<Assignment> getModuleAssignments(int module_id) throws NoSuchEntryException {
        return moduleDAO.getModuleAssignments(module_id);
    }

    @Override
    public Boolean publishModule(Role role,int module_id, int company_id, String start_time, String finish_time) throws AuthorityException{
        if( !role.equals(Role.Admin) ) {
            throw new AuthorityException(role);
        }
        Date start = StringToSqlDate.strToDate(start_time);
        Date finish = StringToSqlDate.strToDate(finish_time);
        ModuleProcess moduleProcess = new ModuleProcess(module_id, company_id, start, finish);
        boolean result = moduleDAO.addModuleCompany(moduleProcess);
        return result;
    }

    @Override
    public List<Company> getModuleCompanys(int module_id) throws NoSuchEntryException {
        return moduleDAO.getModuleCompanys(module_id);
    }

    @Override
    public List<ModuleProcess> getModuleProcesses(int module_id) throws NoSuchEntryException {
        return moduleDAO.getModuleProcesses(module_id);
    }
}
