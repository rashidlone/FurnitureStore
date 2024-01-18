package com.assignment.furniturestore;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String API_URL = "https://YOUR_WEBSITE.com/temp/";
    private GridView gridView;
    private ProgressBar progressBar;
    private FurnitureAdapter furnitureAdapter;
    private SearchView searchView;
    private List<FurnitureItem> furnitureItems;
    private List<FurnitureItem> filteredFurnitureItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        progressBar = findViewById(R.id.progressBar);
        searchView = findViewById(R.id.searchView);

        furnitureItems = new ArrayList<>();
        filteredFurnitureItems = new ArrayList<>();
        furnitureAdapter = new FurnitureAdapter(this, filteredFurnitureItems);

        gridView.setAdapter(furnitureAdapter);

        fetchDataFromServer();

        setupSearchView();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterFurniture(newText);
                return true;
            }
        });
    }

    private void filterFurniture(String query) {
        filteredFurnitureItems.clear();

        for (FurnitureItem item : furnitureItems) {
            if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredFurnitureItems.add(item);
            }
        }

        furnitureAdapter.notifyDataSetChanged();
    }

    private void fetchDataFromServer() {
        showProgressBar();

        String url = MainActivity.API_URL+"get_furniture.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        hideProgressBar();
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressBar();
                        Toast.makeText(MainActivity.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void parseJson(JSONArray jsonArray) {
        furnitureItems.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                String description = jsonObject.getString("description");
                double price = jsonObject.getDouble("price");
                int quantity = jsonObject.getInt("quantity");

                FurnitureItem furnitureItem = new FurnitureItem(id, name, description, price, quantity);
                furnitureItems.add(furnitureItem);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Initially, display all items
        filteredFurnitureItems.addAll(furnitureItems);
        furnitureAdapter.notifyDataSetChanged();
    }

    private void showProgressBar() {
        progressBar.setVisibility(android.view.View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(android.view.View.GONE);
    }
}
