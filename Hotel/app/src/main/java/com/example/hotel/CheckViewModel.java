package com.example.hotel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CheckViewModel extends ViewModel {

    private final MutableLiveData<List<String>> reservations = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<List<String>> getReservations() {
        return reservations;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void fetchReservations() {
        new Thread(() -> {
            List<String> details = new ArrayList<>();
            try {
                URL url = new URL("http://10.0.2.2:8000/api/reservationConfirmation/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject reservation = jsonArray.getJSONObject(i);
                    String hotelName = reservation.getString("hotel_name");
                    String checkin = reservation.getString("checkin");
                    String checkout = reservation.getString("checkout");
                    String confirmation = reservation.getString("confirmation_number");
                    JSONArray guests = reservation.getJSONArray("guests");
                    for (int j = 0; j < guests.length(); j++) {
                        JSONObject guest = guests.getJSONObject(j);
                        String guestInfo = "Hotel: " + hotelName +
                                "\nConfirmation #: " + confirmation +
                                "\nCheck-in: " + checkin +
                                "\nCheck-out: " + checkout +
                                "\nGuest: " + guest.getString("guest_name") +
                                " (" + guest.getString("gender") + ")";
                        details.add(guestInfo);
                    }
                }
                reservations.postValue(details);

            } catch (Exception e) {
                error.postValue("Failed to fetch data");
                e.printStackTrace();
            }
        }).start();
    }
}
