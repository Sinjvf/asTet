package com.example.tetris;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by Sinjvf on 09.03.2015.
 */
public class GameActivity extends Activity implements View.OnTouchListener, View.OnClickListener, ListenerToMain, GestureDetector.OnGestureListener{

    private Game game;
    private final Activity act =this;

    private ImageButton buttonPause, buttonRight, buttonLeft, buttonRotate, buttonRotateBack,buttonDown;
    private Button  buttonGameOver, buttonExit;

    private Float currentX, currentY;
    private Float previosX, previosY;
    private Float motionX, motionY;
    private final static int RIGHT_MOTION = 1;
    private final static int LEFT_MOTION  = -1;
    private final static int DOWN_MOTION  = -2;
    private final static int NOTHING  = 0;
    private int widht;
    private int height;
    private int typeOfMotion = 0;
    private GestureDetector mDetector;
    private LinearLayout surface;
    private DBUser db;
    private int type;
    //private TextView textView;
    //private Handler handler;





    public void onCreate(Bundle savedInstanceState) {
     //   Log.d(Const.LOG_TAG, "create " + this.toString());
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        game = new Game(this, type);
        game.setListenerToMain(this);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.game_layout);
        surface = (LinearLayout)findViewById(R.id.linearLayout1);
        surface.addView(game);
        surface.setOnTouchListener(this);
       // handler = surface.getHandler();


        buttonRight = (ImageButton)findViewById(R.id.button_right);
        buttonLeft = (ImageButton)findViewById(R.id.button_left);
        buttonRotate = (ImageButton)findViewById(R.id.button_rotate);
        buttonRotateBack = (ImageButton)findViewById(R.id.button_rotate_back);
        buttonDown = (ImageButton)findViewById(R.id.button_down);
        buttonPause = (ImageButton)findViewById(R.id.button_pause);
        buttonGameOver = (Button)findViewById(R.id.button_game_over);
        buttonExit = (Button)findViewById(R.id.button_exit);
        buttonRight.setOnClickListener(this);
        buttonLeft.setOnClickListener(this);
        buttonRotate.setOnClickListener(this);
        buttonRotateBack.setOnClickListener(this);
        buttonDown.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        buttonGameOver.setOnClickListener(this);
        buttonGameOver.setClickable(false);
        buttonGameOver.setVisibility(View.INVISIBLE);
        buttonExit.setOnClickListener(this);
        mDetector = new GestureDetector(this,this);



       // test.setClickable(false);
      //  test.setVisibility(View.INVISIBLE);


        db= new DBUser(this, type);
       // textView = new TextView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    //    Log.d(Const.LOG_TAG, "START");
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.setNotPause(true);
      //  Log.d(Const.LOG_TAG, "RESUME");
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.setNotPause(false);
      //  Log.d(Const.LOG_TAG, "PAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
     //   Log.d(Const.LOG_TAG, "STOP");
    }

    private void checkResuts(){
        int score =game.getScore();
        game.stop();
        Log.d(Const.LOG_TAG, "Game Over! " + score);
        if (score>0) {
            if (db.isPrintedResult(score)) {
                Log.d(Const.LOG_TAG, "is Printed res");
                Intent intent = new Intent(this, SaveResultsActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        }
        //  finish();
    }

    @Override
    public void onListenToMain(){
        Log.d(Const.LOG_TAG, "Show game over");
        //     checkResuts();
        //  finish();

        surface.getHandler().post(
                new Runnable() {
                    public void run() {

                        buttonGameOver.setClickable(true);
                        buttonGameOver.setVisibility(View.VISIBLE);
                        buttonPause.setClickable(false);
                        buttonDown.setClickable(false);
                        buttonLeft.setClickable(false);
                        buttonRight.setClickable(false);
                        buttonRotate.setClickable(false);

                    }
                }
        );
    }
    private void inactivateButtons(boolean pause){
        buttonRotate.setClickable(pause);
        buttonRotateBack.setClickable(pause);
        buttonRight.setClickable(pause);
        buttonLeft.setClickable(pause);
        buttonDown.setClickable(pause);
        buttonRotate.setActivated(pause);
        buttonRotateBack.setActivated(pause);
        buttonRight.setActivated(pause);
        buttonLeft.setActivated(pause);
        buttonDown.setActivated(pause);
        game.setNotPause(pause);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_left:
                game.moveFigure(game.getFCurrent(), -1, 0, 0);
                break;
            case R.id.button_right:
                game.moveFigure(game.getFCurrent(), 1, 0, 0);
                break;
            case R.id.button_rotate:
                game.moveFigure(game.getFCurrent(), 0, 0, 1);
                break;
            case R.id.button_rotate_back:
                game.moveFigure(game.getFCurrent(), 0, 0, -1);
                break;
            case R.id.button_down:
                game.moveFigure(game.getFCurrent(), 0, 1, 0);
                game.moveFigure(game.getFCurrent(), 0, 1, 0);
                break;
            case R.id.button_pause:
                boolean pause = !game.getNotPause();
                inactivateButtons(pause);
                game.setNotPause(pause);
                break;
            case R.id.button_exit:
                checkResuts();
                this.finish();
                break;
            case R.id.button_game_over:
                checkResuts();
                this.finish();
                break;
        }

    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        widht = game.getWidth();
        height = game.getHeight();
        currentX = event.getX();
        currentY = event.getY();

        boolean notPause = game.getNotPause();
        if (notPause) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: // нажатие
                    previosX = currentX;
                    previosY = currentY;
                    break;
                case MotionEvent.ACTION_MOVE: // движение
                    motionX = currentX - previosX;
                    motionY = currentY - previosY;
                    if (motionY > height / Const.SENSIBILITY) {
                        typeOfMotion = DOWN_MOTION;
                        previosY = currentY;
                    } else if (motionX < -widht / Const.SENSIBILITY) {
                        typeOfMotion = LEFT_MOTION;
                        previosX = currentX;
                    } else if (motionX > widht / Const.SENSIBILITY) {
                        typeOfMotion = RIGHT_MOTION;
                        previosX = currentX;
                    } else typeOfMotion = NOTHING;


                    Log.d(Const.LOG_TAG + 11, "widht=" + widht + ", height=" + height);
                    switch (typeOfMotion) {
                        case RIGHT_MOTION:
                            Log.d(Const.LOG_TAG + 11, "right");
                            game.moveFigure(game.getFCurrent(), 1, 0, 0);
                            break;
                        case LEFT_MOTION:
                            Log.d(Const.LOG_TAG + 11, "left");
                            game.moveFigure(game.getFCurrent(), -1, 0, 0);
                            break;
                        case DOWN_MOTION:
                            Log.d(Const.LOG_TAG + 11, "down");
                            game.moveFigure(game.getFCurrent(), 0, 1, 0);
                            break;
                    }
                    break;
                case MotionEvent.ACTION_UP: // отпускание

                    break;
            }
        }
        mDetector.onTouchEvent(event);
        return true;
        }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {

        boolean pause = !game.getNotPause();
        inactivateButtons(pause);
        game.setNotPause(pause);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        if (buttonRotate.isClickable()) {
            game.moveFigure(game.getFCurrent(), 0, 0, 1);
            Log.d(Const.LOG_TAG, "single! ");
        }
        return true;
    }


}