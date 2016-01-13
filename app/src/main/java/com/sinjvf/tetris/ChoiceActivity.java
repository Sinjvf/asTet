package com.sinjvf.tetris;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

public class ChoiceActivity extends BaseGameActivity implements View.OnClickListener {
    private Button buttonStandart, buttonAwry, buttonHex,
            buttonBack;
    private Intent intent;
    private int nextLayout;
    private GameProperties gameProperties;

    @Override
    public void onSignInSucceeded() {
    }

    @Override
    public void onSignInFailed() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new Game(this));
        setContentView(R.layout.choice_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        buttonStandart = (Button)findViewById(R.id.button_standart);
        buttonAwry = (Button)findViewById(R.id.button_awry);
        buttonHex = (Button)findViewById(R.id.button_hex);
        buttonBack = (Button)findViewById(R.id.button_back);

        buttonStandart.setOnClickListener(this);
        buttonAwry.setOnClickListener(this);
        buttonHex.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        Intent intent = getIntent();
        nextLayout = intent.getIntExtra(Const.LAYOUT, 0);

    }
        @Override
        public void onClick(View v) {
            int type, back;
            int numb, colors[], pace;
            GameProperties.Complex complex;
            SharedPreferences sPref;
            sPref = getSharedPreferences(Const.SETTINGS, MODE_PRIVATE);
            back = sPref.getInt(Const.BACK, Const.DEFAULT_BACK);
            numb = sPref.getInt(Const.COMPLEX_NUMB, Const.DEFAULT_COMPLEX_NUMB);
            pace = sPref.getInt(Const.COMPLEX_PACE, Const.DEFAULT_COMPLEX_PACE);
            colors = new int [Const.MAX_FIG];
            for (int i=0;i<numb;i++){
                colors[i]=sPref.getInt(Const.COMPLEX_COLORS[i], Const.DEFAULT_COMPLEX_COLOR);
            }
            complex = new GameProperties.Complex(numb, colors, pace);

          //  Log.d(Const.LOG_TAG, "load complex. numb=" + complex.getNumbers()+", pace=" +complex.getPace()+
          //  ", c0="+complex.getColorShemes()[0]+", c1="+complex.getColorShemes()[1]+", c2="+complex.getColorShemes()[2]);

                   switch (v.getId()) {
                case R.id.button_standart:
                    if(nextLayout==Const.NEW_GAME_LAYOUT) {
                        intent = new Intent(this, AboutActivity.class);
                        /*
                        type = Const.STANDART;
                        gameProperties = new GameProperties(type, back, complex);
                        intent.putExtra(Const.PROPERTIES, gameProperties);
                        */
                        startActivity(intent);

                        Log.d(Const.LOG_TAG, "start");
                        finish();
                    }
                    else

                    if (getApiClient() != null && getApiClient().isConnected()) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(),
                                getString(R.string.leaderboard_standart)), Const.STANDART);}

                    break;
                case R.id.button_awry:
                    if(nextLayout==Const.NEW_GAME_LAYOUT) {
                        intent = new Intent(this, AboutAppActivity.class);
                        type = Const.AWRY;
                        gameProperties = new GameProperties(type, back, complex);
                        intent.putExtra(Const.PROPERTIES, gameProperties);
                        startActivity(intent);
                        finish();
                    }
                    else

                    if (getApiClient() != null && getApiClient().isConnected()) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(),
                                getString(R.string.leaderboard_awry)), Const.AWRY);}
                    break;
                case R.id.button_hex:
                    if(nextLayout==Const.NEW_GAME_LAYOUT) {
                        intent = new Intent(this, ComplexityChoiceActivity.class);
                        type = Const.HEX;
                        gameProperties = new GameProperties(type, back, complex);
                        intent.putExtra(Const.PROPERTIES, gameProperties);
                        startActivity(intent);
                        finish();
                    }
                    else

                    if (getApiClient() != null && getApiClient().isConnected()) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(),
                                getString(R.string.leaderboard_hex)), Const.HEX);}

                    break;
                case R.id.button_back:
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
        }
    }

    @Override
    public void onBackPressed(){
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Const.STANDART: //comm
                    break;
                case Const.AWRY:
                    break;
                case Const.HEX:
                    break; //cgfgfgf
            }
        }


        intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
         /**/
    }
}