package com.v2v.vehiclemap;

public final class Car {
    public String id;
    public double lat;
    public double lon;
    public double speed;
    public double bearing;

    public Car() {
        this.id = "";
        this.lat = 0;
        this.lon = 0;
        this.speed = 0;
        this.bearing = 0;
    }

    public Car(String id, double l1, double l2, double s, double b) {
        this.id = id;
        this.lat = l1;
        this.lon = l2;
        this.speed = s;
        this.bearing = b;
    }
}
