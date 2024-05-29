package com.example.ayomakan.model;

import java.util.List;

public class Menus {
    private List<MenuItem> foods;
    private List<MenuItem> drinks;

    public List<MenuItem> getFoods() {
        return foods;
    }

    public void setFoods(List<MenuItem> foods) {
        this.foods = foods;
    }

    public List<MenuItem> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<MenuItem> drinks) {
        this.drinks = drinks;
    }
}