package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class TrainingDevices extends AppCompatActivity {
    RecyclerView recyclerView;
    DeviceAdapter deviceAdapter;
    StaggeredGridLayoutManager layoutManager;
    ArrayList<Device> deviceArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_devices);
        loadDevices();
        recyclerView = findViewById(R.id.RecyclleView);
        deviceAdapter = new DeviceAdapter(deviceArrayList, this);
        layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(deviceAdapter);
        recyclerView.setLayoutManager(layoutManager);


    }

    public void loadDevices(){

        SharedPreferences sharedPreferences =getSharedPreferences(MainActivity.SHAREPREFENCES, MODE_PRIVATE);
        Gson gson = new Gson();
        String Json = sharedPreferences.getString(MainActivity.DEVICES, "");

        Type type = new TypeToken<ArrayList<Device>>(){}.getType();
        deviceArrayList = gson.fromJson(Json, type);

    }
}