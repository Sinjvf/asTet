package com.example.tetris;

import android.graphics.Point;
import java.util.HashMap;
import java.util.HashSet;


/**
 * Created by Sinjvf on 03.03.2015.
 */
public abstract class MyFigures {
    protected HashMap<Integer, HashSet<Point>>modeHashMap ;
    protected int currentMode;
    protected int x, y;
    protected static int movingStep;
    protected static int modes;
    protected static int primaryY;
    protected int colorSheme;

    public MyFigures(){
        modeHashMap = new HashMap<Integer,  HashSet<Point > >();
        colorSheme = 0;
    }
    public void setMischievous(int colorSheme){
        this.colorSheme = colorSheme;
    }
    public int getColorSheme(){
        return colorSheme;
    }
    public int getModes(){return modes;}
    public int getCurrentMode(){
        return currentMode;
    }
    public int getMovingStep() {
        return movingStep;
    }
    public abstract MyFigures clone();
    public void setXY(int i, int j){
        x = i;   y = j;
    }
    public void setCurrentMode(int mode){
        currentMode = mode;
    }

    /**get fields with position and new mode without rotation*/
    public  HashSet<Point> getFieldsWithPosition(int shift_mode) {
        HashSet<Point> field_old, field_new;
        field_old = getHashMap().get((currentMode+shift_mode+modes)%modes);
        field_new = new HashSet<Point>();
        for (Point k: field_old) {
            field_new.add(new Point(k.x + x, k.y + y));
        }
        return field_new;
    }
    public abstract void move(int i, int j);
    protected abstract HashMap<Integer, HashSet<Point>> getHashMap();
    public static MyFigures newFigure(){return null;}
}
