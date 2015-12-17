package com.example.tetris.hex;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.tetris.Const;
import com.example.tetris.Drawing;

/**
 * Created by sinjvf on 20.11.15.
 */
public class DrawingHex extends Drawing{


    @Override
    public void setHW( int height, int width){
        this.height = height;
        this.width = width;
        shiftx = Const.SHIFTX;

        iW = (width*3/4)/ (Const.NW[Const.HEX])/3; //height
        iH =  (int)Math.round (iW*Math.sqrt(3));
        if ((Const.NH[Const.HEX]*2-1)*iH>height) {
            shifty = Const.SHIFTX;
            iH= (height-shifty)/(Const.NH[Const.HEX]*2-1);
            iW = (int)Math.round (iH/Math.sqrt(3));
        }
        else {
            shifty = (height - (Const.NH[Const.HEX] * 2 -1) * iH) / 2;
        }
        hShift = height/2-iH*3/2;
        iW= (iW%2==0)?iW:iW-1;
        iH= (iH%2==0)?iH:iH-1;
    }



    /**
     * draw rectangle on canvas with p in (i, j) point
     */

    @Override
    protected void drawField(Paint p, int i, int j) {
        Path path=new Path();
        int absI, absJ;
        path.reset();
        p.setStyle(Paint.Style.FILL);
        absI = shiftx+iW/2+(iW*3/2)*i;
        absJ = shifty + ((i%2==0)
                        ?(int)((j)*iH)
                        :(int)((j+0.5f)*iH));


        path.moveTo(absI, absJ+Const.TRACE);
        path.lineTo(absI-iW/2+Const.TRACE, absJ+iH/2);
        path.lineTo(absI, absJ+iH-Const.TRACE);
        path.lineTo(absI+iW, absJ+iH-Const.TRACE);
        path.lineTo(absI+iW*1.5f-Const.TRACE, absJ+iH/2);
        path.lineTo(absI+iW, absJ+Const.TRACE);
        path.close();
        canvas.drawPath(path, p);
    }

    //TODO
    @Override
    protected  void drawFieldForNextFugure(Paint p, int i, int j) {
        Integer iiW, iiH;
        iiW = iW/2;
        iiH=iH/2;
        Path path=new Path();
        int absI, absJ;
        path.reset();
        p.setStyle(Paint.Style.FILL);
        absI = iW*3*(Const.NW[Const.HEX] )
                +(iiW*3/2)*(i-1) ;
        absJ = hShift+ iW*3+ 2*iiH+
                ((i%2==0)
                ?(int)(j*iiH)
                :(int)(j*iiH+iiH/2));
        path.moveTo(absI, absJ+Const.TRACE);
        path.lineTo(absI-iiW/2+Const.TRACE, absJ+iiH/2);
        path.lineTo(absI, absJ+iiH-Const.TRACE);
        path.lineTo(absI+iiW, absJ+iiH-Const.TRACE);
        path.lineTo(absI+iiW*1.5f-Const.TRACE, absJ+iiH/2);
        path.lineTo(absI+iiW, absJ+Const.TRACE);
        path.close();
        canvas.drawPath(path, p);
    }

    /**
     * draw fulling rectangles
     */
    //TODO
    @Override
    public void drawFullScreen() {

        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        for (int i = 0; i < Const.NW[Const.HEX]*2; i++) {
            for (int j = 0; j < Const.NH[Const.HEX]*2-1; j++) {
                if (screen.isFull(i, j) ){
                    drawField(p, i, j);
                }
            }
        }
    }
    //TODO text
    @Override
    public void drawGrid(Integer score, int level) {
        Paint p = new Paint();
        p.setStrokeWidth(Const.TRACE*2);
        p.setColor(Color.BLACK);

        for (int j=0; j<Const.NH[Const.HEX]*2;j++) {
            for (int i = 0; i < Const.NW[Const.HEX] ; i++) {
                //bottom
                if (j!=Const.NH[Const.HEX]*2-1) {
                    canvas.drawLine(shiftx + i * (iW * 3),
                            shifty + j * iH + iH / 2,
                            shiftx + i * (iW * 3) + iW / 2,
                            shifty + j * iH + iH,
                            p);
                    canvas.drawLine(shiftx + i * (iW * 3) + iW * 3 / 2,
                            shifty + j * iH + iH,
                            shiftx + i * (3 * iW) + 2 * iW,
                            shifty + j * iH + iH / 2,
                            p);
                    canvas.drawLine(shiftx + i * (iW * 3) + iW / 2,
                            shifty + j * iH + iH,
                            shiftx + i * (iW * 3) + iW * 3 / 2,
                            shifty + j * iH + iH,
                            p);
                }
                //up
                if (!((j==Const.NH[Const.HEX]*2-1) & (i==0))) {
                    canvas.drawLine(shiftx + i * (iW * 3),
                            shifty + (j) * iH + iH / 2,
                            shiftx + i * (iW * 3) + iW / 2,
                            shifty + (j) * iH,
                            p);
                }
                canvas.drawLine(shiftx + i * (iW*3)+iW*3/2,
                        shifty + (j ) *iH ,
                        shiftx + i * (3*iW)+2*iW,
                        shifty + (j) * iH+iH/2,
                        p);
                canvas.drawLine(shiftx + i * (iW*3)+iW*2,
                        shifty + (j ) * iH+iH/2 ,
                        shiftx + i * (iW*3) + iW*3,
                        shifty + (j) * iH  +iH/2,
                        p);

            }
            int i=Const.NW[Const.HEX];
            if (j!=0) {
                canvas.drawLine(shiftx + i * (iW * 3) + iW / 2,
                        shifty + j * iH,
                        shiftx + i * (iW * 3),
                        shifty + (j) * iH + iH / 2,
                        p);
                }
            if (j!=Const.NH[Const.HEX]*2-1) {
                canvas.drawLine(shiftx + i * (iW * 3) + iW / 2,
                        shifty + (j + 1) * iH,
                        shiftx + i * (iW * 3),
                        shifty + (j) * iH + iH / 2,
                        p);
            }
            }
        // first line
        for (int i=0; i<Const.NW[Const.HEX]; i++){
            int j=0;
            canvas.drawLine(shiftx + i * (iW * 3) + iW / 2,
                    shifty + j*iH ,
                    shiftx + i * (iW * 3) + iW * 3 / 2,
                    shifty + j*iH ,
                    p);
        }
        drawMyText(score, level,(width/2 + iW *3/2* Const.NW[Const.HEX]) , hShift, iH );

    }


}
