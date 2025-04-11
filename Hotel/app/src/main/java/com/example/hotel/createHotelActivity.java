package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createHotelActivity extends AppCompatActivity {

    private EditText editTextName, editTextPrice;
    private Button btnAddHotel;
    private CreateViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity); // 对应上面的 create_activity.xml

        // 1. 找到界面控件
        editTextName = findViewById(R.id.editTextHotelName);
        editTextPrice = findViewById(R.id.editTextHotelPrice);
        btnAddHotel = findViewById(R.id.btnAddHotel);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(CreateViewModel.class);
        viewModel.getMessage().observe(this, msg -> {
            Toast.makeText(createHotelActivity.this, msg, Toast.LENGTH_SHORT).show();
            if ("Hotel added successfully".equals(msg)) {
                finish();
            }
        });

        // 2. 点击“添加酒店”按钮
        btnAddHotel.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String priceStr = editTextPrice.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(createHotelActivity.this,
                        "Please enter both hotel name and price",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                Toast.makeText(createHotelActivity.this,
                        "Invalid price format",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.createHotel(name, price);
        });
    }
}
