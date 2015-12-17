package com.example.tetris.hex;

import android.util.Log;

import com.example.tetris.Const;
import com.example.tetris.GameScreen;

import java.util.ArrayList;

/**
 * Created by Sinjvf on 03.03.2015.
 */
public class GameScreenHex extends GameScreen{


    public GameScreenHex(int i_size, int j_size){
        super(i_size, j_size);
    }

    //TODO
@Override
    public boolean isFull(int i, int j){
        if (j<0)return false;
        return screenArray.get(j).get(i);
    }
    //TODO
    @Override
    protected  void removeLine(int j) {
        screenArray.remove(j);
        //add the blank line in the top
        screenArray.add(0, (ArrayList<Boolean>) blankLine.clone());

    }
}
