package com.example.tetris;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button button_new_game,
            button_rating,
            button_settings,
            button_about,
            button_finish;

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        button_new_game = (Button)findViewById(R.id.button_new_game_layout);
        button_rating = (Button)findViewById(R.id.button_results_layout);
        button_settings = (Button)findViewById(R.id.button_settings_layout);
        button_about = (Button)findViewById(R.id.button_about_layout);
        button_finish = (Button)findViewById(R.id.button_finish);

        button_new_game.setOnClickListener(this);
        button_rating.setOnClickListener(this);
        button_settings.setOnClickListener(this);
        button_about.setOnClickListener(this);
        button_finish.setOnClickListener(this);

    }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_new_game_layout:
                    intent = new Intent(this, ChoiceActivity.class);
                    intent.putExtra("layout", Const.NEW_GAME_LAYOUT);
                    startActivity(intent);
                    break;
                case R.id.button_results_layout:
                    intent = new Intent(this, ChoiceActivity.class);
                    intent.putExtra("layout", Const.RESULTS_LAYOUT);
                    startActivity(intent);
                    break;
                case R.id.button_settings_layout:
                    intent = new Intent(this, SettingsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_about_layout:
           //         intent = new Intent(this, PrintResultsActivity.class);

              //      startActivity(intent);
                    break;
                case R.id.button_finish:
                    this.finish();
                    break;
        }
    };


}