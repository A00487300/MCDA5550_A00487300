package com.example.hotel;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToScreen2 = new MutableLiveData<>();

    private String checkInDate;
    private String checkOutDate;
    private int guestsNumber;
    private String userName;

    private final SharedPreferences prefs;

    public MainViewModel(@NonNull Application application) {
        super(application);
        prefs = application.getSharedPreferences("MyPrefs", Application.MODE_PRIVATE);
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public LiveData<Boolean> getNavigateToScreen2() {
        return navigateToScreen2;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public int getGuestsNumber() {
        return guestsNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void handleSearchClick(int yearIn, int monthIn, int dayIn,
                                  int yearOut, int monthOut, int dayOut,
                                  String guestsStr, String userNameInput) {

        if (yearOut < yearIn || (yearOut == yearIn && monthOut < monthIn)
                || (yearOut == yearIn && monthOut == monthIn && dayOut <= dayIn)) {
            toastMessage.setValue("Data is invalid");
            return;
        }

        if (guestsStr.isEmpty()) {
            toastMessage.setValue("Please fill all fields");
            return;
        }

        int guests;
        try {
            guests = Integer.parseInt(guestsStr);
        } catch (NumberFormatException e) {
            toastMessage.setValue("Guests must be an integer");
            return;
        }

        this.checkInDate = yearIn + "-" + monthIn + "-" + dayIn;
        this.checkOutDate = yearOut + "-" + monthOut + "-" + dayOut;
        this.guestsNumber = guests;
        this.userName = userNameInput;

        // 保存 SharedPreferences
        prefs.edit()
                .putString("user_name", userName)
                .putInt("user_count", guestsNumber)
                .apply();

        navigateToScreen2.setValue(true);
    }
}
