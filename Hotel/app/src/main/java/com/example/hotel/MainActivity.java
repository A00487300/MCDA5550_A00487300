package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.hotel.R;

public class MainActivity extends AppCompatActivity {

    private EditText etGuests, etUserName;
    private DatePicker etCheckIn, etCheckOut;
    private Button btnSearch;
    private Button addHotel;
    private Button checkResetvation;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCheckIn = findViewById(R.id.checkin_calendar_view);
        etCheckOut = findViewById(R.id.etCheckOut);
        etGuests = findViewById(R.id.etGuests);
        etUserName = findViewById(R.id.etUserName);
        btnSearch = findViewById(R.id.btnSearch);
        addHotel = findViewById(R.id.addHotel);
        checkResetvation = findViewById(R.id.checkReservation);

        final SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedName = prefs.getString("user_name", "");
        if (savedName != null && !savedName.isEmpty()) {
            etUserName.setText(savedName);
        }

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getToastMessage().observe(this, msg -> {
            if (msg != null && !msg.isEmpty()) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getNavigateToScreen2().observe(this, go -> {
            if (go != null && go) {
                Intent intent = new Intent(MainActivity.this, Screen2Activity.class);
                intent.putExtra("checkIn", viewModel.getCheckInDate());
                intent.putExtra("checkOut", viewModel.getCheckOutDate());
                intent.putExtra("guests", viewModel.getGuestsNumber());
                intent.putExtra("name", viewModel.getUserName());
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(v -> {
            viewModel.handleSearchClick(
                etCheckIn.getYear(), etCheckIn.getMonth() + 1, etCheckIn.getDayOfMonth(),
                etCheckOut.getYear(), etCheckOut.getMonth() + 1, etCheckOut.getDayOfMonth(),
                etGuests.getText().toString().trim(),
                etUserName.getText().toString().trim()
            );
        });

        addHotel.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, createHotelActivity.class);
            startActivity(intent);
        });

        checkResetvation.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, checkResetvation.class);
            startActivity(intent);
        });
    }
}