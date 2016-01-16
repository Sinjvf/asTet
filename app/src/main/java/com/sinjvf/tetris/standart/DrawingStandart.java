package com.sinjvf.tetris.standart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;

import com.sinjvf.tetris.Const;
import com.sinjvf.tetris.Drawing;

/**
 * Created by sinjvf on 20.11.15.
 */
public class DrawingStandart extends Drawing{

    public DrawingStandart(Context context, int fileID) {
        super( context,  fileID);
    }
    @Override
    public void setHW( int height, int width){
        this.height = height;
        this.width = width;
        iW = Math.round((width*3/4)/ Const.NW[Const.STANDART]);
        if ((Const.NH[Const.STANDART])*iW>height)
            iW = Math.round(height/ Const.NH[Const.STANDART]);
        hShift = height/2+iW/2;
        this.shiftx = Const.SHIFTX;
        this.shifty = (height-Const.NH[Const.STANDART]*iW)/2;

        if(fileID!=0)
            back.setSize(width, height);
    }

    /**
     * draw rectangle on canvas with p in (i, j) point
     */

    @Override
    protected  void drawFieldArrow(int i, int j, int color){
        Paint paint = new Paint();
        int absI, absJ;
        absI = shiftx+i * iW;
        absJ = shifty+j * iW;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(color);
        Path path = new Path();
        path.moveTo(absI+iW/2, absJ+iW*3/4);
        path.lineTo(absI + iW / 2, absJ + iW * 3 / 2);
        path.lineTo(absI + iW / 4, absJ + iW );
        path.lineTo(absI + iW / 2, absJ + iW * 3 / 2);
        path.lineTo(absI + iW * 3 / 4, absJ + iW );
        path.lineTo(absI + iW / 2, absJ + iW * 3 / 2);
        path.close();
        canvas.drawPath(path, paint);

    }
    @Override
    protected void drawArrows(){
        for (int i=0; i<screen.getnI();i++){
            drawFieldArrow(i, 0, Const.COLOR_ARROW);
            drawFieldArrow(i, 1, Const.COLOR_ARROW);
        }
    }
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
                absI + iW - Const.TRACE,
                absJ + iW - Const.TRACE,
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
        Integer color;
        for (int i = 0; i < Const.NW[Const.STANDART]; i++) {
            for (int j = 0; j < Const.NH[Const.STANDART]; j++) {
                if (screen.isFull(i, j)){
                    color = screen.getColor(i, j);
                    if (color<0||color>=Const.MAX_COLOR-1)
                        color = Const.MAX_COLOR-1;
                    drawField(Const.COLOR_FIGURES_CORE[color],Const.COLOR_FIGURES_EDGE[color], i, j);
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
                            shifty,
                            shiftx+i * iW,
                            shifty+iW*Const.NH[Const.STANDART],
                            p);
        for (int i = 0; i <= Const.NH[Const.STANDART]; i++)
            canvas.drawLine(shiftx,
                            shifty+i * iW,
                            shiftx+iW * Const.NW[Const.STANDART],
                            shifty+ i * iW,
                            p);

  //      drawMyText(score, level, (width + iW * Const.NW[Const.STANDART]) / 2, hShift, iW);

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
