package com.ciandt.cursoandroid.worldwondersapp.entity;

public class User {

    private String userEmail;
    private String userName;
    private String userPassword;
    private String userCountry;
    private String userLanguage;
    private String userGender;
    private Integer userNotification;

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(final String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(final String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(final String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage(final String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(final String userGender) {
        this.userGender = userGender;
    }

    public Integer getUserNotification() {
        return userNotification;
    }

    public void setUserNotification(final Integer userNotification) {
        this.userNotification = userNotification;
    }
}
