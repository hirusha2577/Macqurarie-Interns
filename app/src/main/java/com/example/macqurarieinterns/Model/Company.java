package com.example.macqurarieinterns.Model;

public class Company {

    private String id;
    private String name;
    private String register_no;
    private String address;
    private String P_imageURL;
    private String email;
    private String type;
    private String phone;
    private String C_imageURL;
    private String status;
    private String about;

    public Company(){

    }

    public Company(String id, String name, String register_no, String address, String p_imageURL, String email, String type, String phone, String c_imageURL, String status, String about) {
        this.id = id;
        this.name = name;
        this.register_no = register_no;
        this.address = address;
        P_imageURL = p_imageURL;
        this.email = email;
        this.type = type;
        this.phone = phone;
        C_imageURL = c_imageURL;
        this.status = status;
        this.about = about;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegister_no() {
        return register_no;
    }

    public void setRegister_no(String register_no) {
        this.register_no = register_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getP_imageURL() {
        return P_imageURL;
    }

    public void setP_imageURL(String p_imageURL) {
        P_imageURL = p_imageURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getC_imageURL() {
        return C_imageURL;
    }

    public void setC_imageURL(String c_imageURL) {
        C_imageURL = c_imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
