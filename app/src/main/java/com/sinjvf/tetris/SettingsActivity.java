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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SettingsActivity extends Activity implements View.OnClickListener {
    private Button buttonSetBack, buttonSetCompex, buttonDefault,
            buttonSave, buttonCancel, buttonGoTo;

    private SharedPreferences sPref;
    private int back;
    private GameProperties.Complex complex;
    private static final int REQUEST_BACK_CODE=1;
    private static final int REQUEST_COMPLEX_CODE=2;

  //  private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        buttonSetBack = (Button)findViewById(R.id.button_set_back);
        buttonSetCompex = (Button)findViewById(R.id.button_set_complex);
        buttonDefault = (Button)findViewById(R.id.button_default_settings);
        buttonSave = (Button)findViewById(R.id.button_save);
        buttonCancel = (Button)findViewById(R.id.button_cancel);
        buttonGoTo = (Button)findViewById(R.id.button_goto_signin);
        buttonSetBack.setOnClickListener(this);
        buttonSetCompex.setOnClickListener(this);
        buttonDefault.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        buttonGoTo.setOnClickListener(this);

        loadSettings();

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

    void saveSettings() {
        sPref = getSharedPreferences(Const.SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(Const.BACK, back);
        saveComplex();
        ed.commit();
       // Log.d(Const.LOG_TAG, "save back = " + back);
    }

    void saveComplex(){
        sPref = getSharedPreferences(Const.SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(Const.COMPLEX_NUMB, complex.getNumbers());
        ed.putInt(Const.COMPLEX_PACE, complex.getPace());
        ed.putInt(Const.COMPLEX_PREVENT, complex.getPrevent());
        for (int i=0;i<complex.getNumbers();i++){
            ed.putInt(Const.COMPLEX_COLORS[i], complex.getColorSchemes()[i]);
        }
        ed.commit();
    //    Log.d(Const.LOG_TAG, "save complex. numb=" + complex.getNumbers() + ", pace=" + complex.getPace());
    }
    void setDefaultSettings() {
        sPref = getSharedPreferences(Const.SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        back = Const.DEFAULT_BACK;
        complex = new GameProperties.Complex();
        ed.putInt(Const.BACK, back);
        saveComplex();
        ed.commit();
    }
    void loadSettings() {
        int numb, colors[], pace, prevent;
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
 //       Log.d(Const.LOG_TAG, "load complex. numb=" + complex.getNumbers() + ", pace=" + complex.getPace());
    }
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.button_set_back:
                    intent = new Intent(this, BackgroundChoiceActivity.class);
                    startActivityForResult(intent, REQUEST_BACK_CODE);

                    break;
                case R.id.button_set_complex:
                    intent = new Intent(this, ComplexityChoiceActivity.class);
                    intent.putExtra(Const.COMPLEXITY, complex);
                    startActivityForResult(intent, REQUEST_COMPLEX_CODE);
                    break;
                case R.id.button_default_settings:
                    setDefaultSettings();
                    Toast toast = Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.def_is_set), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, (int)(getResources().getDimension(R.dimen.button_back_size)));
                    toast.show();

                    break;
                case R.id.button_save:
                    saveSettings();
                case R.id.button_cancel:
                    saveSettings();
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.button_goto_signin:
                    intent = new Intent(this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                    break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_BACK_CODE:
                    back = data.getIntExtra(Const.BACK, Const.DEFAULT_BACK);
                    break;
                case REQUEST_COMPLEX_CODE:
                    complex = (GameProperties.Complex) data.getParcelableExtra(Const.COMPLEXITY);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed(){
        saveSettings();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}