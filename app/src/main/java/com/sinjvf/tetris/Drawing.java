package com.sinjvf.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.HashSet;
import java.util.Random;

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
    protected  int fileID;


    public Drawing(Context context, int fileID){
        this.context = context;
        pCore =new Paint();
        this.fileID = fileID;
        if (fileID!=0) {
            Log.d(Const.LOG_TAG, "load pict");
            back = new DrawBackground(context, fileID);
        }

    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
    public void setScreen(GameScreen screen) {
        this.screen = screen;
    }


    public abstract void setHW( int height, int width);


    public void drawFigure( MyFigures fig) {
        int colorCore, colorEdge;
        int colorSheme;
        Random random;
        random = new Random(System.currentTimeMillis());
        HashSet<Point> hashSet = fig.getFieldsWithPosition(0);

        colorSheme = fig.getColorSheme();
        if (colorSheme==Const.COLOR_FIGURES_CORE.length){
            colorSheme= Math.abs(random.nextInt()%Const.COLOR_FIGURES_CORE.length);
        }
        if(colorSheme<0)
            colorSheme=0;
        colorCore = Const.COLOR_FIGURES_CORE[colorSheme];
        colorEdge = Const.COLOR_FIGURES_EDGE[colorSheme];
        for (Point k : hashSet) {
            if (k.y>=0) {
                drawField(colorCore,colorEdge,  k.x, k.y);
            }
        }
    }
    public void drawNextFigure( MyFigures nextFig){
        MyFigures fig = nextFig.clone();
        fig.y = fig.primaryY;
        HashSet<Point> hashSet = fig.getFieldsWithPosition(0);
        for (Point k : hashSet) {
            drawFieldForNextFugure(Const.COLOR_CORE_RED,Const.COLOR_EDGE_RED,  k.x, k.y);
        }
    }
    public abstract void drawFullScreen() ;
    public abstract void drawGrid(Integer score, int level);
    protected abstract void drawField(int colCore, int colEdg , int i, int j);
    protected abstract void drawFieldForNextFugure(int colCore, int colEdg,  int i, int j);
    protected abstract void drawFieldArrow(int i, int j, int color);
    protected abstract void drawArrows();
    public void drawBackgroung(){
        /** fill field by color*/
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawColor(ContextCompat.getColor(context, R.color.background));//250, 102, 204, 255);

        if (fileID!=0) {
            canvas.drawBitmap(back.getBitmap(), 0, 0, paint);
        }
        /**/
        for (int i=0;i<screen.getnI();i++){
            drawField( Const.COLOR_FIRST_LINE_CORE, Const.COLOR_FIRST_LINE_EDGE, i, 1);
        }
        drawArrows();
/**/
    }
    /**
    protected void drawMyText(Integer score, int level,  int wShift, int hShift, int step){
        Paint p=new Paint();
        p.setColor(Color.BLACK);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(30);

        canvas.drawText(Const.Next, wShift, hShift, p);
        canvas.drawText(Const.Level, wShift, hShift+step*3.5f, p);
        canvas.drawText(""+level, wShift, hShift+step*4.5f, p);
        canvas.drawText(Const.Score, wShift, hShift + step*5.5f, p);
        canvas.drawText(""+score, wShift, hShift+step*6.5f, p);
    }
    **/

}
