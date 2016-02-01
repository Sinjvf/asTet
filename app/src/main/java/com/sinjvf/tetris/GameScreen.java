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
    private int n = 8;

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
    /**in Const
     * return LAY if figure must stay in this position
     * return GAME_OVER if game over
     * return CAN if figure may shift
     * in the other way return 0*/
    public int canMoveOrRotate(MyFigures fig, int shiftX, int shiftY, int shiftMode){
        MyFigures newFig = fig.clone();
        int tmp=Const.MOVE_CAN;
        newFig.move(shiftX, shiftY);
        HashSet<Point> field = newFig.getFieldsWithPosition(shiftMode);
   //     Log.d(Const.LOG_TAG, "new_pos  , x="+shiftX+", y="+shiftY+", Sh="+shiftMode);
        for (Point k : field) {
            //bottom
            if (k.y>= nJ){
                Log.d(Const.LOG_TAG,"bottom");
                return Const.MOVE_BOTTOM;
            }
            //wall
            if (k.x<0 ||k.x>=nI) {
                Log.d(Const.LOG_TAG,"wall");
                return Const.MOVE_WALL;
            }
            if (k.y<0 &&k.x==nI-1 ){
                return Const.MOVE_WALL;
            }
            if (tmp==Const.MOVE_LAY && k.y<0) {
                return Const.MOVE_GAME_OVER;
            }
            //other fig prevents
            if (isFull(k.x,k.y)){

                Log.d(Const.LOG_TAG,"full. k.y="+k.y+", k.x="+k.x);
                //other fig prevents rotation
                if (shiftY == 0 ) {
                    return Const.MOVE_OTHER_PREVENT;
                }
                //game over
                else if (k.y<=0) {
                    Log.d(Const.LOG_TAG,"game over");
                    return Const.MOVE_GAME_OVER;
                }
                //fig lay on the other fig
                else {
                    Log.d(Const.LOG_TAG,"lay");
                 //   return Const.MOVE_LAY;
                    tmp=Const.MOVE_LAY;
                }
            }
        }
        //figure may move
        return tmp;
    }


    //1 if can't move
    //-1 if can
    //2 other prevent
    /*
    type_fig:
    Const.REAL_FIG - x or r moving of real fig
    Const.REAL_FIG_TIME - y moving of real fig
    2-4: misch fig */
    public  int canMoveOrRotateWithOther(MyFigures fig_real, Game.MischievousFigures mis, int type_fig, int shiftX, int shiftY, int shiftMode){
        //if all misch unvisible
        boolean tmpBool =false;
        for (int i=0;i<mis.getNumb();i++) {
            tmpBool=tmpBool||(mis.getVisible(i));
        }
        if (!tmpBool) return Const.MOVE_WITH_CAN;
        //put array with position of other figures
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
            if (k.y>=0 && k.y<nJ) {
                if (full[k.x][k.y]==1) {
                    //other fig prevents rotation
                    return Const.MOVE_WITH_CANNOT;
                }
                else if (full[k.x][k.y]==2) {
                    //other fig prevents rotation
                    return Const.MOVE_WITH_OTHER_LAY;
                }
            }
            else if (k.y<0 ){
                if (full[k.x][-k.y+nJ] == 1) {
                    //other fig prevents rotation
                    return Const.MOVE_WITH_CANNOT;
                } else if (full[k.x][-k.y+nJ] == 2) {
                    //other fig prevents rotation
                    return Const.MOVE_WITH_OTHER_LAY;
                }
            }
        }
        return Const.MOVE_WITH_CAN;
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
        int [][]spaceFig= new int[nI][nJ+n];
        int tmp;
        HashSet<Point> field;
        int i;
        for (i =0; i< nI;i++) {
            for (int j = 0; j < nJ+n; j++) {
                spaceFig[i][j]=-1;
                
            }
           
        }
        if (mis.isPrevent()) {
            switch (type_fig){
                case Const.REAL_FIG:
                case Const.REAL_FIG_TIME:
                    for (i=0;i<mis.getNumb();i++){
                        if(mis.getVisible(i)) {
                            field = mis.get(i).getFieldsWithPosition(0);
                            if (canMoveOrRotate(mis.get(i), 0, 1, 0) == 1)
                                tmp = 2;
                            else
                                tmp = 1;
                            for (Point k : field) {
                                if (k.y >= 0 && k.y < nJ) {
                                    spaceFig[k.x][k.y] = tmp;
                                } else if (k.y < 0) {
                                    spaceFig[k.x][-k.y + nJ] = tmp;
                                }
                            }
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
                        if (k.y>=0 && k.y<nJ)
                          spaceFig[k.x][k.y]=tmp;
                        else if(k.y<0 ){
                            spaceFig[k.x][-k.y+nJ]=tmp;
                        }
                    }
                    for (i=0;i<mis.getNumb();i++) {
                        if((i!=type_fig-2 && mis.getVisible(i))) {
                            field = mis.get(i).getFieldsWithPosition(0);
                            if (canMoveOrRotate(mis.get(i),0, 1, 0)==1)
                                tmp=2;
                            else
                                tmp=1;
                            for (Point k : field) {
                                if (k.y>=0 && k.y<nJ)
                                    spaceFig[k.x][k.y] = tmp;
                                else if(k.y<0 ){
                                    spaceFig[k.x][-k.y+nJ]=tmp;
                                }
                            }
                        }
                    }
                    break;
            }
        }
        return spaceFig;
    }
}
