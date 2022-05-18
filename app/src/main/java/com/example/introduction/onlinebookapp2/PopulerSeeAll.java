package com.example.introduction.onlinebookapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PopulerSeeAll extends AppCompatActivity {

    private ArrayList<BookModel> bookArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_populer_see_all);
        //popularGetData method for popular books
        getPopulerData();
    }

    private void getPopulerData() {
        FirebaseFirestore fs= FirebaseFirestore.getInstance();
        fs.collection("populerGetData").get()
                .addOnFailureListener(e -> {

                })
                .addOnCompleteListener(task -> {
                    QuerySnapshot qs= task.getResult();
                    List<DocumentSnapshot> dsList =qs.getDocuments();
                    bookArrayList = new ArrayList<>();
                    for (DocumentSnapshot ds: dsList){
                        // assigning in the model class to access
                        BookModel bookInfo = new BookModel(
                                ds.getString("title"),
                                ds.getString("subtitle"),
                                ds.getString("authors"),
                                ds.getString("publisher"),
                                ds.getString("publishedDate"),
                                ds.getString("description"),
                                Integer.parseInt(ds.getString("pageCount")),
                                ds.getString("thumbnail"),
                                ds.getString("language"),
                                ds.getString("previewLink"),
                                ds.getString("buyLink")
                        );
                        bookArrayList.add(bookInfo);
                    }
                    //recycler view for BookAdapter all popular books
                    getRecycleView();
                });

    }

    private void getRecycleView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.seeAllRecyclerView);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BookAdapter adapter=new BookAdapter(bookArrayList, PopulerSeeAll.this);
        recyclerView.setAdapter(adapter);

    }
}