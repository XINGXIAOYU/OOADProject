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
        return moduleDAO.findModule(module_name);
    }

    @Override
    public List<Module> getModules() {
        return moduleDAO.getModules();
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
        try {
            moduleDAO.deleteModule(module_id);
            return true;
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean modifyModule(Role role,int module_id, String module_name, String module_content)throws AuthorityException {
        if( !role.equals(Role.Admin) ) {
            throw new AuthorityException(role);
        }
        try {
            moduleDAO.updateModule(module_id, module_name, module_content);
            return true;
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean addAssignmentToModule(Role role,int module_id, int assignment_id)throws AuthorityException {
        if( !role.equals(Role.Admin) ) {
            throw new AuthorityException(role);
        }
        ModuleAssignment newEntry = new ModuleAssignment(module_id, assignment_id);
        return moduleDAO.addAssignmentToModule(newEntry) != -1;
    }

    @Override
    public Boolean deleteAssignmentFromModule(Role role,int module_id, int assignment_id) throws AuthorityException, NoSuchEntryException {
        if( !role.equals(Role.Admin) ) {
            throw new AuthorityException(role);
        }
       try {
           moduleDAO.deleteAssignmentFromModule(module_id,assignment_id);
            return true;
       } catch (NoSuchEntryException e) {
          throw new NoSuchEntryException();
     }
    }

    @Override
    public List<Assignment> getModuleAssignments(int module_id) throws NoSuchEntryException {
        try {
            return moduleDAO.getModuleAssignments(module_id);
        } catch (NoSuchEntryException e) {
            throw new NoSuchEntryException();
        }
        //TODO: throw OR catch?
    }

    @Override
    public Boolean publishModule(Role role,int module_id, int company_id, String start_time, String finish_time) throws AuthorityException{
        if( !role.equals(Role.Admin) ) {
            throw new AuthorityException(role);
        }
        Date start = StringToSqlDate.strToDate(start_time);
        Date finish = StringToSqlDate.strToDate(finish_time);
        ModuleProcess moduleProcess = new ModuleProcess(module_id, company_id, start, finish);
        return moduleDAO.addModuleCompany(moduleProcess);
    }

    @Override
    public List<Company> getModuleCompanys(int module_id) throws NoSuchEntryException {
        try {
            return moduleDAO.getModuleCompanys(module_id);
        } catch (NoSuchEntryException e) {
            throw new NoSuchEntryException();
        }
    }

    @Override
    public List<ModuleProcess> getModuleProcesses(int module_id) throws NoSuchEntryException {
        try {
            return moduleDAO.getModuleProcesses(module_id);
        } catch (NoSuchEntryException e) {
            throw new NoSuchEntryException();
        }
        return null;
    }
}
