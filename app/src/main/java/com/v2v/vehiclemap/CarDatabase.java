package com.v2v.vehiclemap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

import com.v2v.vehiclemap.Car;

public final class CarDatabase extends SQLiteOpenHelper {
    public static final int DB_VERS = 1;
    public static final String DB_NAME = "CarDB";

    public CarDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE cars (id TEXT PRIMARY KEY, lat REAL, lon REAL, speed REAL, bearing REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_vers, int new_vers) {
        db.execSQL("DROP TABLE IF EXISTS cars");
        this.onCreate(db);
    }

    public void seedData() {
        updateCar(new Car("first", 32.842723, -96.782781, 0, 0));
        updateCar(new Car("second", 32.842720, -96.781963, 0, 0));
        updateCar(new Car("third", 32.8428, -96.781963, 0, 0));
    }

    public void updateCar(Car c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put("id", c.id);
        vals.put("lat", c.lat);
        vals.put("lon", c.lon);
        vals.put("speed", c.speed);
        vals.put("bearing", c.bearing);

        int id = (int) db.insertWithOnConflict("cars", null, vals, SQLiteDatabase.CONFLICT_IGNORE);
        if(id == -1) db.update("cars", vals, "id=?", new String[] {c.id});
    }

    public ArrayList<Car> getAllCars() {
        ArrayList<Car> res = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cars", null);
        Car car;
        if(cursor.moveToFirst()) {
            do {
                car = new Car();
                car.id = cursor.getString(0);
                car.lat = cursor.getDouble(1);
                car.lon = cursor.getDouble(2);
                car.speed = cursor.getDouble(3);
                car.bearing = cursor.getDouble(4);
                res.add(car);
            } while(cursor.moveToNext());
        }
        return res;
    }
}
