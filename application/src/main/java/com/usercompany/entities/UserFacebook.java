package com.usercompany.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by volodymyrmordas on 10/13/16.
 */
public class UserFacebook implements Serializable {
    @SerializedName(value = "id")
    private String id;

    @SerializedName(value = "birthday")
    private String birthday;

    public String getSocnetUid() {
        return id;
    }

    public void setSocnetUid(String socnetUid) {
        this.id = socnetUid;
    }

    @SerializedName(value = "first_name")
    private String firstName;

    @SerializedName(value = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @SerializedName(value = "last_name")
    private String lastName;


    @SerializedName(value = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String email;


    @SerializedName(value = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String gender;

    @SerializedName(value = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
