package com.usercompany.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by volodymyrmordas on 10/13/16.
 */
public class User implements Serializable {

    public static Short SOCNET_T_EMAIL = 0;
    public static Short SOCNET_T_FACEBOOK = 1;
    public static Short SOCNET_T_GOOGLE = 2;

    public static Short SEX_UNDEFINED = 0;
    public static Short SEX_MALE = 1;
    public static Short SEX_FEMALE = 2;

    private Long id;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    private Short sex;


    @SerializedName(value = "sex")
    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    private Date birthday;

    @SerializedName(value = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @SerializedName(value = "socnet_uid")
    private String socnetUid;


    @SerializedName(value = "socnet_uid")
    public String getSocnetUid() {
        return socnetUid;
    }

    public void setSocnetUid(String socnetUid) {
        this.socnetUid = socnetUid;
    }

    @SerializedName(value = "socnet_type")
    private short socnetType;


    @SerializedName(value = "socnet_type")
    public short getSocnetType() {
        return socnetType;
    }

    public void setSocnetType(short socnetType) {
        this.socnetType = socnetType;
    }

    private String email;


    @SerializedName(value = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String phone;


    @SerializedName(value = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @SerializedName(value = "login_ip")
    private String loginIp;


    @SerializedName(value = "login_ip")
    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @SerializedName(value = "login_date")
    private Timestamp loginDate;


    @SerializedName(value = "login_date")
    public Timestamp getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Timestamp loginDate) {
        this.loginDate = loginDate;
    }

    private String salt;


    @SerializedName(value = "salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    private String password;


    @SerializedName(value = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SerializedName(value = "auth_key")
    private String authKey;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    private Double lng;


    @SerializedName(value = "lng")
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    private Double lat;


    @SerializedName(value = "lat")
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    private short status;


    @SerializedName(value = "status")
    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    @SerializedName(value = "created_at")
    private Timestamp createdAt;


    @SerializedName(value = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @SerializedName(value = "updated_at")
    private Timestamp updatedAt;


    @SerializedName(value = "updated_at")
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @SerializedName(value = "logout_date")
    private Timestamp logoutDate;


    @SerializedName(value = "logout_date")
    public Timestamp getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Timestamp logoutDate) {
        this.logoutDate = logoutDate;
    }
}
