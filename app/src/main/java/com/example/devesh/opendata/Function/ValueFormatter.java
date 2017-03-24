package com.example.devesh.opendata.Function;

import android.content.pm.InstrumentationInfo;
import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by devesh on 25/3/17.
 */

public class ValueFormatter implements IAxisValueFormatter {

    public static final String TAG = "ValueFormatter";
    ArrayList<String> mList;

    public ValueFormatter(ArrayList<String> mList) {
        this.mList = mList;
        Log.d(TAG,mList.size()+ "size");
    }


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Log.d(TAG,(int)value+"");
        String str = "IDK";
        if((int)value>mList.size())
            return str;
        else
            return mList.get((int)value);
    }


}
