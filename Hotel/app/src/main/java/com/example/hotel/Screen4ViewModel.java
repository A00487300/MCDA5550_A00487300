package com.example.hotel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Screen4ViewModel extends ViewModel {

    private final MutableLiveData<String> confirmationMessage = new MutableLiveData<>();

    public LiveData<String> getConfirmationMessage() {
        return confirmationMessage;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        if (confirmationNumber == null || confirmationNumber.isEmpty()) {
            confirmationNumber = "N/A";
        }

        String msg = "Thank you for your reservation!\nYour reservation number is: " + confirmationNumber;
        confirmationMessage.setValue(msg);
    }
}
