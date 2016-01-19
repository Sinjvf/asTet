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
            R.drawable.rock1, R.drawable.rock2, R.drawable.rock3,
            R.drawable.fox, R.drawable.ev, R.drawable.zai,
            R.drawable.tulip, R.drawable.tulip2, R.drawable.flower,
            R.drawable.vulk, R.drawable.hur, R.drawable.edel};
    private final static int[] s_resources ={
            R.drawable.s_rock1, R.drawable.s_rock2, R.drawable.s_rock3,
            R.drawable.s_fox, R.drawable.s_ev, R.drawable.s_zai,
            R.drawable.s_tulip, R.drawable.s_tulip2, R.drawable.s_flower,
            R.drawable.s_vulk, R.drawable.s_hur, R.drawable.s_edel};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_recycle_back);
   //     setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new RecyclerAdapterBackground(s_resources);
        mRecyclerView.setAdapter(mAdapter);
        ((RecyclerAdapterBackground)mAdapter).setMyClickListener(this);
        buttonCancel = (Button)findViewById(R.id.button_back);
        buttonCancel.setOnClickListener(this);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                    R.drawable.logo_small);
            ActivityManager.TaskDescription taskDesc =
                    new ActivityManager.TaskDescription(getString(R.string.app_name),
                            icon, ContextCompat.getColor(this, R.color.dark_primary));
            this.setTaskDescription(taskDesc);
        }
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

      //  Log.d(Const.LOG_TAG, "picture № " + pos);
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
