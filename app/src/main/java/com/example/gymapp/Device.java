package com.example.gymapp;

public class Device {
    String name, urlImage, urlVideo;



    public Device(String name, String urlImage, String urlVideo) {
        this.name = name;
        this.urlImage = urlImage;
        this.urlVideo = urlVideo;
    }

    public Device(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }
}
