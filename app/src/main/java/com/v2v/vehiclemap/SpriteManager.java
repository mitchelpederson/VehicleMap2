package com.v2v.vehiclemap;

import android.content.res.Resources;
import android.graphics.Canvas;

import java.util.HashMap;

/**
 * Created by Mitchel on 3/14/2017.
 */

public class SpriteManager {

    private HashMap<String, Sprite> sprites;

    public SpriteManager() {
        sprites = new HashMap<String, Sprite>();
    }

    public void addSprite(Resources res, String id, int imageResource) {

        sprites.put(id, new Sprite(res, imageResource));

    }

    public void addSprite(String id, Sprite s) {
        sprites.put(id, s);
    }

    public void removeSprite(String id) {
        sprites.remove(id);
    }

    public Sprite getSprite(String id) {
        return sprites.get(id);
    }

    public void drawAll(Canvas canvas) {
        for (Sprite sprite : sprites.values()) {
            sprite.draw(canvas);
        }
    }
}
