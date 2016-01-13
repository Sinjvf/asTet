package com.sinjvf.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.sinjvf.tetris.awry.DrawingAwry;
import com.sinjvf.tetris.awry.GameScreenAwry;
import com.sinjvf.tetris.awry.MyFiguresAwry;
import com.sinjvf.tetris.hex.DrawingHex;
import com.sinjvf.tetris.hex.GameScreenHex;
import com.sinjvf.tetris.hex.MyFiguresHex;
import com.sinjvf.tetris.standart.DrawingStandart;
import com.sinjvf.tetris.standart.GameScreenStandart;
import com.sinjvf.tetris.standart.MyFiguresStandart;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Sinjvf on 21.02.2015.
 */

/** main game view*/
public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private int width, height;
    private int type, back;
    private GameProperties.Complex complex;
    private MyFigures fCurrent;
    private MyFigures fNext;
    private Drawing draw;
    private GameScreen screen;

    private Canvas canvas;
    private volatile boolean gameOver;
    private Integer score, level;
    private int coef;
    private volatile boolean notPause = true;
    private ListenerGameOver listenerGameOver;
    private ListnerDrawText listenerDrawText;
    private ListnerSetLevel listenerSetLevel;

    private DrawThread drawThread;
    private MischievousFigures mischFig;
    private boolean needReDraw;
    private boolean needLay=false;
    private int numberFalling;

    Context context;

    private ArrayList<Integer> checkingLevel;

    /**
     * constructor
     */
    public Game(Context context, GameProperties gameProperties) {

        super(context);

        Log.d(Const.LOG_TAG, "context CREATED!");
        this.context = context;
        newGame(gameProperties);
    }
    public interface ListenerGameOver {
        void onListenToMain();
    }
    public void setListenerGameOver(ListenerGameOver listenerGameOver){
        this.listenerGameOver = listenerGameOver;}

    public interface ListnerDrawText {
        void onDrawText(int level, int score);
    }
    public void setListenerDrawText(ListnerDrawText listenerDrawText){
        this.listenerDrawText = listenerDrawText;}

    public interface ListnerSetLevel{
        void onSetLevel(int level);
    }
    public void setListenerSetLevel(ListnerSetLevel listenerSetLevel){
        this.listenerSetLevel = listenerSetLevel;}

    public Integer getScore() {
        return score;
    }

    public void newGame(GameProperties gameProperties){

        Log.d(Const.LOG_TAG, "game creating...");
        type = gameProperties.getType();
        back = gameProperties.getBack();
        complex = gameProperties.getComplex();
        numberFalling=0;
        score = 0;
        level =0;
        //simple game
        coef = 1;
        //add for pace
        coef=coef*(1+complex.getPace());
        //add for many figures
        coef=coef+complex.getNumbers();
        gameOver = false;
        checkingLevel = new ArrayList<Integer>();
        checkingLevel.add(0);
        for (int i=0; i<6; i++){
            checkingLevel.add(checkingLevel.get(i)+Const.POINT_FOR_LINE[i]*Const.LEAVE_LEVEL[i]*coef );
        }

        Log.d(Const.LOG_TAG, "simple CREATED!");
        getHolder().addCallback(this);

        Log.d(Const.LOG_TAG, "get Holder!");
        fCurrent = newFig();
        fNext = newFig();
        switch (type){
            case Const.STANDART:
                screen = new GameScreenStandart(Const.NW[type], Const.NH[type]);
                draw = new DrawingStandart(context, back);
                break;
            case Const.AWRY:
                screen = new GameScreenAwry(Const.NW[type],  Const.NH[type]*2-1);
                draw = new DrawingAwry(context, back);
                break;
            case Const.HEX:
                screen = new GameScreenHex(Const.NW[type]*2,  Const.NH[type]*2-1);
                draw = new DrawingHex(context, back);
                break;
        }

        Log.d(Const.LOG_TAG, "new figs and screen!");
        mischFig = new MischievousFigures(complex.getNumbers(), complex.getColorShemes(), false);


        Log.d(Const.LOG_TAG, "GAME CREATED!");
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
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

    public  synchronized  boolean moveFigure(MyFigures fig, int shiftX, int shiftY, int shiftMode) {
        int stay;
        if (!needLay) {
            if ((stay = screen.canMoveOrRotate(fig, shiftX, shiftY, shiftMode)) == -1) {
                fig.setCurrentMode((fig.getCurrentMode() + shiftMode) % fig.getModes());
                fig.move(shiftX, shiftY);
                needReDraw = true;
                return true;
            } else if (stay == 2) {
                gameOver = true;
                screen.fillFigureSpace(fig);
                if (listenerGameOver != null)
                    listenerGameOver.onListenToMain();
            } else if (stay == 1  ) {
                screen.fillFigureSpace(fig);
                score += screen.deleteLineIfNesessary() * Const.POINT_FOR_LINE[level] * coef;
                checkAndSetLevel();
                needLay = true;
                numberFalling=numberFalling+1;


            }
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
    private MyFigures newFig(){
        MyFigures fNext;
        switch (type){
            case Const.STANDART:
                fNext = MyFiguresStandart.newFigure();
                break;
            case Const.AWRY:
                fNext = MyFiguresAwry.newFigure();
                break;
            default:
                fNext = MyFiguresHex.newFigure();
                break;
        }
        return fNext;
    }

    class DrawThread extends Thread {
        private long prevTime;
        private long now;
        private long elapsedTime;
        private long prevTime2;
        private long now2;
        private long elapsedTime2;
        private volatile boolean running = false;
        private SurfaceHolder surfaceHolder;


        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            prevTime = System.currentTimeMillis();
            prevTime2 = prevTime;

        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        public void reDraw(){
            try {
                canvas = surfaceHolder.lockCanvas(null);


            if (canvas != null){
                draw.setCanvas(canvas);
                draw.setScreen(screen);
                draw.drawBackgroung();
                draw.drawFullScreen();
                for (int i=0; i<complex.getNumbers();i++) {
                    draw.drawFigure(mischFig.get(i));
                }

                draw.drawFigure(fCurrent);
                draw.drawNextFigure(fNext);
                draw.drawGrid(score, level);
                if (gameOver){
                    notPause = false;
                    running = false;
                }
                listenerDrawText.onDrawText(level, score);
            }
            else {running = false;}

            needReDraw = false;
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

        }
        @Override
        public void run() {
            width = getWidth();
            height = getHeight();
            draw.setHW(height, width);
            while (running) {
                while (notPause) {
                    now = System.currentTimeMillis();
                    now2 = now;
                    elapsedTime = now - prevTime;
                    elapsedTime2 = now2-prevTime2;
               //     canvas = null;
                    if (elapsedTime2 > 50) {
                        if (needReDraw)
                            reDraw();
                        prevTime2 = now2;}

                    if (elapsedTime > Const.PACE[level]/Const.COMPLEX_PACE_COEFICIENT[complex.getPace()]) {

                        if (moveFigure(fCurrent, 0, 1, 0)) {
                            prevTime = now;
                        } else
                            if (!gameOver) {
                                fCurrent = fNext;
                                fNext = newFig();
                            }
                        needLay=false;
                        mischFig.mischMoving();
                    }
                }
            }

        }
    }


    public class MischievousFigures {
        private int numb;
        private int []colors;
        private ArrayList<MyFigures> misFig;
        private int mX, mY=1, mR;
        private boolean real;
        private Random random;

        public MischievousFigures(int numb, int []colors, boolean real){
            random = new Random(System.currentTimeMillis());
            this.numb = numb;
            this.colors = colors;
            misFig= new ArrayList<>();
            for (int i=0; i<numb;i++){
               newMischFig(i);
            }
            this.real=real;
        }
        public MyFigures get(int i){
            return misFig.get(i);
        }

        private  void newMischFig(int i){

            if(i>=misFig.size())
                misFig.add(newFig());
            else
                misFig.set(i, newFig());
            misFig.get(i).setMischievous(colors[i]);
            mischFistMove(i);
        }

        private void mischFistMove(int i){

            int mX, mY=1;
            mX =Math.abs(random.nextInt()%screen.nI);
            while((screen.canMoveOrRotate(misFig.get(i), mX - misFig.get(i).x, mY, 0))!=-1){
                mX =Math.abs(random.nextInt()%screen.nI);
            }
            misFig.get(i).move(mX - misFig.get(i).x, -2 * i);

        }
        public void mischMoving(){
            int stay;


            for (int i=0; i<numb;i++) {
                mX = Math.abs(random.nextInt() % 3) - 1;
                mR = Math.abs(random.nextInt() % misFig.get(i).getModes());
                if (real) {
                    moveFigure(misFig.get(i),mX, mY, mR);
                }
                else {
                    if ((stay = screen.canMoveOrRotate(misFig.get(i), mX, mY, mR)) == -1) {
                        misFig.get(i).setCurrentMode(
                                (misFig.get(i).getCurrentMode() + mR) % misFig.get(i).getModes());
                        misFig.get(i).move(mX, mY);
                    } else if (stay == 0) {
                        misFig.get(i).move(0, mY);
                    } else if (!gameOver) {
                        newMischFig(i);
                    }
                }
            }
        }
    }

}
