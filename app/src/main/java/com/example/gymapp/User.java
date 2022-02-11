package com.example.gymapp;

public class User {
    Integer height, weight, age;

    public User(String height, String weight, String age) {
        this.height = Integer.valueOf(height);
        this.weight = Integer.valueOf(weight);
        this.age = Integer.valueOf(age);
    }
    public User(){

    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
