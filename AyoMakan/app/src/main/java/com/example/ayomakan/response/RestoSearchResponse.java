package com.example.ayomakan.response;

import com.example.ayomakan.model.Resto;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RestoSearchResponse {
    @SerializedName("restaurants")
    private List<Resto> restos;

    public List<Resto> getRestos() {
        return restos;
    }

    public void setRestos(List<Resto> restos) {
        this.restos = restos;
    }
}
