package com.v2v.vehiclemap;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Mitchel on 3/14/2017.
 */

public class SpriteManager {

    private static ConcurrentHashMap<String, Sprite> sprites;
    private static SpriteManager instance;

    public SpriteManager() {
        sprites = new ConcurrentHashMap<String, Sprite>();
        instance = this;


    }

    public static SpriteManager getInstance() {
        return instance;
    }

    public static void addSprite(Resources res, String id, int imageResource) {

        sprites.put(id, new Sprite(res, imageResource));
        Log.d("SpriteManager", "Added new sprite, id: " + id);

    }

    public static void addSprite(String id, Sprite s) {
        sprites.put(id, s);
    }

    public static void removeSprite(String id) {
        sprites.remove(id);
    }

    public static boolean contains(String id) {
        if (sprites.containsKey(id)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static Set<String> getCarList() {
        return sprites.keySet();
    }

    public static Sprite getSprite(String id) {
        return sprites.get(id);
    }

    public static void drawAll(Canvas canvas) {
        //Log.d("SpriteManager", "called drawAll()");
        //Log.d("SpriteManager", "Drawing " + sprites.size() + " sprites.");

        for (Sprite sprite : sprites.values()) {
            sprite.draw(canvas);
        }

    }
}
