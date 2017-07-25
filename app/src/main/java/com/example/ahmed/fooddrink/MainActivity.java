package com.example.ahmed.fooddrink;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ahmed.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {


    ListView listView = null;
    Context context = null;
    String appKeys = null;
    String appId = null;
    String url = "";
    ListAdapter listAdapter = null;
    ArrayList<String> urls = new ArrayList<String>();
    ArrayList<ArrayList<String>> arrayLists = new ArrayList<ArrayList<String>>();
    final static String KEY_IMG_URL = "KEY_IMG_URL", KEY_ingredients = "KEY_ingredients";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (ListView) findViewById(R.id.lvFoods);
        listView.setOnItemClickListener(this);

        context = this;

        appKeys = getResources().getString(R.string.app_keys);
        appId = getResources().getString(R.string.app_id);
        url = "https://api.edamam.com/search?q=chicken&app_id=" + appId + "&app_key=" +
                appKeys + "&from=0&to=300";

        createRequestQueue(url);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Implementing ActionBar Search inside a fragment
        MenuItem item = menu.add("Search");
        item.setIcon(android.R.drawable.ic_menu_search); // sets icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView sv = new SearchView(getApplicationContext());

        // modifying the text inside edittext component
        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv.findViewById(id);
        textView.setHint("Fishes/Birds/Cartoons/Fashion/Nature/ .....");
        textView.setHintTextColor(getResources().getColor(R.color.clr1));
        textView.setTextColor(getResources().getColor(R.color.clr2));

        // implementing the listener
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (s != null)
                    searchForFood(s);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        item.setActionView(sv);

        return super.onCreateOptionsMenu(menu);
    }

    private void searchForFood(String s) {

        url = "https://api.edamam.com/search?q=" + s + "&app_id=" + appId + "&app_key=" +
                appKeys + "&from=0&to=300";
        createRequestQueue(url);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    private void createRequestQueue(String url) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            String imgUrl = "";
                            String imgTags = "";


                            ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();


                            JSONObject jsonObject = new JSONObject(response.toString());

                            JSONArray jsonArray = jsonObject.getJSONArray("hits");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JSONObject jsonObject2 = jsonObject1.getJSONObject("recipe");

                                ArrayList<String> strings = new ArrayList<String>();
                                JSONArray jsonArray1 = jsonObject2.getJSONArray("ingredients");
                                for (int j = 0; j < jsonArray1.length(); j++) {

                                    strings.add(jsonArray1.getJSONObject(j).getString("text"));
                                }


                                imgUrl = jsonObject2.getString("image");
                                imgTags = jsonObject2.getString("label");

                                foodItems.add(new FoodItem(imgUrl, imgTags, strings));
                                arrayLists.add(strings);
                                urls.add(imgUrl);


                            }

                            listAdapter = new ListAdapter(foodItems, context);
                            listView.setAdapter(listAdapter);
                            listAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //createToast(error.getMessage().toString());
//                createSnackBar(findViewById(R.id.container), "No internet connection", Snackbar.LENGTH_LONG);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        listView.setItemChecked(position, true);
        //fireToast(position+"");

        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra(KEY_IMG_URL, urls.get(position));
        intent.putExtra(KEY_ingredients, arrayLists.get(position));
        startActivity(intent);

//        Log.e("res", urls.get(position));
//        Log.e("res", arrayLists.get(position).toString());


    }

    private void fireToast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


}
