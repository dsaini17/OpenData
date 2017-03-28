package com.example.devesh.opendata.Models;

/**
 * Created by devesh on 25/3/17.
 */

public class Hospital {
    String hospitalName;
    float xCoordinate;
    float yCoordinate;


    public Hospital(String hospitalName, float xCoordinate, float yCoordinate) {
        this.hospitalName = hospitalName;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public float getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public float getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
