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
    private ArrayList<ImageButton> background_buttons;
    private Button button_cancel;
    private ArrayList<LinearLayout> ll;
    private LinearLayout mainLL;
    private ArrayList<Integer> ids;
    private final static int n=9;

    private final static int[] resourses = {R.drawable.bear, R.drawable.cat, R.drawable.fiolet,
            R.drawable.ornament, R.drawable.sky1, R.drawable.sky2,
            R.drawable.straus1, R.drawable.straus2, R.drawable.zhiraf};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.back_choice);
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
        params_for_layout.bottomMargin = 1;
        ids = new ArrayList<Integer>();
        ll = new ArrayList<LinearLayout>();
        background_buttons = new ArrayList<ImageButton>();
        for (Integer k=0; k<n ;k++){
            background_buttons.add(new ImageButton(this));
            background_buttons.get(k).setImageResource(resourses[k]);
            background_buttons.get(k).setBackgroundResource(R.drawable.image_selector);
            background_buttons.get(k).setLayoutParams(params_for_button);
            background_buttons.get(k).setScaleType(ImageView.ScaleType.FIT_XY);
            //    background_buttons.get(k).
            background_buttons.get(k).setOnClickListener(this);
            ids.add(background_buttons.get(k).generateViewId());
            background_buttons.get(k).setId(ids.get(k));
            i = k%3;
            j = k/3;
            if (i==2){
                ll.add(new LinearLayout(this));
                ll.get(j).addView(background_buttons.get(k - 2));
                ll.get(j).addView(background_buttons.get(k - 1));
                ll.get(j).addView(background_buttons.get(k));
                ll.get(j).setLayoutParams(params_for_layout);
                ll.get(j).setOrientation(LinearLayout.HORIZONTAL);
                // ll.get(k).
                mainLL.addView(ll.get(j));

            }
        }
        button_cancel = new Button(this);
        button_cancel.setText("cancel");
        button_cancel.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 0));
        ids.add(button_cancel.generateViewId());
        button_cancel.setId(ids.get(n));
        button_cancel.setBackgroundResource(R.drawable.button_selector);
        button_cancel.setOnClickListener(this);
        mainLL.addView(button_cancel);

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
