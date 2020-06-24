package com.example.hasanocakhomework3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    NewsItem selectedNew;
    ImageView newImage;
    TextView newDate;
    TextView newTitle;
    TextView newDescription;
    //List<CommentItem> comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        newImage = findViewById(R.id.textImage);
        newDate = findViewById(R.id.textDate);
        newTitle= findViewById(R.id.textTitle);
        newDescription = findViewById(R.id.textDescription);

        selectedNew = (NewsItem)getIntent().getSerializableExtra("selectedNewItem");
        //comments = selectedNew.getComments();

        if (selectedNew.getBitmap() == null) {
            new ImageDownloadTask(newImage).execute(selectedNew);
        } else {
            newImage.setImageBitmap(selectedNew.getBitmap());
        }
        newDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(selectedNew.getNewsDate()));
        newTitle.setText(selectedNew.getTitle());
        newDescription.setText(selectedNew.getText());

        getSupportActionBar().setTitle("News Details");

        ActionBar currentActionBar = getSupportActionBar();

        currentActionBar.setHomeButtonEnabled(true);
        currentActionBar.setDisplayHomeAsUpEnabled(true);
        currentActionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_24px);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mn_comment){
            Intent i = new Intent(this, CommentsActivity.class);
            i.putExtra("selectedNews", selectedNew);
            startActivity(i);
            return true;
        }

        else if(item.getItemId() == android.R.id.home){
            Intent i = new Intent();
            i.putExtra("updated_new", selectedNew);
            setResult(RESULT_OK, i);
            finish();
            return true;
        }
        return false;
    }

}
