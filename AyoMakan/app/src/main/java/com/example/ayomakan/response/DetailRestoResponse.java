package com.example.ayomakan.response;

import com.example.ayomakan.model.DetailResto;
import com.google.gson.annotations.SerializedName;

public class DetailRestoResponse {
    private boolean error;
    private String massage;

    @SerializedName("restaurant")
    private DetailResto DetailResto;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public DetailResto getDetailResto() {
        return DetailResto;
    }

    public void setDetailResto(DetailResto detailResto) {
        DetailResto = detailResto;
    }
}