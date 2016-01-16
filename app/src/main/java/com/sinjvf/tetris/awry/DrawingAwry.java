package com.sinjvf.tetris.awry;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;

import com.sinjvf.tetris.Const;
import com.sinjvf.tetris.Drawing;

/**
 * Created by sinjvf on 20.11.15.
 */
public class DrawingAwry extends Drawing{

    public DrawingAwry(Context context, int fileID) {
        super(context, fileID);
    }
    @Override
    public void setHW( int height, int width){
        this.height = height;
        this.width = width;
        iW = (width*3/4)/ (Const.NW[Const.AWRY]); //height
        if ((Const.NH[Const.AWRY])*iW>height)
            iW = Math.round(height/ Const.NH[Const.AWRY]);
        iW= (iW%2==0)?iW:iW-1;
        hShift = height/2+iW/2;
        shiftx = Const.SHIFTX;
        shifty = (height-Const.NH[Const.AWRY]*iW)/2;

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
        int absInext, absJnext;
        int secondX, secondY;
        absJ = shifty+iW*j/2;
        absI = (j%2==0)
                ?(shiftx+(i+1)*iW)- iW/ 6
                :(int)(shiftx+(i+0.5f)*iW)+ iW/ 6;


        absJnext = shifty+iW*(j+1)/2;
        absInext = ((j+1)%2==0)
                ?(shiftx+(i+1)*iW)
                :(int)(shiftx+(i+0.5f)*iW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(color);
        Path path = new Path();
        path.moveTo(absI, absJ + iW * 2 / 3);
        path.lineTo(absInext, absJnext + iW * 1 / 2);
        path.lineTo(absInext, absJnext + iW / 4);
        path.lineTo(absInext, absJnext + iW * 1 / 2);
        secondX = (j%2==0)
                ?absInext+iW/4
                :absInext-iW/4;
        secondY = absJnext + iW * 1 / 2;
        path.lineTo(secondX, secondY );

        path.lineTo(absInext, absJnext + iW * 1 / 2);
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
    protected void drawField(int colCore, int colEdg ,  int i, int j) {
        Paint p =new Paint();
        Path path=new Path();
        int absI, absJ;
        path.reset();
        absJ = shifty+iW*j/2;
        absI = (j%2==0)
                        ?(shiftx+(i+1)*iW)
                        :(int)(shiftx+(i+0.5f)*iW);
        p.setDither(true);
        RadialGradient gradient = new RadialGradient(absI, absJ+iW/2, iW, colCore,
                colEdg, android.graphics.Shader.TileMode.CLAMP);
        p.setShader(gradient);


        path.moveTo(absI, absJ+Const.TRACE);
        path.lineTo(absI-iW/2+Const.TRACE, absJ+iW/2);
        path.lineTo(absI, absJ+iW-(Const.TRACE+1));
        path.lineTo(absI+iW/2-(Const.TRACE+1), absJ+iW/2);
        path.close();
        canvas.drawPath(path, p);
    }
    @Override
    protected  void drawFieldForNextFugure(int colCore, int colEdg , int i, int j) {
        Paint p =new Paint();
        Integer iiw;
        iiw = iW/2;
        Path path=new Path();
        int absI, absJ;
        path.reset();
        absJ = hShift+iW*2+iiw*j/2;
        absI = iW*(Const.NW[Const.AWRY] )+
                ((j%2==0) ?
                        (shiftx+(i)*iiw)
                        :(int)(shiftx+(i-0.5f)*iiw));
        p.setDither(true);
        RadialGradient gradient = new RadialGradient(absI, absJ+iiw/2, iiw, colCore,
                colEdg, android.graphics.Shader.TileMode.CLAMP);
        p.setShader(gradient);
        path.moveTo(absI, absJ+Const.TRACE);
        path.lineTo(absI-iiw/2+Const.TRACE, absJ+iiw/2);
        path.lineTo(absI, absJ+iiw-(Const.TRACE+1));
        path.lineTo(absI+iiw/2-(Const.TRACE+1), absJ+iiw/2);
        path.close();
        canvas.drawPath(path, p);
    }

    /**
     * draw fulling rectangles
     */
    @Override
    public void drawFullScreen() {
        Integer color;
        for (int i = 0; i < Const.NW[Const.AWRY]; i++) {
            for (int j = 0; j < Const.NH[Const.AWRY]*2-1; j++) {
                if (screen.isFull(i, j) && !(j%2==0  && i==Const.NW[Const.AWRY]-1)){
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
        /** fill field by color*/
        p.setStrokeWidth(Const.TRACE*2);
        p.setColor(Color.BLACK);

        for (int j=0; j<Const.NH[Const.AWRY];j++) {
            for (int i = 0; i < Const.NW[Const.AWRY] ; i++) {
                canvas.drawLine(shiftx + i * iW,
                        shifty + j * iW,
                        shiftx + (i + 1) * iW,
                        shifty + (j + 1) * iW,
                        p);

                canvas.drawLine(shiftx + (i + 1) * iW,
                        shifty + j * iW,
                        shiftx + (i) * iW,
                        shifty + (j + 1) * iW,
                        p);
            }
        }
     //   drawMyText(score, level,(width + iW * Const.NW[Const.AWRY]) / 2, hShift, iW );

    }


}
