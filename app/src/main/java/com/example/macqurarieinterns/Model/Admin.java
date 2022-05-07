package com.example.macqurarieinterns.Model;

public class Admin {

    private String id;
    private String name;
    private String P_imageURL;
    private String email;
    private String type;
    private String phone;

    public Admin(){

    }

    public Admin(String id, String name,  String p_imageURL, String email, String type, String phone) {
        this.id = id;
        this.name = name;
        P_imageURL = p_imageURL;
        this.email = email;
        this.type = type;
        this.phone = phone;

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




}
