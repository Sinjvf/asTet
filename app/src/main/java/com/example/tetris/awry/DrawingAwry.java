package com.example.tetris.awry;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;

import com.example.tetris.Const;
import com.example.tetris.Drawing;

/**
 * Created by sinjvf on 20.11.15.
 */
public class DrawingAwry extends Drawing{

    public DrawingAwry(Context context) {
        super(context);
    }
    @Override
    public void setHW( int height, int width){
        this.height = height;
        this.width = width;
        iW = (width*3/4)/ (Const.NW[Const.AWRY]); //height
        iW= (iW%2==0)?iW:iW-1;
        hShift = height/2-iW*3/2;
        shiftx = Const.SHIFTX;
        shifty = (height-Const.NH[Const.AWRY]*iW)/2;
    }



    /**
     * draw rectangle on canvas with p in (i, j) point
     */
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

     /*   RadialGradient gradient = new RadialGradient(absI+(int)(iW*Math.sqrt(2)), absJ+(int)(iW*Math.sqrt(2)), (int)(iW*Math.sqrt(2)), Const.COLOR_CORE,
                Const.COLOR_EDGE, android.graphics.Shader.TileMode.CLAMP);

        p.setShader(gradient);
        */

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
        for (int i = 0; i < Const.NW[Const.AWRY]; i++) {
            for (int j = 0; j < Const.NH[Const.AWRY]*2-1; j++) {
                if (screen.isFull(i, j) && !(j%2==0  && i==Const.NW[Const.AWRY]-1)){
                    drawField(Const.COLOR_CORE_RED,Const.COLOR_EDGE_RED, i, j);
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
        drawMyText(score, level,(width + iW * Const.NW[Const.AWRY]) / 2, hShift, iW );

    }


}
