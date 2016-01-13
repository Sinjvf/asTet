package com.sinjvf.tetris.awry;

import android.graphics.Point;

import java.util.HashSet;

/**
 * Created by Sinjvf on 03.03.2015.
 */
public class F_O_ex extends MyFiguresAwry {
    F_O_ex(){
        super();
       // x=x-1;
        HashSet<Point> hs=new HashSet<>();
        hs.add(new Point(0, -1)); hs.add(new Point(0, 1));hs.add(new Point(1, 1));hs.add(new Point(1, -1));
        modeHashMap.put(0, hs);
        hs=new HashSet<>();
        hs.add(new Point(0, -1)); hs.add(new Point(0, 1));hs.add(new Point(1, 1));hs.add(new Point(1, -1));
        modeHashMap.put(1, hs);
        hs=new HashSet<>();
        hs.add(new Point(0, -1)); hs.add(new Point(0, 1));hs.add(new Point(1, 1));hs.add(new Point(1, -1));
        modeHashMap.put(2, hs);
        hs=new HashSet<>();
        hs.add(new Point(0, -1)); hs.add(new Point(0, 1));hs.add(new Point(1, 1));hs.add(new Point(1, -1));
        modeHashMap.put(3, hs);


        setOddHashMap();
    }


}
