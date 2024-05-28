package com.example.ayomakan.model;

import com.google.gson.annotations.SerializedName;
import android.os.Parcel;
import android.os.Parcelable;

public class Resto implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("city")
    private String city;

    @SerializedName("pictureId")
    private String pictureId;

    @SerializedName("rating")
    private float rating;

    // Getter dan Setter
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

    // Parcelable implementation
    protected Resto(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        city = in.readString();
        pictureId = in.readString();
        rating = in.readFloat();
    }

    public static final Creator<Resto> CREATOR = new Creator<Resto>() {
        @Override
        public Resto createFromParcel(Parcel in) {
            return new Resto(in);
        }

        @Override
        public Resto[] newArray(int size) {
            return new Resto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(city);
        dest.writeString(pictureId);
        dest.writeFloat(rating);
    }
}
