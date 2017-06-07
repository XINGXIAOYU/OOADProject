package ooad.service.impl;

import ooad.bean.Assignment;
import ooad.bean.Company;
import ooad.bean.Module;
import ooad.dao.ModuleDAO;
import ooad.service.IModuleService;

import javax.annotation.Resource;
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
        boolean result = moduleDAO.addAssignmentToModule(module_id,assignment_id);
        return result;
    }

    @Override
    public Boolean deleteAssignmentFromModule(int module_id, int assignment_id) {
        boolean result = moduleDAO.deleteAssignmentToModule(module_id,assignment_id);
        return result;
    }

    @Override
    public List<Assignment> getModuleAssignments(int module_id) {
        return moduleDao.getModuleAssignments(module_id);
    }

    @Override
    public Boolean publishModule(int module_id, int company_id) {
        return null;
    }

    @Override
    public List<Company> getModuleCompanys(int module_id) {
        return null;
    }

    @Override
    public List<ModuleProcess> getModuleProcesses(int module_id) {
        return null;
    }
}
