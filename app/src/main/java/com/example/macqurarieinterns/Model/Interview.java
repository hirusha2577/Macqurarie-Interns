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


}
