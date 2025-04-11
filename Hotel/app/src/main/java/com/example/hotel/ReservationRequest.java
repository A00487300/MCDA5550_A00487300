package com.example.hotel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReservationRequest {
    @SerializedName("hotel_name")
    private String hotel;         // 对应后端 fields: 'hotel'

    @SerializedName("checkin")
    private String checkin;       // 对应后端 fields: 'checkin'

    @SerializedName("checkout")
    private String checkout;      // 对应后端 fields: 'checkout'

    @SerializedName("guests_list")
    private List<Guest> guests;   // 对应后端 fields: 'guests'


    // 构造函数
    public ReservationRequest(String hotel, String checkin, String checkout, List<Guest> guests) {
        this.hotel = hotel;
        this.checkin = checkin;
        this.checkout = checkout;
        this.guests = guests;
    }

    // getter / setter 如果需要的话...
    public String getHotel() {
        return hotel;
    }

    public String getCheckin() {
        return checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public List<Guest> getGuests() {
        return guests;
    }
}