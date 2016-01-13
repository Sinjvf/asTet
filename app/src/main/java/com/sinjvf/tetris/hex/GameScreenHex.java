package com.sinjvf.tetris.hex;

import com.sinjvf.tetris.GameScreen;

import java.util.ArrayList;

/**
 * Created by Sinjvf on 03.03.2015.
 */
public class GameScreenHex extends GameScreen{


    public GameScreenHex(int i_size, int j_size){
        super(i_size, j_size);
    }

@Override
    public boolean isFull(int i, int j){
        if (j<0)return false;
        return screenArray.get(j).get(i);
    }
    @Override
    protected  void removeLine(int j) {
        screenArray.remove(j);
        //add the blank line in the top
        screenArray.add(0, (ArrayList<Boolean>) blankLine.clone());

    }
}
