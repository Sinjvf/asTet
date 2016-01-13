package com.sinjvf.tetris.hex;

import android.graphics.Point;

import java.util.HashSet;

/**
 * Created by Sinjvf on 07.03.2015.
 */
public class F_C extends MyFiguresHex {
    F_C(){
        super();
        HashSet<Point> hs=new HashSet<Point>();
        hs.add(new Point(-1, -1)); hs.add(new Point(0,-1));hs.add(new Point(1, -1));hs.add(new Point(1, 0));
        modeHashMap.put(0, hs);

        hs=new HashSet<Point>();
        hs.add(new Point(0, 1)); hs.add(new Point(0,-1));hs.add(new Point(1, -1));hs.add(new Point(1, 0));
        modeHashMap.put(1, hs);

        hs=new HashSet<Point>();
        hs.add(new Point(0, 1)); hs.add(new Point(-1,0));hs.add(new Point(1, -1));hs.add(new Point(1, 0));
        modeHashMap.put(2, hs);

        hs=new HashSet<Point>();
        hs.add(new Point(0, 1)); hs.add(new Point(-1,0));hs.add(new Point(-1, -1));hs.add(new Point(1, 0));
        modeHashMap.put(3, hs);

        hs=new HashSet<Point>();
        hs.add(new Point(0, 1)); hs.add(new Point(-1,0));hs.add(new Point(-1, -1));hs.add(new Point(0, -1));
        modeHashMap.put(4, hs);

        hs=new HashSet<Point>();
        hs.add(new Point(1, -1)); hs.add(new Point(-1,0));hs.add(new Point(-1, -1));hs.add(new Point(0, -1));
        modeHashMap.put(5, hs);

        setOddHashMap();
    }
}
