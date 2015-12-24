package com.example.tetris;

import android.content.Context;
import android.content.SharedPreferences;
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
    protected Paint pCore;
    protected DrawBackground back;
    private Context context;

    public Drawing(Context context){
        this.context = context;
        pCore =new Paint();



    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
    public void setScreen(GameScreen screen) {
        this.screen = screen;
    }


    public abstract void setHW( int height, int width);


    public void drawFigure( MyFigures fig) {
        Log.d("myLogs", "DRAW FIG");
        HashSet<Point> hashSet = fig.getFieldsWithPosition(0);
        for (Point k : hashSet) {
            if (k.y>=0) {
                drawField(Const.COLOR_CORE,Const.COLOR_EDGE,  k.x, k.y);
            }
        }
    }
    public void drawNextFigure( MyFigures nextFig){
        MyFigures fig = nextFig.clone();
        fig.y = fig.primaryY;
        Log.d("myLogs", "DRAW NEXT FIG:  x="+fig.x + ", y=" + fig.y);
        HashSet<Point> hashSet = fig.getFieldsWithPosition(0);
        for (Point k : hashSet) {
            drawFieldForNextFugure(Const.COLOR_CORE,Const.COLOR_EDGE,  k.x, k.y);
        }
    }
    public abstract void drawFullScreen() ;
    public abstract void drawGrid(Integer score, int level);
    protected abstract void drawField(int colCore, int colEdg , int i, int j);
    protected abstract void drawFieldForNextFugure(int colCore, int colEdg,  int i, int j);
    public void drawBackgroung(int fileID){
        /** fill field by color*/
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawARGB(250, 102, 204, 255);

        if (fileID!=0) {
            back = new DrawBackground(context, fileID);
            back.setSize(width, height);
            canvas.drawBitmap(back.getBitmap(), 0, 0, paint);
        }
        for (int i=0;i<screen.getnI();i++){
            drawField( Const.COLOR_FIRST_LINE_CORE, Const.COLOR_FIRST_LINE_EDGE, i, 1);
        }


    }


    protected void drawMyText(Integer score, int level,  int wShift, int hShift, int step){
        Paint p=new Paint();
        p.setColor(Color.BLACK);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(30);
        canvas.drawText("Next:", wShift, hShift, p);
        canvas.drawText("Level:", wShift, hShift+step*3.5f, p);
        canvas.drawText(""+level, wShift, hShift+step*4.5f, p);
        canvas.drawText("score:", wShift, hShift + step*5.5f, p);
        canvas.drawText(""+score, wShift, hShift+step*6.5f, p);
    }

}
