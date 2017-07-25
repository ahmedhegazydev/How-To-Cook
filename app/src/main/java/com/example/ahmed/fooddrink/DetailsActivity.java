package com.example.ahmed.fooddrink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ahmed.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    LinearLayout llIngredients = null;
    ImageView ivFoodImage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();


    }

    private void init() {

        //get passed data
        Intent intent = getIntent();
        String foodImgUrl = intent.getStringExtra(MainActivity.KEY_IMG_URL);
        ArrayList<String> foodIngredients = (ArrayList<String>) intent.getSerializableExtra(MainActivity.KEY_ingredients);

        ivFoodImage = (ImageView) findViewById(R.id.detailsImage);
        llIngredients = (LinearLayout) findViewById(R.id.detailsIngredients);


        Picasso.with(getApplicationContext()).load(foodImgUrl).into(ivFoodImage);

        Log.e("res", foodIngredients.toString());


        for (int i = 0; i < foodIngredients.size(); i++) {

            View view = getLayoutInflater().inflate(R.layout.ingredient_raw, null);

            View view1 = view.findViewById(R.id.ll1);

            TextView textView = (TextView) view1.findViewById(R.id.tvLines);
            textView.setText(foodIngredients.get(i));

            llIngredients.addView(view);

        }


    }
}
