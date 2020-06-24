package com.example.hasanocakhomework3;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AddCommentActivity extends AppCompatActivity {

    EditText txtName;
    EditText txtBody;
    Button btnComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        txtName = findViewById(R.id.comment_name);
        txtBody = findViewById(R.id.comment_text);
        btnComment = findViewById(R.id.btnComment);

        final NewsItem selectedNew = (NewsItem) getIntent().getSerializableExtra("id");

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCommentTask tsk = new AddCommentTask();
                tsk.execute("http://94.138.207.51:8080/NewsApp/service/news/savecomment", String.valueOf(selectedNew.getId()), txtName.getText().toString(), txtBody.getText().toString());
                finish();

            }
        });

        ActionBar currentActionBar = getSupportActionBar();
        currentActionBar.setHomeButtonEnabled(true);
        currentActionBar.setDisplayHomeAsUpEnabled(true);
        currentActionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_24px);

        currentActionBar.setTitle("Post Comment");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }




    /*
        This Task is for posting a comment to web service
     */
    class AddCommentTask extends AsyncTask<String,Void,String> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(AddCommentActivity.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String data = "";
            String urlStr = strings[0];
            String id = strings[1];
            String name = strings[2];
            String body = strings[3];

            JSONObject obj = new JSONObject();
            try {

                obj.put("name",name);
                obj.put("text",body);
                obj.put("news_id",id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringBuilder strBuilder = new StringBuilder();

            try {
                URL url = new URL(urlStr);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/json");
                conn.connect();
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(obj.toString());

                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line ="";

                    while((line = reader.readLine())!=null){
                        strBuilder.append(line);
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return strBuilder.toString();

        }


        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            try {
                JSONObject inputObj = new JSONObject(s);

                String message = inputObj.getString("serviceMessageText");

                return;

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}
