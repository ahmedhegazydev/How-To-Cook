package com.example.ahmed.fooddrink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ahmed on 24/07/17.
 */

public class ListAdapter extends BaseAdapter {


    ArrayList<FoodItem> foodItems = null;
    Context context = null;

    public ListAdapter(ArrayList<FoodItem> foodItems, Context context) {
        this.foodItems = foodItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.foodItems.size();
    }

    @Override
    public FoodItem getItem(int position) {
        return this.foodItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {


        View view2 = LayoutInflater.from(context).inflate(R.layout.list_item_raw, null);
        ImageView imageView = (ImageView) view2.findViewById(R.id.foodImage);
        TextView textView = (TextView) view2.findViewById(R.id.foodLabel);


        FoodItem foodItem = getItem(position);

        Picasso.with(context).load(foodItem.getImgUrl()).into(imageView);
        textView.setText(foodItem.getImgLabel());


        view2.setTag(foodItem.getIngredients().toString());

        return view2;

    }


}
