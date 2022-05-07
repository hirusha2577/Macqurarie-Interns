package com.example.macqurarieinterns.Model;

public class Student {
    private String studentId;
    private String student_id;
    private String name;
    private String nic;
    private String email;
    private String phone;
    private String center;
    private String degree;
    private String r_stud_year;
    private String intake;
    private String dob;
    private String gender;
    private String status;
    private String P_imageURL;
    private String C_imageURL;
    private String about;

    public Student(){

    }

    public Student(String studentId,String student_id, String name, String nic, String email, String phone, String center, String degree, String r_stud_year, String intake, String dob, String gender, String status, String p_imageURL, String c_imageURL, String about) {
        this.studentId = studentId;
        this.student_id = student_id;
        this.name = name;
        this.nic = nic;
        this.email = email;
        this.phone = phone;
        this.center = center;
        this.degree = degree;
        this.r_stud_year = r_stud_year;
        this.intake = intake;
        this.dob = dob;
        this.gender = gender;
        this.status = status;
        P_imageURL = p_imageURL;
        C_imageURL = c_imageURL;
        this.about = about;

    }

    public String getUID() {
        return studentId;
    }

    public void setUID(String studentId) {
        this.studentId = studentId;
    }

    public String getSID() {
        return student_id;
    }

    public void setSID(String student_id) {
        this.student_id = student_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNIC() {
        return nic;
    }

    public void setNIC(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String center) {
        this.degree = degree;
    }

    public String getYear() {
        return r_stud_year;
    }

    public void setYear(String r_stud_year) {
        this.r_stud_year = r_stud_year;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public String getDOB() {
        return dob;
    }

    public void setDOB(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getP_imageURL() {
        return P_imageURL;
    }

    public void setP_imageURL(String p_imageURL) {
        P_imageURL = p_imageURL;
    }

    public String getC_imageURL() {
        return C_imageURL;
    }

    public void setC_imageURL(String c_imageURL) {
        C_imageURL = c_imageURL;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
    
}
