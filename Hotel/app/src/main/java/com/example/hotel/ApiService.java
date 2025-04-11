package com.example.hotel;

import com.example.hotel.ReservationRequest;
import com.example.hotel.ReservationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // GET: 拉取酒店列表
    @GET("hotels/")
    Call<List<Hotel>> getHotels(
            @Query("checkin") String checkin,
            @Query("checkout") String checkout,
            @Query("guests") int guests
    );

    @POST("hotels/")
    Call<Hotel> addHotel(@Body Hotel newHotel);


    // POST: 提交预订信息
    @POST("reservationConfirmation/")
    Call<ReservationResponse> submitReservation(@Body ReservationRequest request);
}
