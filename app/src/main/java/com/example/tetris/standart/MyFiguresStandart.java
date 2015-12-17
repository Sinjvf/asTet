package com.example.tetris.standart;

import android.graphics.Point;

import com.example.tetris.Const;
import com.example.tetris.MyFigures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


/**
 * Created by Sinjvf on 03.03.2015.
 */
public class MyFiguresStandart extends MyFigures{

    MyFiguresStandart(){
        super();
        x= Const.NW[Const.STANDART]/2-1;
        movingStep = Const.MOVING_STEP[Const.STANDART];
        modes=Const.MODES[Const.STANDART];
    }
    @Override
    public void move(int i, int j){
        x+=i; y+=j;
    }
    @Override
    protected  HashMap<Integer, HashSet<Point>> getHashMap(){
        return modeHashMap;
    }
    public MyFiguresStandart clone(){
        MyFiguresStandart newFig = new MyFiguresStandart();
        newFig.modeHashMap = (HashMap<Integer, HashSet<Point>>)modeHashMap.clone();
        newFig.setCurrentMode(currentMode);
        newFig.setXY(x, y);
        return newFig;
    }
    public static MyFiguresStandart newFigure(){
        MyFiguresStandart fCurrent;
        Random random;
        random = new Random(System.currentTimeMillis());
        int num = Math.abs(random.nextInt()) % 7;
        switch (num) {
            case 1:
                fCurrent = new F_L();
                break;
            case 2:
                fCurrent = new F_O();
                break;
            case 3:
                fCurrent = new F_P();
                break;
            case 4:
                fCurrent = new F_T();
                break;
            case 5:
                fCurrent = new F_Z();
                break;
            case 6:
                fCurrent = new F_Z_back();
                break;
            default:
                fCurrent = new F_I();
                break;
        }
        fCurrent.setCurrentMode(Math.abs(random.nextInt()) % modes);
        return fCurrent;
    }
}
