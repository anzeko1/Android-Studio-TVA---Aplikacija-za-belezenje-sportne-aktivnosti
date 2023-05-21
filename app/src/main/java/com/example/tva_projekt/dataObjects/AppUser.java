package com.example.tva_projekt.dataObjects;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;
import io.realm.annotations.Required;

public class AppUser extends RealmObject {
    @PrimaryKey
    @RealmField("_id")
    private ObjectId id;
    @Required
    private String userName;
    @Required
    private String password;
    @Required
    private String email;
    @Required
    private String gender;
    @Required
    private Integer dob; ////to je Date of Birth za leto rojstva
    //private LocalDate dob; //to je Date of Birth za datum rojstva

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
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
    public void setDob(Integer age) {
        this.dob = dob;
    }


    public AppUser() {

    }

    public AppUser(ObjectId id, String userName, String password, String email, String gender, Integer dob) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
    }

    public AppUser(String userName, String password, String email, String gender, Integer dob) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email + ", dob=" + dob + ", gender=" + gender + "]";
    }


}
