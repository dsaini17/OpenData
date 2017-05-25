package com.example.devesh.opendata;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.devesh.opendata.Models.Hospital;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;

public class Map extends AppCompatActivity implements OnMapReadyCallback{

    public static final String TAG = "MapActivity";
    Button fetchData;
    ArrayList<Hospital> coordinateDataList;
    GoogleMap map;
    SupportMapFragment supportMapFragment;
    RequestQueue requestQueue;
    public static final String data_URL = "https://data.gov.in/api/datastore/resource.json?resource_id=37670b6f-c236-49a7-8cd7-cc2dc610e32d&api-key=00fe157bc3c6a164568b1fc84c5766b0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        coordinateDataList = new ArrayList<>();
        fetchData = (Button) findViewById(R.id.fetchData);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        fetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Button Pressed");
                requestQueue.add(generateStringRequest());
            }
        });

    }

    public StringRequest generateStringRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, data_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG,"Response Received "+response.substring(0,200));
                    generateList(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,error.networkResponse.statusCode+"");
            }
        });
        return stringRequest;
    }

    public void generateList(String resp) throws JSONException{
        JSONObject jsonObject = new JSONObject(resp);
        JSONArray jsonArray = jsonObject.getJSONArray("records");
        int len = jsonArray.length();

        for(int i=0;i<len;i++){
            JSONObject newObject = jsonArray.getJSONObject(i);
            String hospital = newObject.getString("Hospital_Name");
            String location = newObject.getString("Location_Coordinates");
            if(!location.matches("NA")) {
                String a = "", b = "";
                int size = location.length();
                boolean val = false;
                for (int j = 0; j < size; j++) {
                    char[] alpha = new char[4];
                    location.getChars(j, j + 1, alpha, 0);
                    if (alpha[0] == ',')
                        val = true;
                    else {
                        if (val == false) {
                            a += alpha[0];
                        } else {
                            b += alpha[0];
                        }
                    }
                }
                Hospital hospitalObject = new Hospital(hospital, Float.valueOf(a), Float.valueOf(b));
               // Log.d(TAG, hospitalObject.getxCoordinate()+" "+hospitalObject.getyCoordinate());
                coordinateDataList.add(hospitalObject);
            }

        }
//        mapView.getMapAsync(this);
//        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                float x = (float) latLng.latitude , y = (float) latLng.longitude;
                int ind=-1;
                float minDis=10000000;
                int len = coordinateDataList.size();
                for(int i=0;i<len;i++){
                    Hospital hospital = coordinateDataList.get(i);
                    float localDis = (float) Math.sqrt((double)(((hospital.getxCoordinate())-x)*(hospital.getxCoordinate())-x)+(hospital.getyCoordinate()-y)*(hospital.getyCoordinate()-y));
                    if(localDis<minDis){
                        minDis=localDis;
                        ind=i;
                    }
                }
                LatLng latLng1 = new LatLng((double)coordinateDataList.get(ind).getxCoordinate(),(double)coordinateDataList.get(ind).getyCoordinate());
                Log.v(TAG,latLng+" "+latLng1);
                googleMap.addMarker(new MarkerOptions().position(latLng).title("You are here"));
                googleMap.addMarker(new MarkerOptions().position(latLng1).title("Nearest Hospital").snippet(coordinateDataList.get(ind).getHospitalName()));
            }
        });


      /*    Data from API

      int length = coordinateDataList.size();
        for(int i=0;i<length;i++){

            Hospital hospital = coordinateDataList.get(i);

    *//*
           Static Marker
           Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(37.7750, 122.4183))
                    .title("San Francisco")
                    .snippet("Population: 776733"));
*//*
            googleMap.addMarker(new MarkerOptions().position(new LatLng(hospital.getxCoordinate(),hospital.getyCoordinate())).title(hospital.getHospitalName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round)));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(hospital.getxCoordinate(),-hospital.getyCoordinate()),14));
        }*/
    }


}
