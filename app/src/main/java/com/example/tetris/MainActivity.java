package com.example.tetris;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button buttonNewGame,
            buttonRating,
            buttonSettings,
            buttonAbout,
            buttonFinish;
    private final int REQUEST_CODE=1;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        buttonNewGame = (Button)findViewById(R.id.button_new_game_layout);
        buttonRating = (Button)findViewById(R.id.button_results_layout);
        buttonSettings = (Button)findViewById(R.id.button_settings_layout);
        buttonAbout = (Button)findViewById(R.id.button_about_layout);
        buttonFinish = (Button)findViewById(R.id.button_finish);

        buttonNewGame.setOnClickListener(this);
        buttonRating.setOnClickListener(this);
        buttonSettings.setOnClickListener(this);
        buttonAbout.setOnClickListener(this);
        buttonFinish.setOnClickListener(this);

    }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_new_game_layout:
                    intent = new Intent(this, ChoiceActivity.class);
                    intent.putExtra("layout", Const.NEW_GAME_LAYOUT);
                    startActivity(intent);
               //     finish();
                    break;
                case R.id.button_results_layout:
                    intent = new Intent(this, ChoiceActivity.class);
                    intent.putExtra("layout", Const.RESULTS_LAYOUT);
                    startActivity(intent);
                 //   finish();
                    break;
                case R.id.button_settings_layout:
                    intent = new Intent(this, SettingsActivity.class);
                    startActivity(intent);
                  //  finish();
                    break;
                case R.id.button_about_layout:
                    intent = new Intent(this, AboutActivity.class);
                    startActivity(intent);
                  //  finish();
                    break;
                case R.id.button_finish:
                    finish();
                    break;
        }
    }


}