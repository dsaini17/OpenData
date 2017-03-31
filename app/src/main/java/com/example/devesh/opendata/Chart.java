package com.example.devesh.opendata;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class Chart extends AppCompatActivity {

    PieChart myPieChart;
    EditText data1,data2,data3,val1,val2,val3;
    Button plotChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        init();

        plotChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a,b,c;
                Integer d,e,f;

                a = data1.getText().toString().trim();
                b = data2.getText().toString().trim();
                c = data3.getText().toString().trim();

                d = Integer.valueOf(val1.getText().toString().trim());
                e = Integer.valueOf(val2.getText().toString().trim());
                f = Integer.valueOf(val3.getText().toString().trim());

                Float f1 = Float.valueOf((100*d)/(d+e+f)),f2 = Float.valueOf((100*e)/(d+e+f)), f3 = 100-f1-f2 ;

                List<PieEntry> myEntryList = new ArrayList<PieEntry>();
                myEntryList.add(new PieEntry(f1,a));
                myEntryList.add(new PieEntry(f2,b));
                myEntryList.add(new PieEntry(f3,c));

                List<Integer> colorList = new ArrayList<Integer>();
                colorList.add(Color.RED);
                colorList.add(Color.BLUE);
                colorList.add(Color.GREEN);

                PieDataSet pieDataSet = new PieDataSet(myEntryList,"Market Share");
                pieDataSet.setColors(colorList);
                PieData pieData = new PieData(pieDataSet);
                myPieChart.setData(pieData);
                myPieChart.setBackgroundColor(Color.YELLOW);
                myPieChart.setCenterText("PIE CHART");
                myPieChart.invalidate();


            }
        });

    }

    public void init(){
        myPieChart = (PieChart) findViewById(R.id.pieChart);
        data1 = (EditText) findViewById(R.id.oneString);
        data2 = (EditText) findViewById(R.id.twoString);
        data3 = (EditText) findViewById(R.id.threeString);
        val1 = (EditText) findViewById(R.id.oneInteger);
        val2 = (EditText) findViewById(R.id.twoInteger);
        val3 = (EditText) findViewById(R.id.threeInteger);
        plotChart = (Button) findViewById(R.id.plotButton);
    }
}
