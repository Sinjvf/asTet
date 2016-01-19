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
        coef=coef+complex.getNumbers()*complex.getPrevent();
        gameOver = false;
        checkingLevel = new ArrayList<Integer>();
        checkingLevel.add(0);
        for (int i=0; i<6; i++){
            checkingLevel.add(checkingLevel.get(i)+Const.POINT_FOR_LINE[i]*Const.LEAVE_LEVEL[i]*coef );
        }
        getHolder().addCallback(this);
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
        mischFig = new MischievousFigures(complex.getNumbers(), complex.getColorSchemes(), complex.getPrevent());
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

    public  synchronized  boolean moveFigure(MyFigures fig, int shiftX, int shiftY, int shiftMode, int type_fig) {
        int stay, stay2;
        if (!needLay) {
            stay=screen.canMoveOrRotate(fig, shiftX, shiftY, shiftMode);
            if (stay == -1) {
                stay2=screen.canMoveOrRotateWithOther(fCurrent, mischFig, type_fig, shiftX, shiftY, shiftMode);
                if (stay2==2) {
                    layFig(fig);
                    Log.d(Const.LOG_TAG, "2222");
                }
                if((stay2)==-1|| (type_fig==Const.REAL_FIG_TIME && stay2!=2)) {

                    fig.setCurrentMode((fig.getCurrentMode() + shiftMode) % fig.getModes());
                    fig.move(shiftX, shiftY);
                    needReDraw = true;
                    return true;
                }
                else
                    stay=0;
            }
            if (stay == 2) {
                gameOver = true;
                screen.fillFigureSpace(fig);
                if (listenerGameOver != null)
                    listenerGameOver.onListenToMain();
            }
            if (stay == 1  ) {
                layFig(fig);
            }
        }
        return false;
    }


    private void layFig(MyFigures fig){
        Log.d(Const.LOG_TAG, "LAY!");
        screen.fillFigureSpace(fig);
        score += screen.deleteLineIfNesessary() * Const.POINT_FOR_LINE[level] * coef;
        checkAndSetLevel();
        if (fig.isReal()) {
            needLay = true;
            numberFalling = numberFalling + 1;
            Log.d(Const.LOG_TAG, "falling="+numberFalling);
            if (mischFig.numb>0)
                if ( numberFalling>=Const.PERIOD/mischFig.numb)
                {
                    mischFig.last = (mischFig.last+1)%mischFig.numb;
                    Log.d(Const.LOG_TAG, "last="+mischFig.last);
                    mischFig.visible[mischFig.last] = true;

                    mischFig.mischFistMove(mischFig.last);
                    numberFalling=0;
                }
        }else
        if(mischFig.prevent){
            mischFig.visible[mischFig.last]=false;
            mischFig.newMischFig(mischFig.last);
        }
        ///WARNING! DANGEROUS CRUTCH!
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

                        if (moveFigure(fCurrent, 0, 1, 0, Const.REAL_FIG_TIME)) {
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
        private boolean prevent;
        private Random random;
        private boolean []visible;
        private int last=-1;

        public MischievousFigures(int numb, int []colors, int prev){
            prevent = (prev==Const.SWITCH_DISTRACT)?false:true;
            Log.d(Const.LOG_TAG, "prev="+prevent);
            random = new Random(System.currentTimeMillis());
            visible = new boolean[numb];
            for (int i=0; i<numb; i++){
                visible[i]=!prevent;
            }
            this.numb = numb;
            this.colors = colors;
            misFig= new ArrayList<>();
            for (int i=0; i<numb;i++){
               newMischFig(i);
            }
        }

        private  void newMischFig(int i){

            if(i>=misFig.size())
                misFig.add(newFig());
            else
                misFig.set(i, newFig());
            misFig.get(i).setMischievous(colors[i]);
            misFig.get(i).setReal(false);
            if (visible[i])
                 mischFistMove(i);
        }

        private void mischFistMove(int i) {

            Log.d(Const.LOG_TAG, "first move");
            int mX, mY = 1;
            mX = Math.abs(random.nextInt() % screen.nI);
            if (!prevent)
                mY = -2 * i;
            while (true) {
                while ((screen.canMoveOrRotate(misFig.get(i), mX - misFig.get(i).x, 0, 0)) != -1) {
                    mX = Math.abs(random.nextInt() % screen.nI);
                    Log.d(Const.LOG_TAG, "mx="+mX);
                }
               if (prevent) {
                   if (screen.canMoveOrRotateWithOther(fCurrent, this, i + 2, mX - misFig.get(i).x, 0, 0) == -1) {
                       misFig.get(i).move(mX - misFig.get(i).x, 0);
                       break;
                   }
               }
                else {
                   misFig.get(i).move(mX - misFig.get(i).x, 0);
                   break;
               }

                Log.d(Const.LOG_TAG, "true!");
            }
            misFig.get(i).move(0, mY);
        }
        public void mischMoving(){
            for (int i=0; i<numb;i++) {
                mX = Math.abs(random.nextInt() % 3) - 1;
                mR = Math.abs(random.nextInt() % misFig.get(i).getModes());
                if(visible[i]) {

               //     Log.d(Const.LOG_TAG, "moving");
                    if (prevent) {
                        moveFigure(misFig.get(i), mX, 0, 0, i+2);
                        moveFigure(misFig.get(i), 0, 0, mR, i+2);
                        moveFigure(misFig.get(i), 0, mY, 0, i+2);
                    } else {
                        singleMoving(mX,0, 0,  i);
                        singleMoving(0,0, mR,  i);
                        singleMoving(0,mY, 0,  i);
                    }
                }
            }

        }
        private void singleMoving(int x, int y, int r, int i){
            int stay;
            if ((stay = screen.canMoveOrRotate(misFig.get(i), x, y, r)) == -1) {
                misFig.get(i).setCurrentMode(
                        (misFig.get(i).getCurrentMode() + r) % misFig.get(i).getModes());
                misFig.get(i).move(x, y);
            } else if (stay == 0) {
                misFig.get(i).move(0, y);
            } else if (!gameOver) {
                newMischFig(i);
            }
        }
        public MyFigures get(int i){
            return misFig.get(i);
        }
        public boolean isPrevent(){return prevent;}
        public int getNumb(){return numb;}
    }

}
