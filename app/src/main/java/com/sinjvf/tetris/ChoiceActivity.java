package com.sinjvf.tetris;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

public class ChoiceActivity extends Activity implements View.OnClickListener {
    private Button buttonStandart, buttonAwry, buttonHex,
            buttonBack;
    private Intent intent;
    private int nextLayout;
    private GameProperties gameProperties;


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
        nextLayout = intent.getIntExtra(Const.LAYOUT, Const.STANDART);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                    R.drawable.logo_small);
            ActivityManager.TaskDescription taskDesc =
                    new ActivityManager.TaskDescription(getString(R.string.app_name),
                            icon, ContextCompat.getColor(this, R.color.dark_primary));
            this.setTaskDescription(taskDesc);
        }

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void newGame(int type, int back, GameProperties.Complex complex){
        intent = new Intent(this, GameActivity.class);
        GameProperties gameProperties = new GameProperties(type, back, complex);
        intent.putExtra(Const.PROPERTIES, gameProperties);
        startActivity(intent);
        finish();
    }
    private void printRes(int type){
        intent = new Intent(this, SignInActivity.class);
        ApiProperties prop = new ApiProperties(type, Const.SCORE_FOR_RESULTS);
        intent.putExtra(Const.SIGN_IN, prop);
        startActivity(intent);
        this.finish();
    }
        @Override
        public void onClick(View v) {
            int type, back;
            int numb, colors[], pace, prevent;
            GameProperties.Complex complex;
            SharedPreferences sPref;
            sPref = getSharedPreferences(Const.SETTINGS, MODE_PRIVATE);
            back = sPref.getInt(Const.BACK, Const.DEFAULT_BACK);
            numb = sPref.getInt(Const.COMPLEX_NUMB, Const.DEFAULT_COMPLEX_NUMB);
            pace = sPref.getInt(Const.COMPLEX_PACE, Const.DEFAULT_COMPLEX_PACE);
            prevent = sPref.getInt(Const.COMPLEX_PREVENT, Const.DEFAULT_COMPLEX_PREVENT);
            colors = new int [Const.MAX_FIG];
            for (int i=0;i<numb;i++){
                colors[i]=sPref.getInt(Const.COMPLEX_COLORS[i], Const.DEFAULT_COMPLEX_COLOR);
            }
            complex = new GameProperties.Complex(numb, colors, pace, prevent);


                   switch (v.getId()) {
                case R.id.button_standart:
                    type = Const.STANDART;
                    if(nextLayout==Const.NEW_GAME_LAYOUT) {
                        newGame(type, back, complex);
                    }
                    else{printRes(type);
                    }
                    /*
                    if (getApiClient() != null && getApiClient().isConnected()) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(),
                                getString(R.string.leaderboard_standart)), Const.STANDART);}
*/
                    break;
                case R.id.button_awry:
                        type = Const.AWRY;
                        if(nextLayout==Const.NEW_GAME_LAYOUT) {
                            newGame(type, back, complex);
                        }
                        else{printRes(type);
                        }

                    break;
                case R.id.button_hex:
                    type = Const.HEX;
                    if(nextLayout==Const.NEW_GAME_LAYOUT) {
                        newGame(type, back, complex);
                    }
                    else{printRes(type);
                    }

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