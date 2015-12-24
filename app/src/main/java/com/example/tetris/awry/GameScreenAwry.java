package com.example.tetris.awry;

import android.util.Log;

import com.example.tetris.Const;
import com.example.tetris.GameScreen;

import java.util.ArrayList;

/**
 * Created by Sinjvf on 03.03.2015.
 */
public class GameScreenAwry extends GameScreen{



    public GameScreenAwry(int i_size, int j_size){
        super(i_size, j_size);
    }

@Override
    public boolean isFull(int i, int j){
        if (j<0)return false;
        else if (j%2==0 && i== nI-1) return true;
        return screenArray.get(j).get(i);
    }

    @Override
    protected  void removeLine(int j){
/*        ArrayList <Boolean> TmpLine;
        int k=0;
        for (int i=j-2;i>0;i=i-2){

            Log.d(Const.LOG_TAG, "copy line =  "+i+"  to  "+ (Integer)(i+2));
            TmpLine=(ArrayList<Boolean>)screenArray.get(i).clone();
            screenArray.set(i + 2, TmpLine);
            k=k+2;
            if (TmpLine.equals(blankLine)) break;
        }
        screenArray.set(j-k, blankLine);
        */
        screenArray.remove(j);
        screenArray.add(0, blankLine);
    }


}
