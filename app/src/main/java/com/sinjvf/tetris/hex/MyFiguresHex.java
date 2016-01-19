package com.sinjvf.tetris.hex;

import android.graphics.Point;
import android.util.Log;

import com.sinjvf.tetris.Const;
import com.sinjvf.tetris.MyFigures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


/**
 * Created by Sinjvf on 03.03.2015.
 */
public class MyFiguresHex extends MyFigures{


    MyFiguresHex(){
        super();
        primaryY = Const.PRIMARY_Y[Const.HEX];
        x=Const.NW[Const.HEX];
        y=primaryY;
        movingStep = Const.MOVING_STEP[Const.HEX];
        modes=Const.MODES[Const.HEX];
    }


    protected void setOddHashMap(){
        HashSet<Point> hs1, hs;
        for (int k=0;k<modes;k++){
            hs1=new HashSet<Point>();
            hs = modeHashMap.get(k);
            for (Point hsK: hs)
            {
                if (hsK.x%2==0){
                    hs1.add(new Point(hsK.x, hsK.y));
                }
                else{
                    hs1.add(new Point(hsK.x, hsK.y+1));
                }
            }
            modeHashMap.put(k+modes, hs1);
        }
    }


    @Override
    protected  HashMap<Integer, HashSet<Point>> getHashMap() {
        if (x % 2 == 0) {
            return modeHashMap;
        } else {
            HashSet<Point> hs;
            HashMap<Integer, HashSet<Point>> modeOddHashMap = new HashMap<Integer, HashSet<Point>>();
            for (int k = 0; k < modes; k++) {
                hs = modeHashMap.get(k + modes);
                modeOddHashMap.put(k, hs);
            }
            return modeOddHashMap;
        }
    }


    public void move(int i, int j){
            if (x%2!=0 & i!=0){
                y=y+1;
            }
        x=x+i;
        y=y+j* movingStep;

    }

    public MyFiguresHex clone(){
        MyFiguresHex newFig = new MyFiguresHex();
        newFig.modeHashMap = (HashMap<Integer, HashSet<Point>>)modeHashMap.clone();
        newFig.setCurrentMode(currentMode);
        newFig.setXY(x, y);
        return newFig;
    }

    public static MyFiguresHex newFigure(){
        MyFiguresHex fCurrent;
        Random random;
        random = new Random(System.currentTimeMillis());
        int num = Math.abs(random.nextInt()) % 8;
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
                fCurrent = new F_C();
                break;
            case 6:
                fCurrent = new F_L_ex();
                break;
            case 7:
                fCurrent = new F_P_ex();
                break;
            default:
                fCurrent = new F_I();
                break;
        }
  //      fCurrent = new F_L_ex();
        fCurrent.setCurrentMode(Math.abs(random.nextInt()) % modes);
        return fCurrent;
    }
}
