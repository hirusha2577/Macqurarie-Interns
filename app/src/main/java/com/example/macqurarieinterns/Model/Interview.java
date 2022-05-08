package com.example.macqurarieinterns.Model;

public class Interview {
    private String company_id;
    private String student_id;
    private String pTime;
    private String date;
    private String time;
    private String place;
    private String description;

    public Interview(String company_id, String student_id, String pTime, String date, String time, String place, String description) {
        this.company_id = company_id;
        this.student_id = student_id;
        this.pTime = pTime;
        this.date = date;
        this.time = time;
        this.place = place;
        this.description = description;
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

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
