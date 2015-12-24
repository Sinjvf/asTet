package com.example.tetris.standart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;

import com.example.tetris.Const;
import com.example.tetris.Drawing;

/**
 * Created by sinjvf on 20.11.15.
 */
public class DrawingStandart extends Drawing{

    public DrawingStandart(Context context) {
        super( context);
    }
    @Override
    public void setHW( int height, int width){
        this.height = height;
        this.width = width;
        hShift = height/2-iW*3/2;
        iW = (width*3/4)/ Const.NW[Const.STANDART]; //height / Const.NH[Const.STANDART];
        this.shiftx = Const.SHIFTX;
        this.shifty = (height-Const.NH[Const.STANDART]*iW)/2;
    }

    /**
     * draw rectangle on canvas with p in (i, j) point
     */
    @Override
    protected void drawField(int colCore, int colEdg , int i, int j) {
        int absI, absJ;
        absI = shiftx+i * iW;
        absJ = shifty+j * iW;
        Paint p = new Paint();
        p.setDither(true);
        RadialGradient gradient = new RadialGradient(absI + iW/2, absJ+iW/2,
                (int)(Math.sqrt(2)*iW), colCore,
                colEdg, android.graphics.Shader.TileMode.CLAMP);
        p.setShader(gradient);
        canvas.drawRect(absI + Const.TRACE,
                        absJ + Const.TRACE,
                        absI +iW- Const.TRACE,
                        absJ+ iW- Const.TRACE,
                        p);
    }
    @Override
    protected  void drawFieldForNextFugure(int colCore, int colEdg, int i, int j) {
        Integer iiw;
        iiw = iW/2;
        int absI, absJ;
        absI = (i-1) * iiw +  iW*Const.NW[Const.STANDART] ;
        absJ = (j+2)* iiw  +hShift+iW;
        Paint p = new Paint();
        p.setDither(true);
        RadialGradient gradient = new RadialGradient(absI + iiw/2, absJ+iiw/2,
                                                    (int)(Math.sqrt(2)*iiw),
                                                    colCore,colEdg, android.graphics.Shader.TileMode.CLAMP);
        p.setShader(gradient);
        canvas.drawRect(absI+ Const.TRACE +1,
                        absJ+ Const.TRACE,
                        absI+ iiw -  Const.TRACE ,
                        absJ+iiw - Const.TRACE,
                        p);
    }
    /**
     * draw fulling rectangles
     */
    @Override
    public void drawFullScreen() {
        for (int i = 0; i < Const.NW[Const.STANDART]; i++) {
            for (int j = 0; j < Const.NH[Const.STANDART]; j++) {
                if (screen.isFull(i, j)){
                    drawField(Const.COLOR_CORE,Const.COLOR_EDGE, i, j);
                }
            }
        }
    }
    @Override
    public void drawGrid(Integer score, int level) {
        Paint p = new Paint();
        p.setStrokeWidth(Const.TRACE * 2);
        p.setColor(Color.BLACK);
        for (int i = 0; i <=Const.NW[Const.STANDART]; i++)
            canvas.drawLine(shiftx+i * iW,
                            shifty+0,
                            shiftx+i * iW,
                            shifty+iW*Const.NH[Const.STANDART],
                            p);
        for (int i = 0; i <= Const.NH[Const.STANDART]; i++)
            canvas.drawLine(shiftx+0,
                            shifty+i * iW,
                            shiftx+iW * Const.NW[Const.STANDART],
                            shifty+ i * iW,
                            p);

        drawMyText(score, level, (width + iW * Const.NW[Const.STANDART]) / 2, hShift, iW);

    }



    //my small crutch. Not used
public void drawGameOver(){
    Paint p1 = new Paint();
    Paint p = new Paint();
    Rect mTextBoundRect = new Rect();
    String text1 = "Game Over!";
    String text2 = "Press \"exit\" key to escape";
    float realWidth1, realWidth2, realHeight1, realHeight2;
    p1.setARGB(255, 52, 104, 255);
    p1.setStyle(Paint.Style.FILL);
    canvas.drawRect(iW+shiftx, shifty+6*iW, width - (shiftx+iW), height -(shifty+6*iW), p1 ) ;

    p.setColor(Color.BLACK);
    p.setTextSize(80);
    realWidth1 = p.measureText(text1);
    p.getTextBounds(text1, 0, text1.length(), mTextBoundRect);
    realHeight1 = mTextBoundRect.height();
    canvas.drawText(text1,
            width/2 - (realWidth1 / 2f),
            height/2 + (realHeight1 /2f-2*iW),
            p
    );

    p.setTextSize(30);
    realWidth2 = p.measureText(text2);
    p.getTextBounds(text2, 0, text2.length(), mTextBoundRect);
    realHeight2 = mTextBoundRect.height();
    canvas.drawText(text2,
            width/2 - (realWidth2 / 2f),
            height/2 + (realHeight2 /2f+iW),
            p
    );
}
}
