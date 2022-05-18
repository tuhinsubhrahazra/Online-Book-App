package com.example.introduction.onlinebookapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    TextView tv6;
    ArrayList<BookModel> bookModelArrayList;
    private RequestQueue RequestQueue;
    private ProgressBar progressBar;
    String query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // initializing our views.
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        tv6=findViewById(R.id.tv6);

        query = getIntent().getStringExtra("search");
        tv6.setText(query+" Book collection");

        getJson(query);

    }

    private void getJson(String query) {
        // creating a new array list.
        bookModelArrayList = new ArrayList<>();

        // the variable for our request queue.
        RequestQueue = Volley.newRequestQueue(ListActivity.this);

        //  clear cache this will be use when our data is being updated.
        RequestQueue.getCache().clear();

        //  url for getting data from API in json format.
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query;

        //  creating a new request queue.
        RequestQueue queue = Volley.newRequestQueue(ListActivity.this);



        // request json object  and  passing url, get method and getting json object. .
        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                // inside on response method we are extracting all our json data.
                try {
                    JSONArray itemsArray = response.getJSONArray("items");
                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject itemsObj = itemsArray.getJSONObject(i);
                        JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
                        String title = volumeObj.optString("title");
                        String subtitle = volumeObj.optString("subtitle");
                        JSONArray authorsArray = volumeObj.getJSONArray("authors");
                        String author = authorsArray.getString(0);
                        String publisher = volumeObj.optString("publisher");
                        String publishedDate = volumeObj.optString("publishedDate");
                        String description = volumeObj.optString("description");
                        int pageCount = volumeObj.optInt("pageCount");
                        JSONObject imageLinks = volumeObj.optJSONObject("imageLinks");
                        String thumbnail = imageLinks.optString("thumbnail");
                        thumbnail= thumbnail.replace("http","https");
                        String language =volumeObj.optString("language");
                        language= language.replace("en","English");
                        String previewLink = volumeObj.optString("previewLink");
                        String infoLink = volumeObj.optString("infoLink");
                        JSONObject saleInfoObj = itemsObj.optJSONObject("saleInfo");
                        String buyLink = saleInfoObj.optString("buyLink");

                        // after extracting all the data we are
                        // saving this data in our modal class.
                       BookModel bookInfo = new BookModel(title, subtitle, author, publisher, publishedDate, description, pageCount, thumbnail,language, previewLink, buyLink);

                        // pass our modal class in our array list.
                        bookModelArrayList.add(bookInfo);
                        getRecycleView();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    // displaying a toast message when we get any error from API
                    Toast.makeText(ListActivity.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also displaying error message in toast.
                Toast.makeText(ListActivity.this, "Error found is " + error, Toast.LENGTH_SHORT).show();

            }
        });
        // at last we are adding our json object
        // request in our request queue.
        queue.add(booksObjrequest);
    }

    private void getRecycleView() {
        SwipeRefreshLayout refreshLayout= findViewById(R.id.refresh);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BookAdapter adapter=new BookAdapter(bookModelArrayList, ListActivity.this);
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });



    }


}