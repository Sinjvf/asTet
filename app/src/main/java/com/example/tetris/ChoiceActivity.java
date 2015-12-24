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
    private Button button_standart, button_awry, button_hex,
            button_back;
    private Intent intent;
    private int nextLayout;
    private GameProperties gameProperties;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new Game(this));
        setContentView(R.layout.choice_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        button_standart = (Button)findViewById(R.id.button_standart);
        button_awry = (Button)findViewById(R.id.button_awry);
        button_hex = (Button)findViewById(R.id.button_hex);
        button_back = (Button)findViewById(R.id.button_back1);

        button_standart.setOnClickListener(this);
        button_awry.setOnClickListener(this);
        button_hex.setOnClickListener(this);
        button_back.setOnClickListener(this);

        Intent intent = getIntent();
        nextLayout = intent.getIntExtra("layout", 0);

    }
        @Override
        public void onClick(View v) {
            int type, back, complex;
            SharedPreferences sPref;
            sPref = getSharedPreferences(Const.SETTINGS,MODE_PRIVATE);
            back = sPref.getInt(Const.BACK, Const.DEFAULT_BACK);
            complex = sPref.getInt(Const.COMPLEX, Const.DEFAULT_COMPLEX);
            Log.d(Const.LOG_TAG, "-----load log...back="+back);

            switch (v.getId()) {
                case R.id.button_standart:
                    intent = (nextLayout==Const.NEW_GAME_LAYOUT)? new Intent(this, GameActivity.class)
                            :new Intent(this, PrintResultsActivity.class);
                    type = Const.STANDART;
                    gameProperties = new GameProperties(type, back, complex);
                    intent.putExtra("properties", gameProperties);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.button_awry:
                    intent = (nextLayout==Const.NEW_GAME_LAYOUT)? new Intent(this, GameActivity.class)
                            :new Intent(this, PrintResultsActivity.class);
                    type = Const.AWRY;
                    gameProperties = new GameProperties(type, back, complex);
                    intent.putExtra("properties", gameProperties);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.button_hex:
                    intent = (nextLayout==Const.NEW_GAME_LAYOUT)? new Intent(this, GameActivity.class)
                            :new Intent(this, PrintResultsActivity.class);
                    type = Const.HEX;
                    gameProperties = new GameProperties(type, back, complex);
                    intent.putExtra("properties", gameProperties);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.button_back1:
                    finish();
                    break;
        }
    };


}