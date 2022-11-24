package com.example.elc_management.object;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Student implements Parcelable {
    private String sId;
    private String name;
    private String gender;
    private String dob;
    private String sClass;
    private String email;
    private String phoneNumber;
    private String account;
    private String address;


    public Student(String name, String gender, String dob, String sClass, String phoneNumber) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.sClass = sClass;
        this.phoneNumber = phoneNumber;
    }

    public Student() {
    }

    protected Student(Parcel in) {
        sId = in.readString();
        name = in.readString();
        gender = in.readString();
        dob = in.readString();
        sClass = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        account = in.readString();
        address = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("sId", sId);
        result.put("name", name);
        result.put("gender", gender);
        result.put("dob", dob);
        result.put("sClass", sClass);
        result.put("phoneNumber", phoneNumber);

        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NonNull
    @Override
    public String toString() {
        return "Student{" +
                "sid='" + sId + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", dob='" + dob + '\'' +
                ", s_class='" + sClass + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phoneNumber + '\'' +
                ", account='" + account + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //same order as constructor
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sId);
        parcel.writeString(name);
        parcel.writeString(gender);
        parcel.writeString(dob);
        parcel.writeString(sClass);
        parcel.writeString(phoneNumber);
        parcel.writeString(email);
    }
}
