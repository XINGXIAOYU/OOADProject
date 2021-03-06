package ooad.bean;

/**
 * Created by mayezhou on 2017/6/7.
 */
public class Assignment {
    int id;
    String name;
    String content;

    public Assignment() {
    }

    public Assignment(int id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public Assignment(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId='" + id + '\'' +
                ", assignmentName='" + name + '\'' +
                ", assignmentContent=" + content +
                '}';
    }

    public boolean equals(Assignment assignment) {
        return assignment.id == id && name.equals(assignment.name) && content.equals(assignment.content);
    }
}
