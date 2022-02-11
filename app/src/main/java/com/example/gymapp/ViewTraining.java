package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewTraining extends AppCompatActivity {
    RecyclerView recyclerView;
    TrainingAdapter trainingAdapter;
    ArrayList<Training> trainingArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_training);

        recyclerView = findViewById(R.id.RecycleViewTranings);

        getTraningArrayList();
        trainingAdapter = new TrainingAdapter(trainingArrayList, true, this);
        recyclerView.setAdapter(trainingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



public void getTraningArrayList() {
    SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHAREPREFENCES, MODE_PRIVATE);
    Gson gson = new Gson();
    String json = sharedPreferences.getString(MainActivity.TRAINS, "");
    Type type = new TypeToken<ArrayList<Training>>() {
    }.getType();

    trainingArrayList = gson.fromJson(json, type);

}
}