package ooad.service.impl;

import ooad.bean.Assignment;
import ooad.common.Role;
import ooad.common.exceptions.AuthorityException;
import ooad.common.exceptions.NoSuchEntryException;
import ooad.dao.AssignmentDAO;
import ooad.service.IAssignmentService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xingxiaoyu on 17/6/7.
 */
public class AssignmentService implements IAssignmentService {
    @Resource
    AssignmentDAO assignmentDAO;

    @Override
    public List<Assignment> findAssignment(String assignment_name) {
        return assignmentDAO.searchAssignment(assignment_name);
    }

    @Override
    public List<Assignment> getAssignments() {
        return assignmentDAO.getAssignments();
    }

    @Override
    public Boolean newAssignment(Role role, String assignment_name, String assignment_content) throws AuthorityException {
        if( !role.equals(Role.Admin)){
            throw new AuthorityException(role);
        }
        Assignment assignment = new Assignment();
        assignment.setName(assignment_name);
        assignment.setContent(assignment_content);
        int id = assignmentDAO.addAssignment(assignment);
        return id != -1;
    }

    @Override
    public Boolean deleteAssignment(Role role,int assignment_id) throws AuthorityException, NoSuchEntryException {
        if( !role.equals(Role.Admin)){
            throw new AuthorityException(role);
        }
        try {
            assignmentDAO.deleteAssignment(assignment_id);
            return true;
        } catch (Exception e) {
            throw new NoSuchEntryException();
        }
    }
}
