package com.v2v.vehiclemap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Mitchel on 3/14/2017.
 */

public class Sprite {
    private BitmapDrawable img;

        // Position in screen space
    private int x;
    private int y;
    private double alpha;

    public Sprite(Resources res, int resource) {
        img = new BitmapDrawable(res, BitmapFactory.decodeResource(res, resource));
    }

    public void setPosition(int x, int y) {

        this.x = x;
        this.y = y;

        img.setBounds(this.x - (img.getBitmap().getWidth() / 2), this.y - (img.getBitmap().getHeight() / 2),
                      this.x + (img.getBitmap().getWidth() / 2), this.y + (img.getBitmap().getHeight() / 2));

    }

    public void draw(Canvas canvas) {

        img.draw(canvas);

    }

}
