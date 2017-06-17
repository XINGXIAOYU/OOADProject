package ooad.service;

import ooad.bean.Assignment;
import ooad.common.Role;
import ooad.common.exceptions.AuthorityException;
import ooad.common.exceptions.ForeignKeyConstraintException;
import ooad.common.exceptions.NoSuchEntryException;

import java.util.List;

/**
 * Created by xingxiaoyu on 17/6/7.
 */
public interface IAssignmentService {
    /**
     * 根据name查找模板
     *
     * @return 检查项目实体
     */
    List<Assignment> findAssignment(String assignment_name) ;

    /**
     * 获取所有检查项目
     *
     * @return 检查项目列表
     */
    List<Assignment> getAssignments();

    /**
     * 新建检查项目
     *
     * @return 新建是否成功
     */
    Boolean newAssignment(Role role,String assignment_name, String assignment_content) throws AuthorityException;

    /**
     * 删除检查项目
     *
     * @return 删除是否成功
     */
    Boolean deleteAssignment(Role role,int assignment_id) throws AuthorityException, NoSuchEntryException, ForeignKeyConstraintException;
}
