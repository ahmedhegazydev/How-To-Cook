package com.example.ahmed.fooddrink;

import java.util.ArrayList;

/**
 * Created by ahmed on 24/07/17.
 */

public class FoodItem {


    String imgUrl = null;
    String imgLabel = null;

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    ArrayList<String> ingredients = new ArrayList<String>();


    public FoodItem(String imgUrl, String imgLabel, ArrayList<String> ingredients) {
        this.imgUrl = imgUrl;
        this.imgLabel = imgLabel;
        this.ingredients = ingredients;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getImgLabel() {
        return imgLabel;
    }



}
