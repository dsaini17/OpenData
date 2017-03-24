package com.example.devesh.opendata.Models;

/**
 * Created by devesh on 25/3/17.
 */

public class Temperature {

    int year;
    float jan_feb;
    float mar_may;
    float jun_sep;
    float oct_dec;


    public Temperature(int year, float jan_feb, float mar_may, float jun_sep, float oct_dec) {
        this.year = year;
        this.jan_feb = jan_feb;
        this.mar_may = mar_may;
        this.jun_sep = jun_sep;
        this.oct_dec = oct_dec;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getJan_feb() {
        return jan_feb;
    }

    public void setJan_feb(float jan_feb) {
        this.jan_feb = jan_feb;
    }

    public float getMar_may() {
        return mar_may;
    }

    public void setMar_may(float mar_may) {
        this.mar_may = mar_may;
    }

    public float getJun_sep() {
        return jun_sep;
    }

    public void setJun_sep(float jun_sep) {
        this.jun_sep = jun_sep;
    }

    public float getOct_dec() {
        return oct_dec;
    }

    public void setOct_dec(float oct_dec) {
        this.oct_dec = oct_dec;
    }
}
