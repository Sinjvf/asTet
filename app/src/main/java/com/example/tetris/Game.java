package com.example.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.tetris.awry.DrawingAwry;
import com.example.tetris.awry.GameScreenAwry;
import com.example.tetris.awry.MyFiguresAwry;
import com.example.tetris.hex.DrawingHex;
import com.example.tetris.hex.GameScreenHex;
import com.example.tetris.hex.MyFiguresHex;
import com.example.tetris.standart.DrawingStandart;
import com.example.tetris.standart.GameScreenStandart;
import com.example.tetris.standart.MyFiguresStandart;

import java.util.ArrayList;


/**
 * Created by Sinjvf on 21.02.2015.
 */

/** main game view*/
public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private int width, height;
    private int type, back, complex;
    private MyFigures fCurrent;
    private MyFigures fNext;
    private Drawing draw;
    private GameScreen screen;

    private Canvas canvas;
    private volatile boolean gameOver;
    private Integer score, level;
    private volatile boolean notPause = true;
    private ListenerToMain listenerToMain;
    private DrawThread drawThread;

    Context context;

    private ArrayList<Integer> checkingLevel;

    /**
     * constructor
     */
    public Game(Context context, GameProperties gameProperties) {
        super(context);
        this.context = context;
        newGame(gameProperties);
    }

    void setListenerToMain(ListenerToMain listenerToMain){
        this.listenerToMain = listenerToMain;}

    public Integer getScore() {
        return score;
    }

    public void newGame(GameProperties gameProperties){;
        type = gameProperties.getType();
        back = gameProperties.getBack();
        complex = gameProperties.getComplex();

        Log.d(Const.LOG_TAG, "------NEW GAME---------");
        Log.d(Const.LOG_TAG, "type="+ type+ ", back="+back+", complex="+complex);
        Paint p = new Paint();
        score = 0;
        level =0;
        gameOver = false;
        checkingLevel = new ArrayList<Integer>();
        checkingLevel.add(0);
        for (int i=0; i<6; i++){
            checkingLevel.add(checkingLevel.get(i)+Const.POINT_FOR_LINE[i]*Const.LEAVE_LEVEL[i] );
        }
        getHolder().addCallback(this);
        switch (type){
            case Const.STANDART:
                screen = new GameScreenStandart(Const.NW[type], Const.NH[type]);
                fCurrent = MyFiguresStandart.newFigure();
                fNext = MyFiguresStandart.newFigure();
                draw = new DrawingStandart(context);
                break;
            case Const.AWRY:
                screen = new GameScreenAwry(Const.NW[type],  Const.NH[type]*2-1);
                fCurrent = MyFiguresAwry.newFigure();
                fNext = MyFiguresAwry.newFigure();
                draw = new DrawingAwry(context);
                break;
            case Const.HEX:
                fCurrent = MyFiguresHex.newFigure();
                fNext = MyFiguresHex.newFigure();

                screen = new GameScreenHex(Const.NW[type]*2,  Const.NH[type]*2-1);
                draw = new DrawingHex(context);
                break;
        }
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
   //     Log.d(Const.LOG_TAG, "----CHANGE");

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
     //   Log.d(Const.LOG_TAG, "-----CREATE");
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stop();
    }

    public  synchronized void stop() {
        this.setNotPause(false);
        drawThread.setRunning(false);

    }

    public synchronized void setNotPause(boolean pause)
    {
        notPause = pause;
    }
    public boolean getNotPause()
    {
        return notPause;
    }
    public  synchronized  boolean moveFigure(MyFigures fig, int shiftX, int shiftY, int shift_mode) {
        int stay;

          Log.d("myLogs", " CHECK MOVE");
        if ((stay = screen.canMoveOrRotate(fig, shiftX, shiftY, shift_mode))==-1) {
            fig.setCurrentMode((fig.getCurrentMode()+shift_mode)%fig.getModes());
            Log.d("myLogs", " MOVE");
            fig.move(shiftX,  shiftY);
            return true;
        } else if (stay == 2){
                    gameOver = true;
                    screen.fillFigureSpace(fig);
                    if (listenerToMain != null)
                        listenerToMain.onListenToMain();
                }
                else if (stay==1){
                    screen.fillFigureSpace(fig);
                    score+=screen.deleteLineIfNesessary()*Const.POINT_FOR_LINE[level];
                    checkAndSetLevel();
                }
            return false;
    }

    public MyFigures getFCurrent() {
        return fCurrent;
    }


    private synchronized void checkAndSetLevel(){
        while ((level <7) && (score>=checkingLevel.get(level+1))){
            level++;
        }


    }


    class DrawThread extends Thread {
        private long prevTime;
        private long now;
        private long elapsedTime;
        private volatile boolean running = false;
        private SurfaceHolder surfaceHolder;


        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            prevTime = System.currentTimeMillis();

        }

        public void setRunning(boolean running) {
            this.running = running;
        }


        @Override
        public void run() {
            width = getWidth();
            height = getHeight();
            draw.setHW(height, width);
          //  Log.d("myLogs", "WIDGH=" + width + "   HEIGHT=" + height);
            while (running) {
                while (notPause) {
                    now = System.currentTimeMillis();
                    elapsedTime = now - prevTime;
                    canvas = null;
                    if (elapsedTime > Const.PACE[level]) {
                        if (moveFigure(fCurrent, 0, 1, 0)) {
                            prevTime = now;
                        } else
                            if (!gameOver) {
                                fCurrent = fNext;
                                switch (type){
                                    case Const.STANDART:
                                        fNext = MyFiguresStandart.newFigure();
                                        break;
                                    case Const.AWRY:
                                        fNext = MyFiguresAwry.newFigure();
                                        break;
                                    case Const.HEX:
                                        fNext = MyFiguresHex.newFigure();
                                        break;
                                }
                            }
                        try {
                            canvas = surfaceHolder.lockCanvas(null);
                            if (canvas != null){
                                draw.setCanvas(canvas);
                                draw.setScreen(screen);
                                draw.drawBackgroung(back);
                                draw.drawFullScreen();
                                draw.drawFigure(fCurrent);
                                draw.drawNextFigure(fNext);
                                draw.drawGrid(score, level);
                                if (gameOver){
                                    notPause = false;
                                    running = false;
                                    }
                            }
                            else {running = false;}
                        } finally {
                            if (canvas != null) {
                                surfaceHolder.unlockCanvasAndPost(canvas);
                            }
                        }
                    }
                }
            }

        }
    }

}
