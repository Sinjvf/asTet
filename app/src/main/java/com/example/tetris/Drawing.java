package com.example.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import java.util.HashSet;

/**
 * Created by sinjvf on 20.11.15.
 */
public abstract class Drawing {
    protected int iW=0, iH=0;
    protected int height, width;
    protected int shiftx=0,  shifty=0;  //grid shifts
    protected int hShift =0; //text shift
    protected Canvas canvas;
    protected GameScreen screen;


    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
    public void setScreen(GameScreen screen) {
        this.screen = screen;
    }


    public abstract void setHW( int height, int width);


    public void drawFigure( MyFigures fig) {
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        Log.d("myLogs", "DRAW FIG");
        HashSet<Point> hashSet = fig.getFieldsWithPosition(0);
        for (Point k : hashSet) {
            if (k.y>=0)
                drawField(p, k.x, k.y);
        }
    }
    public void drawNextFigure( MyFigures fig){
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        Log.d("myLogs", "DRAW NEXT FIG");
        HashSet<Point> hashSet = fig.getFieldsWithPosition(0);
        for (Point k : hashSet) {
            drawFieldForNextFugure(p, k.x, k.y);
        }
    }
    public abstract void drawFullScreen() ;
    public abstract void drawGrid(Integer score, int level);
    protected abstract void drawField(Paint p, int i, int j);
    protected abstract void drawFieldForNextFugure(Paint p, int i, int j);
    public void drawBackgroung(){
        /** fill field by color*/

        canvas.drawARGB(250, 102, 204, 255);

        Paint p=new Paint();
        p.setColor(Color.argb(100, 200, 200, 0));
        p.setStyle(Paint.Style.FILL);
        for (int i=0;i<screen.getnI();i++){
            drawField( p, i, 1);
        }
    }


    protected void drawMyText(Integer score, int level,  int wShift, int hShift, int step){
        Paint p=new Paint();
        p.setColor(Color.BLACK);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(30);
        canvas.drawText("Next:", wShift, hShift, p);
        canvas.drawText("Level:", wShift, hShift+step*3, p);
        canvas.drawText(""+level, wShift, hShift+step*4, p);
        canvas.drawText("score:", wShift, hShift + step*5, p);
        canvas.drawText(""+score, wShift, hShift+step*6, p);
    }

}
