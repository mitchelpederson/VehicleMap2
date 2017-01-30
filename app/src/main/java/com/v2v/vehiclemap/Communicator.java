package com.v2v.vehiclemap;

import java.util.ArrayList;

public interface Communicator {
    ArrayList<Car> getAllCars(); // Gets the location of all nearby cars
    void sendLocation(Car c); // Gives this car's location to the server/everyone around
}
