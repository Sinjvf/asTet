package com.sinjvf.tetris;

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
import android.widget.TextView;

import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

/**
 * Created by Sinjvf on 09.03.2015.
 */
public class GameActivity extends BaseGameActivity implements View.OnTouchListener, View.OnClickListener,
                                                            Game.ListenerGameOver, GestureDetector.OnGestureListener,
                                                            Game.ListnerDrawText, Game.ListnerSetLevel {

    private Game game;
   // private final Activity act =this;

    private ImageButton buttonPause, buttonRight, buttonLeft, buttonRotate, buttonRotateBack,buttonDown;
    private Button  buttonGameOver, buttonExit;

    private TextView twScore, twLevel;
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
    private GameProperties gameProperties;
    private Intent intent;
    private int lastLevel =1;
    private String levels_id[]={getString(R.string.achievement_2_level),
            getString(R.string.achievement_3_level),
            getString(R.string.achievement_4_level),
            getString(R.string.achievement_5_level),
            getString(R.string.achievement_6_level),
            getString(R.string.achievement_7_level)};

    private String event_id[]={getString(R.string.event_2_level),
            getString(R.string.event_3_level),
            getString(R.string.event_4_level),
            getString(R.string.event_5_level),
            getString(R.string.event_6_level),
            getString(R.string.event_7_level)};
    
    @Override
    public void onSignInSucceeded() {
    }

    @Override
    public void onSignInFailed() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main_layout);
        Log.d(Const.LOG_TAG, "game activity!");
        gameProperties = getIntent().getParcelableExtra(Const.PROPERTIES);
        game = new Game(this, gameProperties);

        game.setListenerGameOver(this);
        game.setListenerDrawText(this);
   //     super.onCreate(savedInstanceState);
        surface = (LinearLayout)findViewById(R.id.linearLayout1);
        surface.addView(game);
        surface.setOnTouchListener(this);


        buttonRight = (ImageButton)findViewById(R.id.button_right);
        buttonLeft = (ImageButton)findViewById(R.id.button_left);
        buttonRotate = (ImageButton)findViewById(R.id.button_rotate);
        buttonRotateBack = (ImageButton)findViewById(R.id.button_rotate_back);
        buttonDown = (ImageButton)findViewById(R.id.button_down);
        buttonPause = (ImageButton)findViewById(R.id.button_pause);
        buttonGameOver = (Button)findViewById(R.id.button_game_over);
        buttonExit = (Button)findViewById(R.id.button_exit);
        twLevel = (TextView)findViewById(R.id.level_id);
        twScore = (TextView)findViewById(R.id.score_id);
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
        db= new DBUser(this, gameProperties.getType());

        Const.Next = getString(R.string.next);
        Const.Level = getString(R.string.level);
        Const.Score = getString(R.string.my_score);

Log.d(Const.LOG_TAG, "OnCreate finish");
    }



    private void checkResuts(){
        int score =game.getScore();
        game.stop();
      /*  if (getApiClient() != null && getApiClient().isConnected()) {
            String leaderboard_id;
            switch (gameProperties.getType()){
                case Const.STANDART:
                    leaderboard_id = getString(R.string.leaderboard_standart);
                    break;
                case Const.AWRY:
                    leaderboard_id = getString(R.string.leaderboard_awry);
                    break;
                default:
                    leaderboard_id = getString(R.string.leaderboard_hex);
                    break;
            }
            Games.Leaderboards.submitScore(getApiClient(), leaderboard_id, score);
        } else {
            Log.d(Const.LOG_TAG, "LOGIN ERROR!");
        }
       */ intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
    @Override
    public void onDrawText(int level,int score){
    final int l=level, s=score;
        surface.getHandler().post(
                new Runnable() {
                    public void run() {
                        twLevel.setText(new Integer(l).toString());
                        twScore.setText(new Integer(s).toString());
                    }
                });
    }
    @Override
    public void onListenToMain(){
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
    @Override
    public void onSetLevel(int level){
/**        if (getApiClient() != null && getApiClient().isConnected()) {
            if (level > lastLevel) {
                String this_level_id = levels_id[level-2];
                Games.Events.increment(getApiClient(), this_level_id, 1);
            //    Games.Achievements.unlock(getApiClient(), this_level_id);
         //       res.unlockAchievementGPGS("HgkIr62KmoQJEAIQGA");
            }
        } else {
            Log.d(Const.LOG_TAG, "LOGIN ERROR!");
        }/**/
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
                inActivateButtons(pause);
                game.setNotPause(pause);
                break;
            case R.id.button_exit:
                checkResuts();
                break;
            case R.id.button_game_over:
                checkResuts();
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

                    switch (typeOfMotion) {
                        case RIGHT_MOTION:
                            game.moveFigure(game.getFCurrent(), 1, 0, 0);
                            break;
                        case LEFT_MOTION:
                            game.moveFigure(game.getFCurrent(), -1, 0, 0);
                            break;
                        case DOWN_MOTION:
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
    private void inActivateButtons(boolean pause){
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
        inActivateButtons(pause);
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
        }
        return true;
    }
    @Override
    public void onBackPressed(){
        checkResuts();
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
        inActivateButtons(true);

        inActivateButtons(false);
        //  game.setNotPause(true);
        //  Log.d(Const.LOG_TAG, "RESUME");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //    game.setNotPause(false);

        inActivateButtons(false);
        //  Log.d(Const.LOG_TAG, "PAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //   Log.d(Const.LOG_TAG, "STOP");
    }
}