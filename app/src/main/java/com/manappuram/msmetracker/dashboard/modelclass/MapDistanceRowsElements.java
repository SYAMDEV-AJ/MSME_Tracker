package com.manappuram.msmetracker.dashboard.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MapDistanceRowsElements {

    @SerializedName("distance")
    @Expose
    private MapDistanceRowsElementsDistance distance;
   /* @SerializedName("duration")
    @Expose
    private MapDistanceRowsElementsDuration duration;
    @SerializedName("status")
    @Expose
    private String status;*/

    public MapDistanceRowsElementsDistance getDistance() {
        return distance;
    }

    public void setDistance(MapDistanceRowsElementsDistance distance) {
        this.distance = distance;
    }

   /* public MapDistanceRowsElementsDuration getDuration() {
        return duration;
    }

    public void setDuration(MapDistanceRowsElementsDuration duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/

   /* @SerializedName("distance")
    @Expose
    private MapDistanceRowsElementsDistance distance;
    @SerializedName("duration")
    @Expose
    private MapDistanceRowsElementsDuration duration;
    @SerializedName("status")
    @Expose
    private String status;

    public MapDistanceRowsElementsDistance getDistance() {
        return distance;
    }

    public void setDistance(MapDistanceRowsElementsDistance distance) {
        this.distance = distance;
    }

    public MapDistanceRowsElementsDuration getDuration() {
        return duration;
    }

    public void setDuration(MapDistanceRowsElementsDuration duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/

}
