package com.sinjvf.tetris.awry;

import com.sinjvf.tetris.GameScreen;

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

}
