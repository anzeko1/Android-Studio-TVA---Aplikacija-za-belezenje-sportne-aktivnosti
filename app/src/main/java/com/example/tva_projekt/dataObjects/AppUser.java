package com.example.tva_projekt.dataObjects;

import org.bson.types.ObjectId;

public class AppUser {

    private String idUser;
    private String userName;
    private String password;
    private String email;
    private String gender;
    private Integer dob;
    private String response;

    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String name) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String email) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getDob() {
        return dob;
    }
    public void setDob(Integer dob) {
        this.dob = dob;
    }
    public String getResponse() {
        return response;
    }
    public void setResponse(String response) {
        this.response = response;
    }

    public AppUser() {

    }

    public AppUser(String userName, String password, String email, String gender, Integer dob) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
    }
}
