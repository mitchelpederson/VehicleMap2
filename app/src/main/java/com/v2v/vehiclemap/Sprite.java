package com.v2v.vehiclemap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Mitchel on 3/14/2017.
 */

public class Sprite {
    private BitmapDrawable img;

        // Position in screen space
    private int x;
    private int y;
    private int height, width;

    public Sprite(Resources res, int resource) {
        img = new BitmapDrawable(res, BitmapFactory.decodeResource(res, resource));
        this.x = 0;
        this.y = 0;
        this.height = img.getBitmap().getHeight();
        this.width = img.getBitmap().getWidth();

    }

    public void setColorFilter(int color, PorterDuff.Mode pdMode) {

        img.setColorFilter(new PorterDuffColorFilter(color, pdMode));

    }

    public void setAlpha(int a) {
        img.setAlpha(a);
    }


    public void setPosition(int x, int y) {

        this.x = x;
        this.y = y;

        img.setBounds(this.x - (width / 2), this.y - (height / 2),
                      this.x + (width / 2), this.y + (height / 2));

    }

    public void scale(double factor) {
        width = (int) (width * factor);
        height = (int) (height * factor);

        img.setBounds(this.x - (width / 2), this.y - (height / 2),
                      this.x + (width / 2), this.y + (height / 2));
    }

    public void draw(Canvas canvas) {
        // If not on screen, don't draw
        //if (img.getBounds().bottom > 0 && img.getBounds().top < canvas.getHeight() &&
         //       img.getBounds().left > canvas.getWidth() && img.getBounds().right < 0) {


            img.draw(canvas);

        //}

    }

}
