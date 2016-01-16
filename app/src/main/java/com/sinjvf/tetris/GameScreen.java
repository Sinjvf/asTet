package com.sinjvf.tetris;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Sinjvf on 03.03.2015.
 */
public abstract class GameScreen {
    protected int nI, nJ;
    protected ArrayList <ArrayList<Boolean> > screenArray;

    protected ArrayList <ArrayList<Integer> > colorArray;
    protected ArrayList <Boolean> blankLine;
    protected ArrayList <Integer> tonelessLine;


    public GameScreen(int iSize, int jSize){
        blankLine = new ArrayList<>();
        screenArray = new ArrayList<>();
        tonelessLine = new ArrayList<>();
        colorArray = new ArrayList<>();
        nI = iSize;
        nJ = jSize;
        for (int i =0; i< nI;i++){
            blankLine.add(false);
            tonelessLine.add(Const.COLOR_NULL);
        }
        for (int i =0; i< nJ;i++){
            screenArray.add((ArrayList<Boolean>) blankLine.clone());
            colorArray.add((ArrayList<Integer>) tonelessLine.clone());
        }
    }
    public int getnI() {
        return nI;
    }

    public abstract boolean isFull(int i, int j);
    public Integer getColor(int i, int j){
        if (!isFull(i, j))
            return Const.COLOR_NULL;
        else
            return colorArray.get(j).get(i);
    }

    /**Proof of ability of move or rotation*/
    /**return 1 if figure must stay in this position
     * return 2 if game over
     * return -1 if figure may shift
     * in the other way return 0*/
    public int canMoveOrRotate(MyFigures fig, int shiftX, int shiftY, int shiftMode){
        MyFigures newFig = fig.clone();
        newFig.move(shiftX, shiftY);
        HashSet<Point> field = newFig.getFieldsWithPosition(shiftMode);
   //     Log.d(Const.LOG_TAG, "new_pos  , x="+shiftX+", y="+shiftY+", Sh="+shiftMode);
        for (Point k : field) {
            //bottom
            if (k.y>= nJ){
                return 1;}
            //wall
            if (k.x<0 ||k.x>=nI) {

                return 0;
            }
            //other fig prevents
            if (isFull(k.x,k.y)){
                //other fig prevents rotation
                if (shiftY == 0 ) {

                    return 0;
                }
                //game over
                else if (k.y<=0) {

       //             Log.d(Const.LOG_TAG,"game over");
                    return 2;
                }
                //fig lay on the other fig
                else {

                    return 1;
                }
            }
        }
        //figure may move
        return -1;
    }


    //1 if can't move
    //-1 if can
    public  int canMoveOrRotateWithOther(MyFigures fig_real, Game.MischievousFigures mis, int type_fig, int shiftX, int shiftY, int shiftMode){
        int [][]full = spaceOtherFig(fig_real,  mis, type_fig);
        MyFigures fig=null;
        switch (type_fig) {
            case Const.REAL_FIG:
            case Const.REAL_FIG_TIME:
                fig = fig_real;
                break;
            default:
                for (int i=0;i<mis.getNumb();i++) {
                if (type_fig==Const.MISCH_FIG[i])
                    fig=mis.get(i);
            }  break;
        }
        MyFigures newFig = fig.clone();
        newFig.move(shiftX, shiftY);
        HashSet<Point> field = newFig.getFieldsWithPosition(shiftMode);
        //     Log.d(Const.LOG_TAG, "new_pos  , x="+shiftX+", y="+shiftY+", Sh="+shiftMode);
        for (Point k : field) {
            //other fig prevents
            if (k.y>0 && k.y<nJ) {
                if (full[k.x][k.y]==1) {
                    //other fig prevents rotation
                    return 1;
                }
                else if (full[k.x][k.y]==2) {
                    //other fig prevents rotation
                    return 2;
                }
            }
        }
        return -1;
    }
    /**del full lines and */
    public int deleteLineIfNesessary(){
        int removingLines=0;
        boolean fullLine;
        for (int j=0; j< nJ;j++){
            fullLine = true;
            for (int i=0;i< nI;i++){
                if (!isFull(i, j)) {fullLine = false;
                break;}
            }
            if (fullLine){
                removeLine(j);
                removingLines++;
            }
        }
        return (int)Math.pow(3, removingLines-1);
    }

    public  void removeLine(int j){
        screenArray.remove(j);
        colorArray.remove(j);
        //add the blank line in the top
        screenArray.add(0, (ArrayList<Boolean>) blankLine.clone());
        colorArray.add(0, (ArrayList<Integer>) tonelessLine.clone());
    }

    public void fillFigureSpace(MyFigures fig){
        HashSet<Point> field = fig.getFieldsWithPosition(0);
        ArrayList<Boolean> tmpLine;
        ArrayList<Integer> tmpLineColor;
        for (Point k:field){
            if (k.y>=0){
                tmpLine =(ArrayList<Boolean>) screenArray.get(k.y).clone();
                tmpLineColor =(ArrayList<Integer>) colorArray.get(k.y).clone();
                tmpLine.set(k.x, true);
                tmpLineColor.set(k.x, fig.colorScheme);
                screenArray.set(k.y, tmpLine);
                colorArray.set(k.y, tmpLineColor);
            }
        }


    }
    //-1=free
    //1=fill
    //2=next lay

    private int [][] spaceOtherFig(MyFigures fig_real, Game.MischievousFigures mis, int type_fig){
        int [][]spaceFig= new int[nI][nJ];
        int tmp;
        HashSet<Point> field;
        int i;
        for (i =0; i< nI;i++) {
            for (int j = 0; j < nJ; j++) {
                spaceFig[i][j]=-1;
            }
        }
        if (mis.isPrevent()) {
            switch (type_fig){
                case Const.REAL_FIG:
                case Const.REAL_FIG_TIME:
                    for (i=0;i<mis.getNumb();i++){
                        field = mis.get(i).getFieldsWithPosition(0);
                        if (canMoveOrRotate(mis.get(i),0, 1, 0)==1)
                            tmp=2;
                        else
                            tmp=1;
                        for (Point k:field){
                            if (k.y>0 && k.y<nJ)
                             spaceFig[k.x][k.y]=tmp;
                        }
                    }
                    break;
                default:
                    field = fig_real.getFieldsWithPosition(0);
                    for (Point k:field){
                        if (canMoveOrRotate(fig_real,0, 1, 0)==1)
                            tmp=2;
                        else
                            tmp=1;
                        if (k.y>0 && k.y<nJ)
                          spaceFig[k.x][k.y]=tmp;
                    }
                    for (i=0;i<mis.getNumb();i++) {
                        if(i!=type_fig-2) {
                            field = mis.get(i).getFieldsWithPosition(0);
                            if (canMoveOrRotate(mis.get(i),0, 1, 0)==1)
                                tmp=2;
                            else
                                tmp=1;
                            for (Point k : field) {
                                if (k.y>0 && k.y<nJ)
                                    spaceFig[k.x][k.y] = tmp;
                            }
                        }
                    }
                    break;
            }
        }
        return spaceFig;
    }
}
