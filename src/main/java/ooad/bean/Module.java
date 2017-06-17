package ooad.bean;


/**
 * Created by mayezhou on 2017/6/7.
 */
public class Module {
    public final static String PUBLISHED = "Published";
    public final static String UNPUBLISHED = "Unpublished";
    public final static String BANNED = "Banned";
    int id;
    String name;
    String description;
    String moduleStatus;

    public Module() {
    }

    public Module(int id, String name, String description, String moduleStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.moduleStatus = moduleStatus;
    }

    public Module(String name, String description) {
        this.name = name;
        this.description = description;
        this.moduleStatus = UNPUBLISHED;
    }

    @Override
    public String toString() {
        return "id : " + id
                +"\nname : " + name
                +"\ndescription : " + description
                +"\nstatus : " + moduleStatus.toString();
    }

    public boolean equals(Module module) {
        return this.toString().equals(module.toString());
    }

    public String getModuleStatus() {
        return moduleStatus;
    }

    public void setModuleStatus(String moduleStatus) {
        this.moduleStatus = moduleStatus;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
