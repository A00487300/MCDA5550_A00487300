package com.example.hotel;

public class Hotel {
    private String name;
    private double price;
    private boolean availability;

    public Hotel(String hotelName, double price, boolean availability) {
        this.name = hotelName;
        this.price = price;
        this.availability = availability;
    }

    public String getHotelName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailability() {
        return availability;
    }
}