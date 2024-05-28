package com.example.ayomakan.response;

import com.example.ayomakan.model.Resto;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RestoResponse {

    @SerializedName("restaurants")
    private List<Resto> restoList;

    public List<Resto> getRestoList() {
        return restoList;
    }

    public void setRestoList(List<Resto> restoList) {
        this.restoList = restoList;
    }
}
