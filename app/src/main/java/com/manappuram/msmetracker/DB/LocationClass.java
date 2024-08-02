package com.manappuram.msmetracker.DB;

public class LocationClass {

    int _id;
    String _latitude;
    String _longitude;

    public LocationClass() {
    }

    public LocationClass(String _latitude, String _longitude) {
        this._latitude = _latitude;
        this._longitude = _longitude;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_latitude() {
        return _latitude;
    }

    public void set_latitude(String _latitude) {
        this._latitude = _latitude;
    }

    public String get_longitude() {
        return _longitude;
    }

    public void set_longitude(String _longitude) {
        this._longitude = _longitude;
    }
}
