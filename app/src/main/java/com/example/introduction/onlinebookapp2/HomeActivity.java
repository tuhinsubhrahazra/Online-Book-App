package com.example.introduction.onlinebookapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    ProgressBar progressBar;
    EditText search;
    ImageButton btn_search;
    ArrayList<BookModel> bookModelArrayList;
    Intent intent;
    TextView seeAll;

   BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //below line is use initializing id
        findId();

        // initializing on click listener for search button.
        btn_search.setOnClickListener(view -> {
                //if string is empty them shoe a toast massage
            if (search.getText().toString().isEmpty()) {
                Toast.makeText(HomeActivity.this,"Please enter your book name",Toast.LENGTH_LONG).show();
            }
            else {
                intent = new Intent(HomeActivity.this, ListActivity.class);
               // passing  input data in next intent
                intent.putExtra("search", search.getText().toString());
                startActivity(intent);
            }
        });
        //progressbar initializing and start
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        //popularGetData method for popular books
        popularGetData();
        // home activity bottom navigation method
        bottomnavigationview();
        // initializing on click listener for seeAll button.
        seeAll.setOnClickListener(view -> {
            Intent intent= new Intent(HomeActivity.this,PopulerSeeAll.class);
            startActivity(intent);
        });

    }

    private void popularGetData() {
        FirebaseFirestore fs= FirebaseFirestore.getInstance();
        fs.collection("populerGetData").get()
                .addOnFailureListener(e -> {
                    // also displaying error message get any error from database.
                    Toast.makeText(HomeActivity.this, "No data found" + e, Toast.LENGTH_SHORT).show();
                })
                .addOnCompleteListener(task -> {
                    QuerySnapshot qs= task.getResult();
                    List<DocumentSnapshot> dsList =qs.getDocuments();
                    bookModelArrayList = new ArrayList<>();
                    for (DocumentSnapshot ds: dsList){
                        BookModel bookInfo = new BookModel(
                                ds.getString("title"),
                                ds.getString("authors"),
                                ds.getString("thumbnail"),
                                ds.getString("previewLink")
                        );

                        bookModelArrayList.add(bookInfo);
                    }
                    //recycler view for popularAdapter
                    populerRecyclerView();
                });


    }

    private void populerRecyclerView() {
        progressBar.setVisibility(View.GONE);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.populer_recyclerview);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);

        recyclerView.setLayoutManager(layoutManager);
        populerApater adapter= new populerApater(bookModelArrayList,HomeActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private void bottomnavigationview() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_menu:
                        Toast.makeText(getApplicationContext(),"Home panel is open",Toast.LENGTH_SHORT).show();

                        //repleceFragment(new HomeFragment());

                        break;
                    case R.id.book_menu:
                        Toast.makeText(getApplicationContext(),"book panel is open",Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(HomeActivity.this,PopulerSeeAll.class);
                        startActivity(intent);
                        //repleceFragment(new bookFragment());
                        break;
                    case R.id.profile_menu:
                        Toast.makeText(getApplicationContext(),"profile panel is open",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
                        break;
                }
                return true;
            }
        });


    }


    private void findId() {
        progressBar=findViewById(R.id.progressBar);
        search=findViewById(R.id.search);
        btn_search=findViewById(R.id.btn_search);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        seeAll=findViewById(R.id.seeall);

    }
}