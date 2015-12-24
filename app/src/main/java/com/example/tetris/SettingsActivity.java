package com.example.tetris;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class SettingsActivity extends Activity implements View.OnClickListener {
    private Button button_set_back, button_set_compex, button_default,
            button_save, button_cancel;

    private SharedPreferences sPref;
    private int back, complex;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        button_set_back = (Button)findViewById(R.id.button_set_back);
        button_set_compex = (Button)findViewById(R.id.button_set_complex);
        button_default = (Button)findViewById(R.id.button_default_settings);
        button_save = (Button)findViewById(R.id.button_save_settings);
        button_cancel = (Button)findViewById(R.id.button_cancel_settings);

        button_set_back.setOnClickListener(this);
        button_set_compex.setOnClickListener(this);
        button_default.setOnClickListener(this);
        button_save.setOnClickListener(this);
        button_cancel.setOnClickListener(this);

        loadSettings();

        Log.d(Const.LOG_TAG, "load back = " + back);
    }

    void saveSettings() {
        sPref = getSharedPreferences(Const.SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(Const.BACK, back);
        ed.putInt(Const.COMPLEX, complex);
        ed.commit();
        Log.d(Const.LOG_TAG, "save back = " + back);
    }
    void setDefaultSettings() {
        sPref = getSharedPreferences(Const.SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        back = Const.DEFAULT_BACK;
        complex = Const.DEFAULT_COMPLEX;
        ed.putInt(Const.BACK, back);
        ed.putInt(Const.COMPLEX, complex);
        ed.commit();
    }
    void loadSettings() {
        sPref = getSharedPreferences(Const.SETTINGS, MODE_PRIVATE);
        back = sPref.getInt(Const.BACK, Const.DEFAULT_BACK);
        complex = sPref.getInt(Const.COMPLEX, Const.DEFAULT_COMPLEX);
    }
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.button_set_back:
                    intent = new Intent(this, BackgroundChoiceActivity.class);
                    startActivityForResult(intent, 1);

                    break;
                case R.id.button_set_complex:

                    break;
                case R.id.button_default_settings:
                    setDefaultSettings();
                    break;
                case R.id.button_save_settings:
                    saveSettings();
                    finish();
                    break;
                case R.id.button_cancel_settings:
                    finish();
                    break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        back = data.getIntExtra(Const.BACK, Const.DEFAULT_BACK);


    }
}