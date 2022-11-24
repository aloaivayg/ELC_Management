package com.example.elc_management.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Classs implements Parcelable {
    private String cId;
    private String schedule;
    private String time;
    private int quantity;

    private boolean status;

    public Classs(String cId, String schedule, String time) {
        this.cId = cId;
        this.schedule = schedule;
        this.time = time;
        this.quantity = 0;
    }

    public Classs() {
    }

    protected Classs(Parcel in) {
        cId = in.readString();
        schedule = in.readString();
        time = in.readString();
        quantity = in.readInt();
        status = in.readByte() != 0;
    }

    public static final Creator<Classs> CREATOR = new Creator<Classs>() {
        @Override
        public Classs createFromParcel(Parcel in) {
            return new Classs(in);
        }

        @Override
        public Classs[] newArray(int size) {
            return new Classs[size];
        }
    };

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Classs{" +
                "cId='" + cId + '\'' +
                ", schedule=" + schedule +
                ", time='" + time + '\'' +
                ", number of students='" + quantity + '\'' +
                ", status=" + status +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("cId", cId);
        result.put("schedule", schedule);
        result.put("time", time);
        result.put("quantity", quantity);
        result.put("status", status);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cId);
        parcel.writeString(schedule);
        parcel.writeString(time);
        parcel.writeInt(quantity);
        parcel.writeByte((byte) (status ? 1 : 0));
    }
}
