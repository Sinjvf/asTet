package com.sinjvf.tetris.awry;

import android.graphics.Point;

import com.sinjvf.tetris.Const;
import com.sinjvf.tetris.MyFigures;

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
        primaryY = Const.PRIMARY_Y[Const.AWRY];
        x=Const.NW[Const.AWRY]/2;
        y=primaryY;
        movingStep = Const.MOVING_STEP[Const.AWRY];
        modes=Const.MODES[Const.AWRY];
    }

    protected void setOddHashMap(){
        HashSet<Point> hs1, hs;//=new HashSet<Point>();
        for (int k=0;k<modes;k++){
            hs1=new HashSet<>();
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
            return modeHashMap;
        }
        else{
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
        int num = Math.abs(random.nextInt()) % 18;
        switch (num) {
            case 1:
                fCurrent = new F_C();
                break;
            case 2:
                fCurrent = new F_F_Left();
                break;
            case 3:
                fCurrent = new F_F_Right();
                break;
            case 4:
                fCurrent = new F_Y();
                break;
            case 5:
                fCurrent = new F_L();
                break;
            case 6:
                fCurrent = new F_L_ex();
                break;
            case 7:
                fCurrent = new F_L_ex2();
                break;
            case 8:
                fCurrent = new F_O();
                break;
            case 9:
                fCurrent = new F_O_ex();
                break;
            case 10:
                fCurrent = new F_P();
                break;
            case 11:
                fCurrent = new F_P_ex();
                break;
            case 12:
                fCurrent = new F_P_ex2();
                break;
            case 13:
                fCurrent = new F_T();
                break;
            case 14:
                fCurrent = new F_T_ex();
                break;
            case 15:
                fCurrent = new F_Z_back();
                break;
            case 16:
                fCurrent = new F_Z();
                break;
            default:
                fCurrent = new F_I();

        }
       // fCurrent=new F_T_ex();
        fCurrent.setCurrentMode(Math.abs(random.nextInt()) % modes);
        return fCurrent;
    }
}
