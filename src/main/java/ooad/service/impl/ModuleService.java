package ooad.service.impl;

import ooad.bean.*;
import ooad.common.Role;
import ooad.common.exceptions.AuthorityException;
import ooad.common.exceptions.ForeignKeyConstraintException;
import ooad.common.exceptions.NoSuchEntryException;
import ooad.common.util.StringToSqlDate;
import ooad.dao.ModuleAssignmentDAO;
import ooad.dao.ModuleDAO;
import ooad.dao.ModuleProcessDAO;
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
    @Resource
    ModuleAssignmentDAO moduleAssignmentDAO;
    @Resource
    ModuleProcessDAO moduleProcessDAO;

    @Override
    public List<Module> findModule(String module_name) {
        return moduleDAO.getModulesByName(module_name);
    }

    @Override
    public List<Module> getModules() {
        return moduleDAO.getAll();
    }

    @Override
    public Boolean newModule(Role role, String module_name, String module_content) throws AuthorityException {
        if (!role.equals(Role.Admin)) {
            throw new AuthorityException(role);
        }
        Module newModule = new Module();
        newModule.setName(module_name);
        newModule.setDescription(module_content);
        int result = moduleDAO.save(newModule);
        return result != -1;
    }

    @Override
    public Boolean deleteModule(Role role, int module_id) throws AuthorityException, NoSuchEntryException {
        if (!role.equals(Role.Admin)) {
            throw new AuthorityException(role);
        }
        try {
            moduleDAO.delete(module_id);
            return true;
        } catch (NoSuchEntryException e) {
            throw e;
        } catch (ForeignKeyConstraintException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean modifyModule(Role role, int module_id, String module_name, String module_content) throws AuthorityException, NoSuchEntryException {
        if (!role.equals(Role.Admin)) {
            throw new AuthorityException(role);
        }
        try {
            moduleDAO.update(module_id, module_name, module_content);
            return true;
        } catch (NoSuchEntryException e) {
            throw e;
        }
    }

    @Override
    public Boolean addAssignmentToModule(Role role, int module_id, int assignment_id) throws AuthorityException, ForeignKeyConstraintException {
        if (!role.equals(Role.Admin)) {
            throw new AuthorityException(role);
        }
        ModuleAssignment newEntry = new ModuleAssignment(module_id, assignment_id);
        try {
            moduleAssignmentDAO.save(newEntry);
            return true;
        } catch (ForeignKeyConstraintException e) {
            throw e;
            //TODO
        }
    }

    @Override
    public Boolean deleteAssignmentFromModule(Role role, int id_moduleAssignment) throws AuthorityException, NoSuchEntryException {
        if (!role.equals(Role.Admin)) {
            throw new AuthorityException(role);
        }
        try {
            moduleAssignmentDAO.delete(id_moduleAssignment);
            return true;
        } catch (NoSuchEntryException e) {
            throw e;
            //TODO
        }
    }

    @Override
    public List<Assignment> getModuleAssignments(int module_id) throws NoSuchEntryException {
        try {
            return moduleAssignmentDAO.getAssignmentsOfModule(module_id);
        } catch (NoSuchEntryException e) {
            throw e;
        }
        //TODO: catch and show warning
    }

    @Override
    public Boolean publishModule(Role role, int module_id, int company_id, String start_time, String finish_time) throws AuthorityException, ForeignKeyConstraintException {
        if (!role.equals(Role.Admin)) {
            throw new AuthorityException(role);
        }
        ModuleProcess moduleProcess = new ModuleProcess(module_id, company_id, start_time, finish_time);
        try {
            return moduleProcessDAO.save(moduleProcess) != -1;
        } catch (ForeignKeyConstraintException e) {
            throw e;
        }
    }

    @Override
    public List<Company> getModuleCompanys(int module_id) throws NoSuchEntryException {
        try {
            return moduleProcessDAO.getCompaniesOfPublishedModule(module_id);
        } catch (NoSuchEntryException e) {
            throw e;
        }
    }

    @Override
    public List<ModuleProcess> getModuleProcesses(int module_id) {
        return moduleProcessDAO.getModuleProcesses(module_id);
    }
}
