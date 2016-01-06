package com.example.tetris;

/**
 * Created by sinjvf on 28.12.15.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;



public class AboutActivity extends Activity implements View.OnClickListener {
    private Button buttonRules, buttonApp,
            buttonBack;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new Game(this));
        setContentView(R.layout.about_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        buttonRules = (Button)findViewById(R.id.button_about_rules);
        buttonApp = (Button)findViewById(R.id.button_about_app);
        buttonBack = (Button)findViewById(R.id.button_about_cancel);

        buttonRules.setOnClickListener(this);
        buttonApp.setOnClickListener(this);
        buttonBack.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_about_rules:
                intent = new Intent(this, AboutAppActivity.class);
                intent.putExtra(Const.ABOUT, Const.ABOUT_RULE);
                startActivity(intent);
            //    finish();
                break;
            case R.id.button_about_app:
                intent = new Intent(this, AboutAppActivity.class);
                intent.putExtra(Const.ABOUT, Const.ABOUT_APP);
                startActivity(intent);
              //  finish();
                break;
            case R.id.button_about_cancel:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    };


}
