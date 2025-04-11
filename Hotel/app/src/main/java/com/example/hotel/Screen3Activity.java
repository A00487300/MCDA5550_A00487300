package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import com.example.hotel.R;
import com.example.hotel.Guest;
import com.example.hotel.ReservationRequest;
import com.example.hotel.ReservationResponse;
import com.example.hotel.ApiService;
import com.example.hotel.RetrofitClient;
import com.example.hotel.GuestListAdapter;

import java.util.List;

public class Screen3Activity extends AppCompatActivity {
    private TextView tvSelectedHotel;
    private RecyclerView rvGuests;
    private Button btnSubmit;

    private Screen3ViewModel viewModel;
    // 这几个字段改成和后端要求的字段一致
    private String hotel;
    private String checkin;
    private String name;
    private String checkout;
    private int guestsCount;


    private GuestListAdapter guestListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        tvSelectedHotel = findViewById(R.id.tvSelectedHotel);
        rvGuests = findViewById(R.id.rvGuests);
        btnSubmit = findViewById(R.id.btnSubmit);

        // 从 Intent 获取上一个页面传来的数据
        // 这里把变量名改为 hotel_name、checkin、checkout 等
        hotel = getIntent().getStringExtra("hotel_name");
        checkin    = getIntent().getStringExtra("checkin");
        checkout   = getIntent().getStringExtra("checkout");
        guestsCount= getIntent().getIntExtra("guests", 1);
        name    = getIntent().getStringExtra("name");

        // 显示信息，仅供参考
        String infoText = "Hotel name: " + hotel + "\n"
                + "Check In: " + checkin + "\n"
                + "Check Out: " + checkout + "\n"
                + "Name: " + name + "\n"
                + "Guests: " + guestsCount;
        tvSelectedHotel.setText(infoText);

        // 初始化 RecyclerView
        rvGuests.setLayoutManager(new LinearLayoutManager(this));
        guestListAdapter = new GuestListAdapter(guestsCount);
        rvGuests.setAdapter(guestListAdapter);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(Screen3ViewModel.class);
        viewModel.getConfirmationNumber().observe(this, confirmationNumber -> {
            if (confirmationNumber != null) {
                Intent intent = new Intent(Screen3Activity.this, Screen4Activity.class);
                intent.putExtra("confirmation_number", confirmationNumber);
                startActivity(intent);
            }
        });
        viewModel.getError().observe(this, errorMsg -> {
            if (errorMsg != null) {
                Toast.makeText(Screen3Activity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });


        // 点击提交按钮
        btnSubmit.setOnClickListener(v -> {


            // 从适配器获取用户填写的全部客人信息
            List<Guest> guests_list = guestListAdapter.getGuestsData();


            // 简单检查是否填写完整
            for (Guest g : guests_list) {
                if (g.getGuestName().isEmpty() || g.getGender().isEmpty()) {
                    Toast.makeText(Screen3Activity.this, "Please fill all guests info", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            viewModel.submitReservation(hotel, checkin, checkout, guests_list);

        });
    }
}