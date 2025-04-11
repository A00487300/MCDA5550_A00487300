package com.example.hotel;
import com.google.gson.annotations.SerializedName;
public class Guest {
    @SerializedName("guest_name")
    private String guestName;

    @SerializedName("gender")
    private String gender;

    public Guest(String guestName, String gender) {
        this.guestName = guestName;
        this.gender = gender;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
