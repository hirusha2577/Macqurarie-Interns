package com.example.macqurarieinterns.Model;

public class Vacancy {

    private String id;
    private String company_name;
    private String title;
    private String field;
    private String description;
    private String appliers_count;
    private String pTime;
    private String company_address;
    private String company_image;

    public Vacancy(){

    }

    public Vacancy(String id, String company_name, String title, String field, String description, String appliers_count, String pTime, String company_address, String company_image) {
        this.id = id;
        this.company_name = company_name;
        this.title = title;
        this.field = field;
        this.description = description;
        this.appliers_count = appliers_count;
        this.pTime = pTime;
        this.company_address = company_address;
        this.company_image = company_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppliers_count() {
        return appliers_count;
    }

    public void setAppliers_count(String appliers_count) {
        this.appliers_count = appliers_count;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getCompany_image() {
        return company_image;
    }

    public void setCompany_image(String company_image) {
        this.company_image = company_image;
    }
}
