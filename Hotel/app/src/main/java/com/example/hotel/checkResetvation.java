package com.example.hotel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;

public class checkResetvation extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> reservationDetails;
    private Button back;
    private CheckViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reservation);
        listView = findViewById(R.id.listViewReservations);
        back = findViewById(R.id.btnBackHome);
        reservationDetails = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reservationDetails);
        listView.setAdapter(adapter);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(checkResetvation.this, MainActivity.class);
            startActivity(intent);
        });

        viewModel = new ViewModelProvider(this).get(CheckViewModel.class);
        viewModel.getReservations().observe(this, list -> {
            reservationDetails.clear();
            reservationDetails.addAll(list);
            adapter.notifyDataSetChanged();
        });
        viewModel.getError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.fetchReservations();
    }

}
