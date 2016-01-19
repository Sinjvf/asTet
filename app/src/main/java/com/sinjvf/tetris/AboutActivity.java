package com.sinjvf.tetris;

/**
 * Created by sinjvf on 28.12.15.
 */

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class AboutActivity extends Activity implements View.OnClickListener {
    private Button buttonRules, buttonApp,
            buttonBack;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new Game(this));
        setContentView(R.layout.about_layout);
      //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        buttonRules = (Button)findViewById(R.id.button_about_rules);
        buttonApp = (Button)findViewById(R.id.button_about_app);
        buttonBack = (Button)findViewById(R.id.button_cancel);

        buttonRules.setOnClickListener(this);
        buttonApp.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_about_rules:
                intent = new Intent(this, AboutAppActivity.class);
                intent.putExtra(Const.ABOUT, Const.ABOUT_RULE);
                startActivity(intent);
                finish();
                break;
            case R.id.button_about_app:
                intent = new Intent(this, AboutAppActivity.class);
                intent.putExtra(Const.ABOUT, Const.ABOUT_APP);
                startActivity(intent);
                finish();
                break;
            case R.id.button_cancel:
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


}
