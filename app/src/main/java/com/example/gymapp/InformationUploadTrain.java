package com.example.gymapp;

public class InformationUploadTrain {
    Training training;
    Integer caloriesEaten;
    String imageLink;
    Long time;

    public InformationUploadTrain(Training training, Integer caloriesEaten, String imageLink, Long time) {
        this.training = training;
        this.caloriesEaten = caloriesEaten;
        this.imageLink = imageLink;
        this.time = time;
    }

    public InformationUploadTrain(){

    }




    public InformationUploadTrain(Training training, Integer caloriesEaten, Long time){
        this.training = training;
        this.caloriesEaten = caloriesEaten;
        this.time = time;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Integer getCaloriesEaten() {
        return caloriesEaten;
    }

    public void setCaloriesEaten(Integer caloriesEaten) {
        this.caloriesEaten = caloriesEaten;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
