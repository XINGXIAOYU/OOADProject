package ooad.service.impl;

import ooad.bean.*;
import ooad.common.ModuleStatus;
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
    public Module findModule(String module_name) {
        return moduleDAO.findModule(module_name);
    }

    @Override
    public List<Module> getModules() {
        return moduleDAO.getModules();
    }

    @Override
    public Boolean newModule(String module_name, String module_content) {
        Module newModule = new Module();
        newModule.setName(module_name);
        newModule.setDescription(module_content);
        boolean result = moduleDAO.addModule(newModule);
        return result;
    }

    @Override
    public Boolean deleteModule(int module_id) {
        boolean result = moduleDAO.deleteModule(module_id);
        return result;
    }

    @Override
    public Boolean modifyModule(int module_id, String module_name, String module_content) {
        boolean result = moduleDAO.updateModule(module_id, module_name, module_content);
        return result;
    }

    @Override
    public Boolean addAssignmentToModule(int module_id, int assignment_id) {
        ModuleAssignment newEntry = new ModuleAssignment(module_id, assignment_id);
        boolean result = moduleDAO.addAssignmentToModule(newEntry);
        return result;
    }

    @Override
    public Boolean deleteAssignmentFromModule(int module_id, int assignment_id) {
        boolean result = moduleDAO.deleteAssignmentToModule(module_id, assignment_id);
        return result;
    }

    @Override
    public List<Assignment> getModuleAssignments(int module_id) {
        return moduleDAO.getModuleAssignments(module_id);
    }

    @Override
    public Boolean publishModule(int module_id, int company_id, String start_time, String finish_time) {
        Date start = StringToSqlDate.strToDate(start_time);
        Date finish = StringToSqlDate.strToDate(finish_time);
        ModuleProcess moduleProcess = new ModuleProcess(module_id, company_id, start, finish);
        boolean result = moduleDAO.addModuleCompany(moduleProcess);
        return result;
    }

    @Override
    public List<Company> getModuleCompanys(int module_id) {
        return moduleDAO.getModuleCompanys(module_id);
    }

    @Override
    public List<ModuleProcess> getModuleProcesses(int module_id) {
        return moduleDAO.getModuleProcesses(module_id);
    }
}
