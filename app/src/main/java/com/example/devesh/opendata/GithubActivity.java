package com.example.devesh.opendata;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.Map;

import static com.example.devesh.opendata.R.id.auth;

public class GithubActivity extends AppCompatActivity {

    public static String URL = "";
    public static final String TOKEN = "fa4db3bee40c22253bf1531a963823632a0f12fc";
    public static final String WEBSITE = " https://api.github.com";
    Button addUser;
    EditText userName;
    TextView displayData;
    ListView listView;
    ImageView imageView;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

        init();

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user= userName.getText().toString().trim();
                requestQueue.add(createRequest1(user));
                requestQueue.add(createRequest2(user));

            }
        });
    }

    public ImageRequest createRequest3(){
        ImageRequest imageRequest = new ImageRequest(URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, 1000, 1000, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        return imageRequest;
    }

    public StringRequest createRequest2(String user){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, WEBSITE + "/users/" +
                user + "/repos", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    HashMap<String,Integer> hashMap = new HashMap<String, Integer>();
                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        String lang = jsonObject.getString("language");
                        if(hashMap.containsKey(lang)){
                            Integer oldValue = hashMap.get(lang);
                            hashMap.remove(lang);
                            hashMap.put(lang,oldValue+1);
                        }
                        else{
                            hashMap.put(lang,1);
                        }
                    }
                    String display = "";
                    Set<String> allLanguages = hashMap.keySet();
                    Iterator<String> lang = allLanguages.iterator();
                    while (lang.hasNext()){
                        String addLang = lang.next();
                        Integer value = hashMap.get(addLang);
                        display = display + addLang + " " + String.valueOf(value) + "\n";
                    }
                    displayData.setText(display);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<String, String>();
                stringMap.put("Authorization","token "+TOKEN);
                return stringMap;
            }
        };
        return  stringRequest;
    }


    public StringRequest createRequest1(String user){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, WEBSITE+"/users/"
                +user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String urlString = jsonObject.getString("avatar_url");
                    URL = urlString;
                    requestQueue.add(createRequest3());
                    //webView.loadUrl(urlString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //String myString = response.toString();
                //displayData.setText(myString);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("Authorization","token "+TOKEN);
                return  hashMap;
            }
        };
        return stringRequest;
    }

    private void init(){
        addUser = (Button) findViewById(R.id.userAdd);
        userName = (EditText) findViewById(auth);
        //listView = (ListView) findViewById(R.id.listView);
        displayData = (TextView) findViewById(R.id.displayData);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // webView = (WebView) findViewById(R.id.webView);
        imageView = (ImageView) findViewById(R.id.imageView);
    }
}
