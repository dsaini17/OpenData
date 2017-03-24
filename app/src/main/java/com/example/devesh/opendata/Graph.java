package com.example.devesh.opendata;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.devesh.opendata.Function.ValueFormatter;
import com.example.devesh.opendata.Models.Temperature;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ColorFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Graph extends AppCompatActivity {

    Spinner spinner;
    public static final String TAG= "GraphActivity";
    String yearTemp = "";
    Button getData;
    EditText yearEditText;
    BarChart barChart;
    ArrayList <Temperature> tempDataList;
    RequestQueue requestQueue;
    int curr_month = 1;
    public static final String hit_URL = "https://data.gov.in/api/datastore/resource.json?resource_id=acf1e293-e721-47d4-a1df-5505dd3927ca&api-key=00fe157bc3c6a164568b1fc84c5766b0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        barChart =(BarChart) findViewById(R.id.barGraph) ;
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        //spinner = (Spinner) findViewById(R.id.spinnerMonth);
        getData = (Button) findViewById(R.id.showData);
        yearEditText = (EditText) findViewById(R.id.yearEditText);
        tempDataList = new ArrayList<>();

      /*  ArrayAdapter <CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.monthselect,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 : curr_month =1 ;
                        break;
                    case 1 : curr_month = 2 ;
                        break;
                    case 2 : curr_month = 3;
                        break;
                    case 3 : curr_month = 4;
                        break;
                    default: curr_month =1 ;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearTemp = yearEditText.getText().toString().trim();
                if(tempDataList.size()==0)
                    requestQueue.add(makeRequest());
                else
                    buildGraph();
            }
        });

    }

    public Request makeRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, hit_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.v(TAG,response);
                    createDataList(response);
                    buildGraph();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, String.valueOf(error.networkResponse.statusCode));
                Log.d(TAG,error.getMessage());
            }
        });
        return stringRequest;
    }

    public void createDataList(String response) throws JSONException{
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("records");
        int length = jsonArray.length();
        for(int i=0;i<length;i++){
            JSONObject myObject = jsonArray.getJSONObject(i);
            float a = Float.valueOf(myObject.getString("JANFEB"));
            float b = Float.valueOf(myObject.getString("MARMAY"));
            float c= Float.valueOf(myObject.getString("JUNSEP"));
            float d = Float.valueOf(myObject.getString("OCTDEC"));
            int year = Integer.valueOf(myObject.getString("YEAR"));
            Temperature temperature = new Temperature(year,a,b,c,d);
            tempDataList.add(temperature);
        }
    }

    public void buildGraph(){
        int length = tempDataList.size(),ind=-1,curr_Year = Integer.valueOf(yearTemp);
        for(int i=0;i<length;i++){
            if(tempDataList.get(i).getYear() == curr_Year ){
                ind = i;
                break;
            }
        }
        if(ind==-1){
            Toast.makeText(getApplicationContext(),"DATA NOT AVAILABLE",Toast.LENGTH_SHORT).show();
        }
        else{


            ArrayList<BarEntry> yAxis = new ArrayList<>();
            Temperature temp = tempDataList.get(ind);

            Log.d(TAG,temp.getJan_feb()+" "+temp.getJun_sep()+" "+temp.getMar_may()+" "+temp.getOct_dec());

            yAxis.add(new BarEntry(0f,temp.getJan_feb()));
            yAxis.add(new BarEntry(2f,temp.getMar_may()));
            yAxis.add(new BarEntry(4f,temp.getJun_sep()));
            yAxis.add(new BarEntry(6f,temp.getOct_dec()));

            BarDataSet barDataSet = new BarDataSet(yAxis,"Temperature");
            barDataSet.setBarBorderColor(Color.BLACK);
            barDataSet.setBarBorderWidth(0.1f);

            ArrayList<String> xAxisElements = new ArrayList<>();
            xAxisElements.add("JAN-FEB");
            xAxisElements.add("MAR-MAY");
            xAxisElements.add("JUN-SEP");
            xAxisElements.add("OCT-DEC");

            final String[] quarters = new String[] { "JAN-FEB", "MAR-MAY", "JUN-SEP", "OCT-DEC" };
            XAxis xAxis = barChart.getXAxis();
            xAxis.setTextSize(10);
            xAxis.setTextColor(Color.RED);
            xAxis.setValueFormatter(new ValueFormatter(xAxisElements));


            BarData barData = new BarData(barDataSet);
            barChart.setData(barData);
            barChart.setFitBars(true);

            barChart.setTouchEnabled(true);
            //barChart.setPinchZoom(true);

            barChart.setDragEnabled(true);
            //barChart.setScaleEnabled(true);
            barChart.invalidate();
        }
    }
}
