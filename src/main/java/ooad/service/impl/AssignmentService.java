package ooad.service.impl;

import ooad.bean.Assignment;
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
    public Assignment findAssignment(String assignment_name) {
        return assignmentDAO.searchAssignment(assignment_name);
    }

    @Override
    public List<Assignment> getAssignments() {
        return assignmentDAO.getAssignments();
    }

    @Override
    public Boolean newAssignment(String assignment_name, String assignment_content) {
        Assignment assignment = new Assignment();
        assignment.setName(assignment_name);
        assignment.setContent(assignment_content);
        int id = assignmentDAO.addAssignment(assignment);
        return id != -1;
    }

    @Override
    public Boolean deleteAssignment(int assignment_id) {
        try {
            assignmentDAO.deleteAssignment(assignment_id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
