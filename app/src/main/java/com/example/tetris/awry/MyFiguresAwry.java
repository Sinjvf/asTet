package com.example.tetris.awry;

import android.graphics.Point;
import android.util.Log;

import com.example.tetris.Const;
import com.example.tetris.MyFigures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


/**
 * Created by Sinjvf on 03.03.2015.
 */
public class MyFiguresAwry extends MyFigures{
  //  protected HashMap<Integer, HashSet<Point>>modeOddHashMap;


    MyFiguresAwry(){
        super();
        x=Const.NW[Const.AWRY]/2-1;
        y=-3;
        movingStep = Const.MOVING_STEP[Const.AWRY];
        modes=Const.MODES[Const.AWRY];
    }


    protected void setOddHashMap(){
        HashSet<Point> hs1, hs;//=new HashSet<Point>();
        for (int k=0;k<modes;k++){
            hs1=new HashSet<Point>();
            hs = modeHashMap.get(k);
            for (Point hsK: hs)
            {
                if (hsK.y%2==0){
                    hs1.add(new Point(hsK.x, hsK.y));
                }
                else{
                    hs1.add(new Point(hsK.x-1, hsK.y));
                }
            }
            modeHashMap.put(k+modes, hs1);
        }
    }
    @Override
    protected  HashMap<Integer, HashSet<Point>> getHashMap(){
        if (y%2==0){

            Log.d(Const.LOG_TAG+11, "-----------------normal------- x="+x+", y="+y);
            return modeHashMap;
        }
        else{

            Log.d(Const.LOG_TAG+11, "-----------------odd---------- x="+x+", y="+y);
            HashSet<Point> hs;
            HashMap<Integer,  HashSet<Point > > modeOddHashMap = new HashMap<Integer,  HashSet<Point > >();
            for (int k=0;k<modes;k++){
                hs=modeHashMap.get(k+modes);
                modeOddHashMap.put(k, hs);
            }
            return modeOddHashMap;
    }
    }

    public void move(int i, int j){

        Log.d(Const.LOG_TAG, "moving! x="+x+", y="+y);
        if (i % 2 == 0)
            x += i/2;
        else
            if (y%2==0){
                x=x+(i+1)/2;
                y=y+1;
            }
            else{
                x=x+(i-1)/2;
                y=y+1;
            }

//        x+=i;

        y=y+j* movingStep;
        Log.d(Const.LOG_TAG, "moved! x="+x+", y="+y);

    }

    public MyFiguresAwry clone(){
        MyFiguresAwry newFig = new MyFiguresAwry();
        newFig.modeHashMap = (HashMap<Integer, HashSet<Point>>)modeHashMap.clone();
        newFig.setCurrentMode(currentMode);
        newFig.setXY(x, y);
        return newFig;
    }
    public static MyFiguresAwry newFigure(){
        MyFiguresAwry fCurrent;
        Random random;
        random = new Random(System.currentTimeMillis());
        int num = Math.abs(random.nextInt()) % 9;
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
            case 7:
                fCurrent = new F_I();
                break;
            default:
                fCurrent = new F_I_extended();
                break;
        }
        //fCurrent=new F_I_extended();
        fCurrent.setCurrentMode(Math.abs(random.nextInt()) % modes);
        return fCurrent;
    }
}
