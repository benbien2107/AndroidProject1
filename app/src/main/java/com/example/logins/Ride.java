package com.example.logins;

import android.widget.ImageView;

import java.time.LocalDateTime;

public class Ride {

    private int image;
    private String place;
    private String car; // might change into an imageField later
    private LocalDateTime dateObj;
    private String id; // a string to identify

    public Ride (String place, String car, LocalDateTime dateObj, String id, int image){
        this.place = place;
        this.car = car;
        this.dateObj = dateObj;
        this.id = id;
        this.image = image;
    }

    public String getPlace () {return place;}
    public void setPlace (String place) {this.place = place;}
    public String getCar (){return car;}
    public void setCar (String car) {this.car = car;}
    public LocalDateTime getDateTime() {return dateObj;}
    public void setDateTime( LocalDateTime datetime) { dateObj = datetime;}
    public String getId () {return id;}
    public void setId(String id) { this.id = id;}
    public int getImageView() {return image; }
    public void setImage(int image) { this.image = image;}

}

