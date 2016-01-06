package com.example.tetris;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
        buttonBack = (Button)findViewById(R.id.button_back1);

        buttonStandart.setOnClickListener(this);
        buttonAwry.setOnClickListener(this);
        buttonHex.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        Intent intent = getIntent();
        nextLayout = intent.getIntExtra("layout", 0);

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

            Log.d(Const.LOG_TAG, "load complex. numb=" + complex.getNumbers()+", pace=" +complex.getPace()+
            ", c0="+complex.getColorShemes()[0]+", c1="+complex.getColorShemes()[1]+", c2="+complex.getColorShemes()[2]);
                   switch (v.getId()) {
                case R.id.button_standart:
                    intent = (nextLayout==Const.NEW_GAME_LAYOUT)? new Intent(this, GameActivity.class)
                            :new Intent(this, PrintResultsActivity.class);
                    type = Const.STANDART;
                    gameProperties = new GameProperties(type, back, complex);
                    intent.putExtra(Const.PROPERTIES, gameProperties);
                    startActivity(intent);
                //    finish();
                    break;
                case R.id.button_awry:
                    intent = (nextLayout==Const.NEW_GAME_LAYOUT)? new Intent(this, GameActivity.class)
                            :new Intent(this, PrintResultsActivity.class);
                    type = Const.AWRY;
                    gameProperties = new GameProperties(type, back, complex);
                    intent.putExtra(Const.PROPERTIES, gameProperties);
                    startActivity(intent);
                //    finish();
                    break;
                case R.id.button_hex:
                    intent = (nextLayout==Const.NEW_GAME_LAYOUT)? new Intent(this, GameActivity.class)
                            :new Intent(this, PrintResultsActivity.class);
                    type = Const.HEX;
                    gameProperties = new GameProperties(type, back, complex);
                    intent.putExtra(Const.PROPERTIES, gameProperties);
                    startActivity(intent);
                //    finish();
                    break;
                case R.id.button_back1:
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
        }
    }


}