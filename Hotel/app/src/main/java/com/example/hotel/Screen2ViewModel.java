package com.example.hotel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen2ViewModel extends ViewModel {

    private final MutableLiveData<List<Hotel>> hotelList = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<List<Hotel>> getHotelList() {
        return hotelList;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void fetchHotels(String checkin, String checkout, int guests) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Hotel>> call = apiService.getHotels(checkin, checkout, guests);

        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    hotelList.postValue(response.body());
                } else {
                    error.postValue("Failed to load hotels");
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                error.postValue("Error: " + t.getMessage());
            }
        });
    }
}
