package ooad.bean;


/**
 * Created by mayezhou on 2017/6/11.
 */
public class ModuleAssignment {
    int moduleID;
    int assignmentID;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModuleAssignment(int moduleID, int assignmentID) {
        this.moduleID = moduleID;
        this.assignmentID = assignmentID;
    }

    @Override
    public String toString() {
        return "=========ModuleAssignment========\nid: " + id + "\nmoduleID: " + moduleID + "\nassignmentID: " + assignmentID;
    }

    public boolean equals(ModuleAssignment moduleAssignment) {
        return id == moduleAssignment.id && moduleID == moduleAssignment.moduleID && assignmentID == moduleAssignment.assignmentID;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }
}
