package com.example.introduction.onlinebookapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class DetailsActivity extends AppCompatActivity {
    String title,publisher, publishedDate,language, description, thumbnail, previewLink, infoLink, buyLink;
    int pageCount;

    TextView TVTitle,TVpublisher,TVpage,TVlanguage,TVPublishDate,TVdescription;
    Button previewBtn, buyBtn;
    private ImageView IVbook;
    ImageButton IVback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

            findId();     // initializing our views..

            getData();      // getting the data which we have passed from our adapter class.

            setData();     // after getting the data we are setting that data to our text views and image view.

            // initializing on click listener for preview button.
            previewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (previewLink.isEmpty()) {
                    // below toast message is displayed when preview link is not present.
                    Toast.makeText(DetailsActivity.this, "Oops Sorry!!\nNo preview Link present for this book", Toast.LENGTH_SHORT).show();
                    return;
                }
                // if the link is present we are opening that link .
                Uri uri = Uri.parse(previewLink);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
                 }
            });

            // initializing on click listener for buy button.
            buyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (buyLink.isEmpty()) {
                    // below toast message is displaying when buy link is empty.
                    Toast.makeText(DetailsActivity.this, "Oops Sorry!!\nNo buy page present for this book", Toast.LENGTH_SHORT).show();
                    return;
                }
                // if the link is present we are opening the link.
                Uri uri = Uri.parse(buyLink);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
                 }
            });
    }

    //set method
    private void setData() {
        TVTitle.setText(title);
        TVpublisher.setText(publisher);
        TVPublishDate.setText("" + publishedDate);
        TVdescription.setText(description);
        TVpage.setText("" +pageCount);
        Glide.with(DetailsActivity.this).load(thumbnail).into(IVbook);
        //Picasso.get().load(thumbnail).into(IVbook);
        TVlanguage.setText(language);

    }

    //initializing id
    private void findId() {
        TVTitle = findViewById(R.id.TVTitle);
        TVpublisher= findViewById(R.id.TVpublisher);
        TVpage= findViewById(R.id.TVpage);
        TVlanguage= findViewById(R.id.TVlanguage);
        TVPublishDate= findViewById(R.id.TVPublishDate);
        TVdescription= findViewById(R.id.TVdescription);
        IVbook=findViewById(R.id.IVbook);
        IVback=findViewById(R.id.IVback);
        previewBtn = findViewById(R.id.idBtnPreview);
        buyBtn = findViewById(R.id.idBtnBuy);
    }

    //get data method
    private void getData() {

        title = getIntent().getStringExtra("title");
        publisher = getIntent().getStringExtra("publisher");
        publishedDate = getIntent().getStringExtra("publishedDate");
        description = getIntent().getStringExtra("description");
        language=getIntent().getStringExtra("language");
        pageCount = getIntent().getIntExtra("pageCount", 0);
        thumbnail = getIntent().getStringExtra("thumbnail");
        previewLink = getIntent().getStringExtra("previewLink");
        infoLink = getIntent().getStringExtra("infoLink");
        buyLink = getIntent().getStringExtra("buyLink");

    }
}