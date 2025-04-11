package com.example.hotel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Screen3ViewModel extends ViewModel {

    private final MutableLiveData<String> confirmationNumber = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    // 用于 Activity 观察
    public LiveData<String> getConfirmationNumber() {
        return confirmationNumber;
    }

    public LiveData<String> getError() {
        return error;
    }

    // 调用 API 提交预订信息
    public void submitReservation(String hotel, String checkin, String checkout, List<Guest> guestsList) {
        ReservationRequest request = new ReservationRequest(hotel, checkin, checkout, guestsList);

        ApiService apiService = RetrofitClient.getApiService();
        Call<ReservationResponse> call = apiService.submitReservation(request);

        call.enqueue(new Callback<ReservationResponse>() {
            @Override
            public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    confirmationNumber.postValue(response.body().getConfirmation_number());
                } else {
                    error.postValue("Reservation failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                error.postValue("Error: " + t.getMessage());
            }
        });
    }
}
