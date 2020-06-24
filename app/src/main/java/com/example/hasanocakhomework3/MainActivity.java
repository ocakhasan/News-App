package com.example.hasanocakhomework3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsAdapter.NewsAdapterListener {
    RecyclerView lists;
    List<NewsItem> all_news;            //This is for all the news in the main activity
    List<String> categories;               //This is for categories
    NewsAdapter adp;                        //for Main Activity
    Spinner spinner;
    List<Category> all_categories_with_id;      //This is for categories but with their id.
    ArrayAdapter<String> categoryAdapter;       //This is for spinner categories

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);

        all_categories_with_id = new ArrayList<>();
        categories = new ArrayList<>();
        categories.add("All");
        lists = findViewById(R.id.lists);
        all_news = new ArrayList<>();

        adp = new NewsAdapter(all_news, this, this);
        lists.setLayoutManager(new LinearLayoutManager(this));
        lists.setAdapter(adp);

        Log.i("Hasan", "loadNewsCalled");
        //loadNews();
        loadCategories();

        categoryAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinner.setAdapter(categoryAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories.get(position);
                if(selectedCategory.equals("All")){
                    JsonTask tsk = new JsonTask();
                    tsk.execute("http://94.138.207.51:8080/NewsApp/service/news/getall");
                }
                else{
                    getCategory(selectedCategory);
                    Log.i("Hasan","categories size" + String.valueOf(categories.size() ));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });


        getSupportActionBar().setTitle("News");
    }

    /*
        If a new is selected call the detail activity
     */
    @Override
    public void onClick(NewsItem selectedNewItem) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("selectedNewItem", selectedNewItem);
        startActivity(i);
    }

    /*
        Get Selected Category From the Categories List and Call the getByCategory id to
        get all the news in that category
     */
    public void getCategory(String category){
        SpecificCategory tsk = new SpecificCategory();
        int id = 0;
        int size = all_categories_with_id.size();
        for (int i = 0; i < size; i++){
            if(all_categories_with_id.get(i).getName().equals(category)){
                id = all_categories_with_id.get(i).getId();
                break;
            }
        }
        String url = "http://94.138.207.51:8080/NewsApp/service/news/getbycategoryid/" + id;
        tsk.execute(url);
    }

    /*
     This function load the All News
     */
    public void loadNews(){
        JsonTask tsk = new JsonTask();
        tsk.execute("http://94.138.207.51:8080/NewsApp/service/news/getall");
    }

    /*
    This function loads the all categories
     */
    public void loadCategories(){
        CategoryTask tsk = new CategoryTask();
        tsk.execute("http://94.138.207.51:8080/NewsApp/service/news/getallnewscategories");
    }

    /*
        This Json Task is for downloading all the news from the Web Service.
     */
    class JsonTask extends AsyncTask<String,Void,String> {

        ProgressDialog dialog;                      //For showing the progress
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
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
        This is the format of an new Item. We can process easily by looking at it.
                {
                 "id":48,
                 "title":"A New F.B.I. Director Prepares to Take the Reins",
                 "text":"James B. Comey is likely to spend the next year dealing with the impact of the automatic budget cuts, according to current and former senior F.B.I. officials.",
                 "date":1598475600000,
                 "image":"http://94.138.207.51:8080/NewsApp/images/news12.jpg",
                 "categoryName":"Politics"
              }
         */
        public void parseArray(JSONArray array) throws JSONException {
            int len = array.length();
            Log.i("Hasan", " Len = "+ len);
            for(int i = 0; i < len; i++){
                JSONObject object = (JSONObject) array.get(i);
                int id = object.getInt("id");
                String title = object.getString("title");
                String text = object.getString("text");
                long date = object.getLong("date");
                String image = object.getString("image");
                String category = object.getString("categoryName");

                NewsItem item = new NewsItem(id, title, text, image, new Date(date),category );
                all_news.add(item);
            }
            adp.notifyDataSetChanged();
            dialog.dismiss();
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            try {
                JSONObject inputObj = new JSONObject(s);

                String message = inputObj.getString("serviceMessageText");
                Log.i("Hasan", "-------"+ message);

                if(message.equals("SUCCESS")){

                    JSONArray items = inputObj.getJSONArray("items");
                    parseArray(items);                                      //call the parse array to get the all news
                    Log.i("Hasan", items.get(0).toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            adp.notifyDataSetChanged();


        }
    }
    /*
        This is for downloading all the categories from the Web Service.
     */

    class CategoryTask extends AsyncTask<String,Void,String> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
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
        This is the format of a category item in the Web Service. We can process more easily
            {
             "id":4,
             "name":"Economics"
            }
         */
        public void parseCategories(JSONArray array) throws JSONException {
            int len = array.length();
            for(int i = 0; i < len; i++){
                JSONObject object = (JSONObject) array.get(i);
                int id = object.getInt("id");
                String category = object.getString("name");
                Category cat = new Category(id, category);
                all_categories_with_id.add(cat);
                categories.add(category);
                Log.i("Hasan", " category " + category + " added");
            }
            categoryAdapter.notifyDataSetChanged();

        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            try {
                JSONObject inputObj = new JSONObject(s);

                String message = inputObj.getString("serviceMessageText");
                Log.i("Hasan", " achieved post execute");
                if(message.equals("SUCCESS")){
                    Log.i("Hasan", " Success ");
                    JSONArray items = inputObj.getJSONArray("items");
                    parseCategories(items);                                 //CAll the parse categories to get all the categories
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            categoryAdapter.notifyDataSetChanged();
        }
    }

    /*
        This is for getting the news about a specific Category.
        It is called when a specific category is pressed in the
        Spinner part.
     */

    class SpecificCategory extends AsyncTask<String,Void,String> {

        ProgressDialog dialog;                      //For showing the progress
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            all_news.clear();
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
        This is the format of an new Item. We can process easily by looking at it.
                {
                 "id":48,
                 "title":"A New F.B.I. Director Prepares to Take the Reins",
                 "text":"James B. Comey is likely to spend the next year dealing with the impact of the automatic budget cuts, according to current and former senior F.B.I. officials.",
                 "date":1598475600000,
                 "image":"http://94.138.207.51:8080/NewsApp/images/news12.jpg",
                 "categoryName":"Politics"
              }
         This function gets all the news from the Web Service with given category id.
         */
        public void parseCategoryArray(JSONArray array) throws JSONException {
            int len = array.length();
            for(int i = 0; i < len; i++){
                JSONObject object = (JSONObject) array.get(i);
                int id = object.getInt("id");
                String title = object.getString("title");
                String text = object.getString("text");
                long date = object.getLong("date");
                String image = object.getString("image");
                String category = object.getString("categoryName");

                NewsItem item = new NewsItem(id, title, text, image, new Date(date),category );
                all_news.add(item);
            }
            adp.notifyDataSetChanged();
            //dialog.dismiss();
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            try {
                JSONObject inputObj = new JSONObject(s);

                String message = inputObj.getString("serviceMessageText");

                if(message.equals("SUCCESS")){
                    JSONArray items = inputObj.getJSONArray("items");
                    parseCategoryArray(items);                          //call parse array to get the All News From the Given Category
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            adp.notifyDataSetChanged();
        }
    }
}
