package com.manappuram.msmetracker.dashboard.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MapDistanceRows {

    @SerializedName("elements")
    @Expose
    private List<MapDistanceRowsElements> elements = null;

    public List<MapDistanceRowsElements> getElements() {
        return elements;
    }

    public void setElements(List<MapDistanceRowsElements> elements) {
        this.elements = elements;
    }


   /* @SerializedName("elements")
    @Expose
    private List<MapDistanceRowsElements> elements;

    public List<MapDistanceRowsElements> getElements() {
        return elements;
    }

    public void setElements(List<MapDistanceRowsElements> elements) {
        this.elements = elements;
    }

    */



}
