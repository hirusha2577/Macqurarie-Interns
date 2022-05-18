package com.example.macqurarieinterns.Model;

public class VacancyApply {

    private String id;
    private String vacancy_id;
    private String company_id;
    private String student_id;
    private String student_name;
    private String student_dp;
    private String student_year;
    private String cv;

    public VacancyApply(){

    }

    public VacancyApply(String id, String vacancy_id, String company_id, String student_id, String student_name, String student_dp, String student_year, String cv) {
        this.id = id;
        this.vacancy_id = vacancy_id;
        this.company_id = company_id;
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_dp = student_dp;
        this.student_year = student_year;
        this.cv = cv;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVacancy_id() {
        return vacancy_id;
    }

    public void setVacancy_id(String vacancy_id) {
        this.vacancy_id = vacancy_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_dp() {
        return student_dp;
    }

    public void setStudent_dp(String student_dp) {
        this.student_dp = student_dp;
    }

    public String getStudent_year() {
        return student_year;
    }

    public void setStudent_year(String student_year) {
        this.student_year = student_year;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }
}
