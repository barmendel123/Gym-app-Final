package com.example.gymapp;

import java.util.ArrayList;

public class Training {


    Integer cloriesBurned;
    String name;
    Integer timeOfTrain;
    String imageOfTrain;
    ArrayList<Device> deviceArrayList;


    public Training(Integer clories, String name, Integer timeOfTrain, String imageOfTrain, ArrayList<Device> deviceArrayList) {
        this.cloriesBurned = clories;
        this.name = name;
        this.timeOfTrain = timeOfTrain;
        this.imageOfTrain = imageOfTrain;
        this.deviceArrayList = deviceArrayList;

    }

    public Training(){


    }


    public Integer getCloriesBurned() {
        return cloriesBurned;
    }

    public void setCloriesBurned(Integer cloriesBurned) {
        this.cloriesBurned = cloriesBurned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTimeOfTrain() {
        return timeOfTrain;
    }

    public void setTimeOfTrain(Integer timeOfTrain) {
        this.timeOfTrain = timeOfTrain;
    }

    public String getImageOfTrain() {
        return imageOfTrain;
    }

    public void setImageOfTrain(String imageOfTrain) {
        this.imageOfTrain = imageOfTrain;
    }

    public ArrayList<Device> getDeviceArrayList() {
        return deviceArrayList;
    }

    public void setDeviceArrayList(ArrayList<Device> deviceArrayList) {
        this.deviceArrayList = deviceArrayList;
    }



}