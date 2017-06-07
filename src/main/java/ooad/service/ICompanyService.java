package ooad.service;

import ooad.bean.Company;
import ooad.bean.Module;

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
    List<Module> getCModuleList(int company_id);

    /**
     * 录入排查结果
     *
     * @return 是否录入成功
     */
    Boolean completeStatus(int module_id);
}
