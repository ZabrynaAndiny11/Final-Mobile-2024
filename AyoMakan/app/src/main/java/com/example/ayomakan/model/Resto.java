package com.example.ayomakan.model;

import java.io.Serializable;

public class Resto implements Serializable {
    private String id;
    private String name;
    private String description;
    private String city;
    private String pictureId;
    private float rating;

    public Resto(String id, String name, String description, String city, String pictureId, float rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.pictureId = pictureId;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}