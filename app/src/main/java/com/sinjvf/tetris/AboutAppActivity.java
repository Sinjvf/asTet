package com.sinjvf.tetris;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by sinjvf on 28.12.15.
 */
public class AboutAppActivity extends Activity implements View.OnClickListener {
    private Button buttonBack;
  //  private LinearLayout mainLayout;
    private int appType;
    private Intent intent;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    //private LinearLayoutManager llm;

    private ArrayList<Spanned> ssText;
    private ArrayList<Spanned> ssDescript;
    private ArrayList<Spanned> ssSection;
    private String[] sText;
    private String[] sDescript;
    private String[] sSection;
    private Spanned sblank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        appType = intent.getIntExtra(Const.ABOUT, 1);
        setContentView(R.layout.main_recycle_about);
       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        buttonBack = (Button) findViewById(R.id.button_back);
        buttonBack.setOnClickListener(this);
       // mainLayout = (LinearLayout)findViewById(R.id.main_about_layout);
        ssText = new ArrayList<Spanned>();
        ssDescript = new ArrayList<Spanned>();
        ssSection = new ArrayList<Spanned>();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String blank = getResources().getString(R.string.blank);
        sblank = android.text.Html.fromHtml(blank);
        switch (appType) {
            case Const.ABOUT_RULE:
                setRule();
                break;
            case Const.ABOUT_APP:
                setApp();
                break;
        }
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                    R.drawable.logo_small);
            ActivityManager.TaskDescription taskDesc =
                    new ActivityManager.TaskDescription(getString(R.string.app_name),
                            icon, ContextCompat.getColor(this, R.color.dark_primary));
            this.setTaskDescription(taskDesc);
        }
    }

    private void setRule(){

        sText =getResources().getStringArray(R.array.about_t);
        sDescript =getResources().getStringArray(R.array.about_d);
        sSection =getResources().getStringArray(R.array.about_sect);

        for (int i=0; i<sDescript.length;i++){
            ssText.add(android.text.Html.fromHtml(sText[i]));
            ssDescript.add(android.text.Html.fromHtml(sDescript[i]));
            ssSection.add(android.text.Html.fromHtml(sSection[i]));
        }
        mAdapter = new RecyclerAdapterAbout(ssSection, ssDescript, ssText,sblank,
                Const.ABOUT_RULE, mLayoutManager, getResources().getDimension(R.dimen.text_about_d_size));

        mRecyclerView.setAdapter(mAdapter);
    }
    private void setApp(){

        sText =getResources().getStringArray(R.array.about_app_t);
        sDescript =getResources().getStringArray(R.array.about_app_d);
        sSection =getResources().getStringArray(R.array.about_app_sect);

        for (int i=0; i<sDescript.length;i++){
         ssText.add(android.text.Html.fromHtml(sText[i]));
         ssDescript.add(android.text.Html.fromHtml(sDescript[i]));
         ssSection.add(android.text.Html.fromHtml(sSection[i]));

        }

        //llm.scrollToPositionWithOffset(2, 20)
        mAdapter = new RecyclerAdapterAbout(ssSection, ssDescript, ssText,sblank, Const.ABOUT_APP, mLayoutManager, 0);
        mRecyclerView.setAdapter(mAdapter);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                finish();
            break;
        }
    }
    @Override
    public void onBackPressed(){
        intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
        finish();
    }
}
