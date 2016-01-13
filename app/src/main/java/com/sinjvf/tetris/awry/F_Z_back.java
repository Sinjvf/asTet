package com.sinjvf.tetris.awry;

import android.graphics.Point;

import java.util.HashSet;

/**
 * Created by Sinjvf on 07.03.2015.
 */public class F_Z_back extends MyFiguresAwry {
    F_Z_back(){
        super();
        HashSet<Point> hs=new HashSet<Point>();
        hs.add(new Point(-1, 0)); hs.add(new Point(0, 0));hs.add(new Point(0, 1));hs.add(new Point(1, 1));
        modeHashMap.put(0, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(0, 2)); hs.add(new Point(0, 0));hs.add(new Point(1, 1));hs.add(new Point(1, -1));
        modeHashMap.put(1, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(-1, 0)); hs.add(new Point(0, 0));hs.add(new Point(0, 1));hs.add(new Point(1, 1));
        modeHashMap.put(2, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(0, 2)); hs.add(new Point(0, 0));hs.add(new Point(1, 1));hs.add(new Point(1, -1));
        modeHashMap.put(3, hs);

        setOddHashMap();
    }
}

