package com.example.elc_management.object;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private String aId;
    private String aUsername;
    private String aPassword;
    private String aEmail;
    private String aPhoneNumber;
    private String aNickname;

    public Account(String aId, String aUsername, String aPassword, String aPhoneNumber, String aEmail, String aNickname) {
        this.aId = aId;
        this.aUsername = aUsername;
        this.aPassword = aPassword;
        this.aEmail = aEmail;
        this.aPhoneNumber = aPhoneNumber;
        this.aNickname = aNickname;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("aId", aId);
        result.put("aUsername", aUsername);
        result.put("aPassword", aPassword);
        result.put("aEmail", aEmail);
        result.put("aPhoneNumber", aPhoneNumber);
        result.put("aNickname", aNickname);

        return result;
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getaUsername() {
        return aUsername;
    }

    public void setaUsername(String aUsername) {
        this.aUsername = aUsername;
    }

    public String getaPassword() {
        return aPassword;
    }

    public void setaPassword(String aPassword) {
        this.aPassword = aPassword;
    }

    public String getaEmail() {
        return aEmail;
    }

    public void setaEmail(String aEmail) {
        this.aEmail = aEmail;
    }

    public String getaPhoneNumber() {
        return aPhoneNumber;
    }

    public void setaPhoneNumber(String aPhoneNumber) {
        this.aPhoneNumber = aPhoneNumber;
    }

    public String getaNickname() {
        return aNickname;
    }

    public void setaNickname(String aNickname) {
        this.aNickname = aNickname;
    }

    @Override
    public String toString() {
        return "Account{" +
                "aId='" + aId + '\'' +
                ", aUsername='" + aUsername + '\'' +
                ", aPassword='" + aPassword + '\'' +
                ", aEmail='" + aEmail + '\'' +
                ", aPhoneNumber='" + aPhoneNumber + '\'' +
                ", aNickname='" + aNickname + '\'' +
                '}';
    }
}
