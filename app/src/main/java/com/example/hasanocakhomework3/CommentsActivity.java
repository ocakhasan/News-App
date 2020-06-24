package com.example.hasanocakhomework3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity implements CommentAdapter.CommentAdapterListener {
    List<CommentItem> commentsList;
    NewsItem selectedNews;
    RecyclerView comments;
    //CommentItem newComment;
    CommentAdapter adp;
    private int id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        commentsList = new ArrayList<>();

        comments = findViewById(R.id.comments_list);
        selectedNews = (NewsItem)getIntent().getSerializableExtra("selectedNews");
        //commentsList = selectedNews.getComments();

        adp = new CommentAdapter(commentsList, this, this);
        comments.setLayoutManager(new LinearLayoutManager(this));
        comments.setAdapter(adp);
        id = selectedNews.getId();


        ActionBar currentActionBar = getSupportActionBar();
        currentActionBar.setHomeButtonEnabled(true);
        currentActionBar.setDisplayHomeAsUpEnabled(true);
        currentActionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_24px);
        currentActionBar.setTitle("Comments");
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadComments();
    }

    private void loadComments() {
        CommentTask tsk = new CommentTask();
        tsk.execute("http://94.138.207.51:8080/NewsApp/service/news/getcommentsbynewsid/" + id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            Intent i = new Intent();
            i.putExtra("updated_new", selectedNews);
            setResult(RESULT_OK, i);
            finish();
            return true;
        }

        if(item.getItemId() == R.id.mn_new_comment){
            Intent i = new Intent(this, AddCommentActivity.class);
            i.putExtra("id", selectedNews);
            startActivity(i);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(CommentItem selectedCommentItem) {
        //Toast.makeText(this, String.valueOf(selectedNews.getComments().size()), Toast.LENGTH_SHORT).show();;
    }

    /*
        This commentTask is for loading the comments of selected News from the main activity, from the web service.
     */

    class CommentTask extends AsyncTask<String,Void,String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(CommentsActivity.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String data = "";
            String urlStr = strings[0];
            StringBuilder strBuilder = new StringBuilder();

            try {
                URL url = new URL(urlStr);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = "";
                while ((line = reader.readLine()) != null) {
                    strBuilder.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return strBuilder.toString();

        }

        /*
        This is the form of a comment item. We can parse it easily by looking at it.
            {
             "id":1596,
             "news_id":44,
             "text":"x",
             "name":"x"
             }

         This function parses the comments from the array and adds them to Commentlist
         */

        public void parseArrayComments(JSONArray array) throws JSONException {
            int len = array.length();
            for(int i = 0; i < len; i++){
                JSONObject object = (JSONObject) array.get(i);
                int id = object.getInt("id");
                int news_id = object.getInt("news_id");
                String text = object.getString("text");
                String name = object.getString("name");

                CommentItem item = new CommentItem(id, name, text);
                commentsList.add(item);
            }
            adp.notifyDataSetChanged();
            dialog.dismiss();

        }

        @Override
        protected void onPostExecute(String s) {
            commentsList.clear();
            dialog.dismiss();
            try {
                JSONObject inputObj = new JSONObject(s);

                String message = inputObj.getString("serviceMessageText");

                if(message.equals("SUCCESS")){
                    JSONArray items = inputObj.getJSONArray("items");
                    parseArrayComments(items);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            adp.notifyDataSetChanged();
        }
    }
}
