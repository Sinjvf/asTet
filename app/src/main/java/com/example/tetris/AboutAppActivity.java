package com.example.tetris;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by sinjvf on 28.12.15.
 */
public class AboutAppActivity extends Activity implements View.OnClickListener {
    private Button buttonBack;
    private LinearLayout mainLayout;
    private int appType;
    private Intent intent;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
        setContentView(R.layout.empty_about_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        buttonBack = (Button) findViewById(R.id.button_about_app_back);
        buttonBack.setOnClickListener(this);
        mainLayout = (LinearLayout)findViewById(R.id.main_about_layout);
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

/**
        iText.add(R.string.about_t_0);
        iText.add(R.string.about_t_1);
        iText.add(R.string.about_t_2);
        iText.add(R.string.about_t_3);
        iText.add(R.string.about_t_4);
        iText.add(R.string.about_t_5);
        iText.add(R.string.about_t_6);
        iText.add(R.string.about_t_7);
        iText.add(R.string.about_t_8);
        iText.add(R.string.about_t_9);
        iText.add(R.string.about_t_10);
        iDescript.add(R.string.about_d_0);
        iDescript.add(R.string.about_d_1);
        iDescript.add(R.string.about_d_2);
        iDescript.add(R.string.about_d_3);
        iDescript.add(R.string.about_d_4);
        iDescript.add(R.string.about_d_5);
        iDescript.add(R.string.about_d_6);
        iDescript.add(R.string.about_d_7);
        iDescript.add(R.string.about_d_8);
        iDescript.add(R.string.about_d_9);
        iDescript.add(R.string.about_d_10);
        iSection.add(R.string.about_sect_0);
        iSection.add(-1);
        iSection.add(-1);
        iSection.add(-1);
        iSection.add(-1);
        iSection.add(-1);
        iSection.add(R.string.about_sect_6);
        iSection.add(-1);
        iSection.add(-1);
        iSection.add(-1);
        iSection.add(R.string.about_sect_10);
 /**/

        mAdapter = new RecyclerAdapter(ssSection, ssDescript, ssText,sblank,  Const.ABOUT_RULE);
        mRecyclerView.setAdapter(mAdapter);
    }
    private void setApp(){

/**
        iSection.add(-1);
        iDescript.add(R.string.about_app_d_0);
        iText.add(R.string.about_app_t_0);
 /**/
        sText =getResources().getStringArray(R.array.about_app_t);
        sDescript =getResources().getStringArray(R.array.about_app_d);
        sSection =getResources().getStringArray(R.array.about_app_sect);

        for (int i=0; i<sDescript.length;i++){
         ssText.add(android.text.Html.fromHtml(sText[i]));
         ssDescript.add(android.text.Html.fromHtml(sDescript[i]));
         ssSection.add(android.text.Html.fromHtml(sSection[i]));

        }

        //mAdapter = new RecyclerAdapter(iSection, iDescript, iText, Const.ABOUT_APP);

        mAdapter = new RecyclerAdapter(ssSection, ssDescript, ssText,sblank, Const.ABOUT_APP);
        mRecyclerView.setAdapter(mAdapter);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_about_app_back:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                finish();
            break;
    }
    }
}
