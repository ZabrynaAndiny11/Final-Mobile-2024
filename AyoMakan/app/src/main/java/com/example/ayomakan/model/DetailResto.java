package com.example.ayomakan.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailResto {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("city")
    private String city;

    @SerializedName("address")
    private String address;

    @SerializedName("pictureId")
    private String pictureId;

    @SerializedName("rating")
    private float rating;

    private Menus menus;
    private List<CustomerReview> customerReviews;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Menus getMenus() {
        return menus;
    }

    public void setMenus(Menus menus) {
        this.menus = menus;
    }

    public List<CustomerReview> getCustomerReviews() {
        return customerReviews;
    }

    public void setCustomerReviews(List<CustomerReview> customerReviews) {
        this.customerReviews = customerReviews;
    }
}