package com.v2v.vehiclemap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by Mitchel on 2/1/2017.
 */

public class OverlaySurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    private SurfaceHolder surfaceHolder;
    private Bitmap bmp;
    private Thread thread;
    private boolean running;
    private Canvas canvas;

    private Random random;

    int bmpX;
    int bmpY;

    public OverlaySurface(Context context) {
        super(context);
        init();
    }

    public OverlaySurface(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }


    private void init() {

        thread = null;
        running = false;
        canvas = null;
        random = new Random();

        surfaceHolder = this.getHolder();
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.dot);

        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setZOrderOnTop(true);

        bmpX = 0;
        bmpY = getHeight()/2;

        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        running = true;
        thread = new Thread(this);
        thread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        running = false;
        while(retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void run() {
        while (running) if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas(null);

            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            for (int i = 0; i < 200; i++) {
                canvas.drawBitmap(bmp, random.nextInt(getWidth()), random.nextInt(getHeight()), null);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

}
