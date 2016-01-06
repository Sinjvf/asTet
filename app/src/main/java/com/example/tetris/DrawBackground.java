package com.example.tetris;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import java.util.Arrays;

/**
 * Created by sinjvf on 21.12.15.
 */
public class DrawBackground extends View {
    private Bitmap bitmap;
    private int height, width;

    public DrawBackground(Context context, int fileID){
        super(context);
        if (fileID!=0) {
            bitmap = BitmapFactory.decodeResource(getResources(), fileID);
        }
    }

    public void setSize(int w, int h){
        height = h;
        width = w;
        bitmap = Bitmap.createScaledBitmap(bitmap,  width,
                height, false);
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    protected void onDraw(Canvas canvas) {
    }

}
