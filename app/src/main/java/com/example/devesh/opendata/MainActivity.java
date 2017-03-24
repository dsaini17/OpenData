package com.example.devesh.opendata;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.devesh.opendata.Models.PostOffice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    public static final String API_KEY = "00fe157bc3c6a164568b1fc84c5766b0";
    public static final String URL = "https://data.gov.in/api/datastore/resource.json?resource_id=6176ee09-3d56-4a3b-8115-21841576b2f6";
    public static final String TAG = "MainActivity";
    Button get_Button,graph_Button;
    EditText pincode_EditText;
    TextView officename_TextView , districtname_TextView , statename_TextView ;
    public static final String hit_thisURL = "https://data.gov.in/api/datastore/resource.json?resource_id=6176ee09-3d56-4a3b-8115-21841576b2f6&api-key=00fe157bc3c6a164568b1fc84c5766b0";
    ArrayList <PostOffice> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        get_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEvent();
            }
        });
        graph_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),Graph.class));
            }
        });
    }

    public void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        get_Button = (Button) findViewById(R.id.getData_Button);
        pincode_EditText = (EditText) findViewById(R.id.pincode_EditText);
        officename_TextView = (TextView) findViewById(R.id.officename_TextView);
        districtname_TextView = (TextView) findViewById(R.id.districtname_TextView);
        graph_Button = (Button) findViewById(R.id.graphActivity);
        statename_TextView = (TextView) findViewById(R.id.statename_TextView);
        dataList = new ArrayList<>();
    }

    public void onClickEvent(){
        int pinCode = Integer.parseInt(pincode_EditText.getText().toString().trim());
        Log.d(TAG,""+pinCode);
        if(dataList.size()==0)
            requestQueue.add(stringRequestBuilder(hit_thisURL,pinCode));
        else
            searchAndDisplay(pinCode);
        pincode_EditText.setText("");
    }

    public Request stringRequestBuilder(String myURL, final int pinCode){

        StringRequest myStringRequest = new StringRequest(Request.Method.GET, myURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    createList(response);
                    searchAndDisplay(pinCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, String.valueOf(error.getCause()));
                Log.d(TAG,error.getMessage());
                Log.d(TAG, String.valueOf(error.networkResponse.statusCode));
            }
        });
        return myStringRequest;
    }

    public void createList(String response)throws JSONException{
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("records");
        int length = jsonArray.length();
        for(int i=0;i<length;i++){
            JSONObject newObject = new JSONObject(String.valueOf(jsonArray.getJSONObject(i)));
            PostOffice postOffice = new PostOffice(newObject.getString("officename"),newObject.getString("Districtname"),newObject.getString("statename"),newObject.getString("pincode"));
            dataList.add(postOffice);
        }
        return;
    }

    public void searchAndDisplay(int pinCode){
        String searchPIN = String.valueOf(pinCode);
        int length = dataList.size(),index=-1;
        for(int i=0;i<length;i++){
            if(searchPIN.matches(dataList.get(i).getPincode())){
                index=i;
                break;
            }
        }
        if(index==-1){
            Toast.makeText(getApplicationContext(),"Invalid Pin Code",Toast.LENGTH_SHORT).show();
        }
        else{
            PostOffice postOffice = dataList.get(index);
            officename_TextView.setText(postOffice.getOfficeName());
            districtname_TextView.setText(postOffice.getDistrictName());
            statename_TextView.setText(postOffice.getStateName());
        }
    }
}
