package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.Hotel;
import com.example.hotel.HotelListAdapter;
import com.example.hotel.Screen2ViewModel;

import java.util.List;

public class Screen2Activity extends AppCompatActivity {

    private TextView tvInfo;
    private RecyclerView rvHotels;
    private Button btnNext;
    private Screen2ViewModel viewModel;

    private String checkin;
    private String checkout;
    private String name;
    private int guests;
    private Hotel selectedHotel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        tvInfo = findViewById(R.id.tvInfo);
        rvHotels = findViewById(R.id.rvHotels);
        btnNext = findViewById(R.id.btnNext);
        viewModel = new ViewModelProvider(this).get(Screen2ViewModel.class);

        // 获取上个页面传来的数据
        checkin = getIntent().getStringExtra("checkIn");
        checkout = getIntent().getStringExtra("checkOut");
        guests = getIntent().getIntExtra("guests", 1);
        name = getIntent().getStringExtra("name");

        String infoText = "CheckIn: " + checkin + "\nCheckOut: " + checkout + "\nName: " + name + "\nGuests: " + guests;
        tvInfo.setText(infoText);

        rvHotels.setLayoutManager(new LinearLayoutManager(this));

        // 调用 ViewModel 获取酒店列表
        viewModel.fetchHotels(checkin, checkout, guests);
        viewModel.getHotelList().observe(this, hotelList -> {
            if (hotelList != null && !hotelList.isEmpty()) {
                String newText = infoText + "\nSelect hotel:";
                tvInfo.setText(newText);

                HotelListAdapter adapter = new HotelListAdapter(hotelList, hotel -> {
                    // 当用户点击某个hotel时
                    selectedHotel = hotel;
                });
                rvHotels.setAdapter(adapter);
            } else {
                Toast.makeText(Screen2Activity.this, "No hotels found", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(Screen2Activity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (selectedHotel == null) {
                Toast.makeText(Screen2Activity.this, "Please select a hotel", Toast.LENGTH_SHORT).show();
            } else {
                // 跳转到 Screen3
                Intent intent = new Intent(Screen2Activity.this, Screen3Activity.class);
                intent.putExtra("hotel_name", selectedHotel.getHotelName());
                intent.putExtra("hotelPrice", selectedHotel.getPrice());
                intent.putExtra("checkin", checkin);
                intent.putExtra("checkout", checkout);
                intent.putExtra("guests", guests);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }
}