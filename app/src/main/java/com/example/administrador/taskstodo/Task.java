package com.example.administrador.taskstodo;

import com.orm.SugarRecord;

/**
 * Created by Juan Tavera on 31/10/2017.
 */

public class Task extends SugarRecord<Task> {

String title;
String description;
String date;
String webPage;
Double latitude;
Double longitude;
Boolean done;
Boolean urgent;
String imageSource;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public Task() {
    }

    public Task(String title, String description, String date, String webPage, Double latitude, Double longitude, Boolean done, Boolean urgent, String imageSource) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.webPage = webPage;
        this.latitude = latitude;
        this.longitude = longitude;
        this.done = done;
        this.urgent = urgent;
        this.imageSource = imageSource;

    }
}
