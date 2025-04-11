package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.view.View;

import com.example.hotel.R;

public class Screen4Activity extends AppCompatActivity {

    private TextView tvConfirmation;
    private Screen4ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen4);

        tvConfirmation = findViewById(R.id.tvConfirmation);
        viewModel = new Screen4ViewModel();

        // 获取 POST 返回的确认号
        String confirmationNumber = getIntent().getStringExtra("confirmation_number");
        viewModel.setConfirmationNumber(confirmationNumber);

        viewModel.getConfirmationMessage().observe(this, message -> {
            tvConfirmation.setText(message);
        });

        Button btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Screen4Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
