package ooad.service;

import ooad.bean.Assignment;
import ooad.bean.Company;
import ooad.bean.Module;
import ooad.bean.ModuleProcess;
import ooad.common.Role;
import ooad.common.exceptions.AuthorityException;
import ooad.common.exceptions.NoSuchEntryException;

import java.util.List;

/**
 * Created by xingxiaoyu on 17/6/7.
 */
public interface IModuleService {
    /**
     * 根据name查找模板
     *
     * @return 模板实体
     */
    List<Module> findModule(String module_name);

    /**
     * 获取所有模板
     *
     * @return 模板列表
     */
    List<Module> getModules();

    /**
     * 新建模板
     *
     * @return 是否新建成功
     */
    Boolean newModule(Role role,String module_name, String module_content)throws AuthorityException;

    /**
     * 删除模板
     *
     * @return 是否删除成功
     */
    Boolean deleteModule(Role role,int module_id)throws AuthorityException;

    /**
     * 修改模板名字或内容
     *
     * @return 是否修改成功
     */
    Boolean modifyModule(Role role,int module_id, String module_name, String module_content)throws AuthorityException;

    /**
     * 在模板中增加检查项目
     *
     * @return 是否增加成功
     */
    Boolean addAssignmentToModule(Role role,int module_id, int assignment_id)throws AuthorityException;

    /**
     * 在模板中删除检查项目
     *
     * @return 是否删除成功
     */
    Boolean deleteAssignmentFromModule(Role role,int id)throws AuthorityException;

    /**
     * 获得模板中的检查项目列表
     *
     * @return 定义的模板检查项目
     */
    List<Assignment> getModuleAssignments(int module_id) throws NoSuchEntryException;

    /**
     * 发放模板给企业
     *
     * @return 是否发放成功
     */
    Boolean publishModule(Role role,int module_id, int company_id, String start_time, String finish_time)throws AuthorityException;

    /**
     * 获得某个模板所对应的企业列表
     *
     * @return 对应企业列表
     */
    List<Company> getModuleCompanys(int module_id) throws NoSuchEntryException;

    /**
     * 获得某个模板执行进度列表
     *
     * @return 执行进度列表
     */
    List<ModuleProcess> getModuleProcesses(int module_id) throws NoSuchEntryException;

}
