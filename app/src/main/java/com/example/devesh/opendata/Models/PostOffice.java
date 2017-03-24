package com.example.devesh.opendata.Models;

/**
 * Created by devesh on 24/3/17.
 */

public class PostOffice {
    String officeName;
    String districtName;
    String stateName;
    String pincode;

    public PostOffice(String officeName, String districtName, String stateName, String pincode) {
        this.officeName = officeName;
        this.districtName = districtName;
        this.stateName = stateName;
        this.pincode = pincode;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
