package ooad.bean;

import ooad.common.ModuleStatus;

/**
 * Created by mayezhou on 2017/6/7.
 */
public class Module {
    int id;
    String name;
    String description;
    ModuleStatus moduleStatus;

    public Module() {
    }

    public Module(int id, String name, String description, ModuleStatus moduleStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.moduleStatus = ModuleStatus.Unpublished;
    }

    public Module(String name, String description) {
        this.name = name;
        this.description = description;
        this.moduleStatus = ModuleStatus.Unpublished;
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
