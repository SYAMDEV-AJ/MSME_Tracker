package com.manappuram.msmetracker.dashboard.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MapDistanceResponse {

    @SerializedName("destination_addresses")
    @Expose
    private List<String> destinationAddresses = null;
    @SerializedName("origin_addresses")
    @Expose
    private List<String> originAddresses = null;
    @SerializedName("rows")
    @Expose
    private List<MapDistanceRows> rows = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public List<String> getOriginAddresses() {
        return originAddresses;
    }

    public void setOriginAddresses(List<String> originAddresses) {
        this.originAddresses = originAddresses;
    }

    public List<MapDistanceRows> getRows() {
        return rows;
    }

    public void setRows(List<MapDistanceRows> rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



  /*  @SerializedName("rows")
    @Expose
    private List<MapDistanceRows>  rows;

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MapDistanceRows> getRows() {
        return rows;
    }

    public void setRows(List<MapDistanceRows> rows) {
        this.rows = rows;
    }


*/





}
