package ooad.bean;


import java.sql.Date;

/**
 * Created by xingxiaoyu on 17/6/7.
 */
public class ModuleProcess {
    public static final String COMPLETED = "Completed";
    public static final String UNCOMPLETED = "Uncompleted";
    int id;
    int module_id;
    int company_id;
    Date start_time;
    Date finish_time;
    Date company_finish_time;
    String status;

    public ModuleProcess(int module_id, int company_id, Date start_time, Date finish_time) {
        this.module_id = module_id;
        this.company_id = company_id;
        this.start_time = start_time;
        this.finish_time = finish_time;
        status = UNCOMPLETED;
    }

    public ModuleProcess(int id, int module_id, int company_id, Date start_time, Date finish_time, Date company_finish_time, String status) {
        this.id = id;
        this.module_id = module_id;
        this.company_id = company_id;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.company_finish_time = company_finish_time;
        this.status = status;
    }

    @Override
    public String toString() {
        return "======ModuleProcess=======\nid: " + id
                + "\nmodule_id: " + module_id
                + "\ncompany_id: " + company_id
                + "\nstart_time: " + start_time
                + "\nfinish_time: " + finish_time
                + "\ncompany_finish_time: " + (company_finish_time == null? "NULL":company_finish_time)
                + "\nstatus: " + status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModule_id() {
        return module_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(Date finish_time) {
        this.finish_time = finish_time;
    }

    public Date getCompany_finish_time() {
        return company_finish_time;
    }

    public void setCompany_finish_time(Date company_finish_time) {
        this.company_finish_time = company_finish_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
