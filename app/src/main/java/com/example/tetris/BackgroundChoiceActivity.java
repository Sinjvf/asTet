package com.example.tetris;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by sinjvf on 24.12.15.
 */
public class BackgroundChoiceActivity extends Activity implements View.OnClickListener {
    private ArrayList<ImageButton> backgroundButtons;
    private Button buttonCancel;
    private ArrayList<LinearLayout> ll;
    private LinearLayout mainLL;
    private ArrayList<Integer> ids;
    private final static int n=9;
    private final int id_0=0;

    private final static int[] resourses = {
            R.drawable.bear, R.drawable.cat, R.drawable.fiolet,
            R.drawable.ornament, R.drawable.sky1, R.drawable.sky2,
            R.drawable.straus1, R.drawable.straus2, R.drawable.zhiraf};
    private final static int[] s_resourses = {
            R.drawable.s_bear, R.drawable.s_cat, R.drawable.s_fiolet,
            R.drawable.s_ornament, R.drawable.s_sky1, R.drawable.s_sky2,
            R.drawable.s_straus1, R.drawable.s_straus2, R.drawable.s_zhiraf};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_background_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mainLL = (LinearLayout)findViewById(R.id.linearLayout_from_back_choice);

        int i, j;
        LinearLayout.LayoutParams params_for_button;
        params_for_button = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params_for_button.setMargins(0, 0, 0, 0);

        LinearLayout.LayoutParams params_for_layout;
        params_for_layout = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params_for_layout.setMargins(0, 0, 0, 0);

        ids = new ArrayList<Integer>();
        ll = new ArrayList<LinearLayout>();
        backgroundButtons = new ArrayList<ImageButton>();
        for (Integer k=0; k<n ;k++){
            backgroundButtons.add(new ImageButton(this));
            backgroundButtons.get(k).setImageResource(s_resourses[k]);
            backgroundButtons.get(k).setBackgroundResource(R.drawable.image_selector);
            backgroundButtons.get(k).setLayoutParams(params_for_button);
            backgroundButtons.get(k).setScaleType(ImageView.ScaleType.FIT_XY);
            backgroundButtons.get(k).setOnClickListener(this);
            ids.add(k);
            backgroundButtons.get(k).setId(ids.get(k));
            i = k%3;
            j = k/3;
            if (i==2){
                ll.add(new LinearLayout(this));
                ll.get(j).addView(backgroundButtons.get(k - 2));
                ll.get(j).addView(backgroundButtons.get(k - 1));
                ll.get(j).addView(backgroundButtons.get(k));
                ll.get(j).setLayoutParams(params_for_layout);
                ll.get(j).setOrientation(LinearLayout.HORIZONTAL);
                // ll.get(k).
                mainLL.addView(ll.get(j));

            }
        }
        buttonCancel = new Button(this);
        buttonCancel.setText("cancel");
        LinearLayout.LayoutParams params_for_cancel = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 0);
        params_for_cancel.setMargins(0, 0, 0, 4);
        buttonCancel.setLayoutParams(params_for_cancel);
        ids.add(ids.get(ids.size()-1)+1);
        buttonCancel.setId(ids.get(n));
        buttonCancel.setBackgroundResource(R.drawable.button_selector);
        buttonCancel.setOnClickListener(this);
        mainLL.addView(buttonCancel);

    }

    @Override
    public void onClick(View v) {
        for (int i=0;i<9;i++){
            if (v.getId()==ids.get(i)){
                Log.d(Const.LOG_TAG, "picture № " + i);
                Intent intent = new Intent();
                intent.putExtra(Const.BACK, resourses[i]);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        if (v.getId() == ids.get(n)){
            finish();
        }
    }
}
