package ooad.service;

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
public interface ICompanyService {
    /**
     * 获得所有企业列表
     *
     * @return 所有企业列表
     */
    List<Company> getCompanys();

    /**
     * 获得下发模板列表
     *
     * @return 下发模板列表
     */
    List<Module> getCModuleList(Role role,int company_id) throws AuthorityException, NoSuchEntryException;

    /**
     * 录入排查结果
     *
     * @return 是否录入成功
     */
    Boolean completeStatus(Role role,int module_process_id) throws AuthorityException, NoSuchEntryException;

    /**
     * 获得模板进程项
     *
     * @return 模板进程
     */
    ModuleProcess getModuleProcess(Role role,int module_process_id) throws AuthorityException, NoSuchEntryException;
}

