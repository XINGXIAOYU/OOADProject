package bean;

/**
 * Created by mayezhou on 2017/6/7.
 */
public class Company {
    int id;
    String orgCode;
    String name;
    String status;
    String category;
    String industry;
    String contact;
    String tel;
    String description;

    public Company(int id, String orgCode, String name, String status, String category, String industry, String contact, String tel, String description) {
        this.id = id;
        this.orgCode = orgCode;
        this.name = name;
        this.status = status;
        this.category = category;
        this.industry = industry;
        this.contact = contact;
        this.tel = tel;
        this.description = description;
    }

    public Company() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
