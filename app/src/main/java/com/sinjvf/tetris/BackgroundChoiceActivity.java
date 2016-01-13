package com.sinjvf.tetris;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by sinjvf on 24.12.15.
 */
public class BackgroundChoiceActivity extends Activity implements View.OnClickListener, RecyclerAdapterBackground.ItemClickListener {
    private ArrayList<ImageButton> backgroundButtons;
    private Button buttonCancel;
    private ArrayList<LinearLayout> ll;
    private LinearLayout mainLL;
    private ArrayList<Integer> ids;
    private final static int n=9;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapterBackground.ItemClickListener clickListener;

    private final static int[] resources = {
            R.drawable.sky1, R.drawable.sky2, R.drawable.ice,
            R.drawable.cat, R.drawable.cat1, R.drawable.cat3,
            R.drawable.fiolet, R.drawable.ornament, R.drawable.fox,
            R.drawable.white, R.drawable.white2, R.drawable.pec,
            R.drawable.straus1, R.drawable.straus2, R.drawable.zhiraf,
            R.drawable.rock, R.drawable.rock2, R.drawable.rock3,
            R.drawable.elm,R.drawable.elm2,R.drawable.flakes};
    private final static int[] s_resources = {
            R.drawable.s_sky1, R.drawable.s_sky2, R.drawable.s_ice,
            R.drawable.s_cat, R.drawable.s_cat1, R.drawable.s_cat3,
            R.drawable.s_fiolet, R.drawable.s_ornament, R.drawable.s_fox,
            R.drawable.s_white, R.drawable.s_white2, R.drawable.s_pec,
            R.drawable.s_straus1, R.drawable.s_straus2, R.drawable.s_zhiraf,
            R.drawable.s_rock, R.drawable.s_rock2, R.drawable.s_rock3,
            R.drawable.s_elm,R.drawable.s_elm2,R.drawable.s_flakes };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_recycle_back);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new RecyclerAdapterBackground(s_resources);
        mRecyclerView.setAdapter(mAdapter);
        ((RecyclerAdapterBackground)mAdapter).setMyClickListener(this);
        buttonCancel = (Button)findViewById(R.id.button_back);
        buttonCancel.setOnClickListener(this);

    }

    @Override
    public void onImageClick(View view, int position) {
        int pos = position*3;
        switch (view.getId()) {
            case R.id.ib_recycler_1:
                pos =position * 3;
                break;
            case R.id.ib_recycler_2:
                pos = position * 3 + 1;
                break;
            case R.id.ib_recycler_3:
                pos = position * 3 + 2;
                break;
        }

        Log.d(Const.LOG_TAG, "picture № " + pos);
        Intent intent = new Intent();
        intent.putExtra(Const.BACK, resources[pos]);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_back){
            finish();
        }
    }
}
