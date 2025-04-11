package com.example.hotel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class CreateViewModel extends ViewModel {

    private final MutableLiveData<String> message = new MutableLiveData<>();

    public LiveData<String> getMessage() {
        return message;
    }

    public void createHotel(String name, double price) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HotelApi api = retrofit.create(HotelApi.class);
        HotelRequest hotel = new HotelRequest(name, price);

        api.createHotel(hotel).enqueue(new Callback<HotelRequest>() {
            @Override
            public void onResponse(Call<HotelRequest> call, Response<HotelRequest> response) {
                if (response.isSuccessful()) {
                    message.postValue("Hotel added successfully");
                } else {
                    message.postValue("Failed to add hotel");
                }
            }

            @Override
            public void onFailure(Call<HotelRequest> call, Throwable t) {
                message.postValue("Error: " + t.getMessage());
            }
        });
    }

    interface HotelApi {
        @POST("/api/hotels/")
        Call<HotelRequest> createHotel(@Body HotelRequest hotel);
    }

    static class HotelRequest {
        @SerializedName("name")
        String name;

        @SerializedName("price")
        double price;

        public HotelRequest(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }
}
