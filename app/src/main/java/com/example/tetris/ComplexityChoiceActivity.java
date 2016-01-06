package com.example.tetris;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sinjvf on 24.12.15.
 */
public class ComplexityChoiceActivity extends Activity implements View.OnClickListener {
    private GameProperties.Complex complex;
    private int []colors;

    private LinearLayout.LayoutParams paramsForBig;
    private LinearLayout mainLL;
    private TextView titleColors;
    private TextView titlePace;
    private int last_id=0;

    private RadioGroup groupForNumbers;
    private ArrayList<RadioButton> buttonForNumbers;
   // private ArrayList<Integer> idsForNumbers;
    private int [] idsForNumbers;

    private ArrayList<RadioGroup> groupForColors;
    private ArrayList<ArrayList<RadioButton> > buttonForColors;
    //private ArrayList<ArrayList<Integer> > idsForColors;
    private int [][]idsForColors;


    private RadioGroup groupForPace;
    private ArrayList<RadioButton> buttonForPace;
   // private ArrayList<Integer> idsForPace;
    private int []idsForPace;
    

    private Button buttonCancel, buttonSave;
    private static final int[] resources={R.drawable.red,
            R.drawable.green,
            R.drawable.yellow,
            R.drawable.blue,
            R.drawable.random};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        complex  = (GameProperties.Complex) getIntent().getParcelableExtra(Const.COMPLEXITY);
        colors = complex.getColorShemes();
        
        setContentView(R.layout.set_complexity_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mainLL = (LinearLayout)findViewById(R.id.linearLayout_from_complex_choice);
        String[] textForNumb = {"0", "1", "2", "3", "4"};

        paramsForBig = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 0);
        paramsForBig.setMargins(0, 10, 0, 10);
       // idsForNumbers = new ArrayList<Integer>();
        idsForNumbers = new int [Const.MAX_FIG+1];
        groupForNumbers = new RadioGroup(this);
        buttonForNumbers = new ArrayList<RadioButton>();
        setIdsForNumbers();
     //   idsForNumbers = getResources().getIntArray(R.array.numb);
        setButtons(groupForNumbers, buttonForNumbers, idsForNumbers,
                Const.MAX_FIG + 1, textForNumb, null);
        buttonForNumbers.get(complex.getNumbers()).setChecked(true);

        titleColors = new TextView(this);
        titleColors.setText("Color for\nmischievous figures:");
        titleColors.setGravity(Gravity.CENTER);
        titleColors.setTextSize(25);
        mainLL.addView(titleColors);
        
        groupForColors = new ArrayList<RadioGroup>();
        buttonForColors =new ArrayList<ArrayList<RadioButton> >();
        String[] textForColor1 = { "R", "G","Y", "B", "RAND"};
      //  idsForColors = new ArrayList<ArrayList<Integer> >();

        idsForColors = new int [Const.MAX_FIG][];
        setIdsForColors();
        for (int i=0; i<Const.MAX_FIG;i++){
            groupForColors.add(new RadioGroup(this));
            buttonForColors.add(new ArrayList<RadioButton>());
            //idsForColors.add(new ArrayList<Integer>());

            setButtons(groupForColors.get(i), buttonForColors.get(i), idsForColors[i],
                    Const.MAX_COLOR, textForColor1, resources);
            buttonForColors.get(i).get(complex.getColorShemes()[i]).setChecked(true);
        }
        int i=complex.getNumbers();
        for (int j = 0; j < i; j++) {
            groupForColors.get(j).setVisibility(View.VISIBLE);
        }
        for (int j = i; j < 3; j++) {
            groupForColors.get(j).setVisibility(View.INVISIBLE);
        }
        if (i > 0) {
            titleColors.setVisibility(View.VISIBLE);
        } else {
            titleColors.setVisibility(View.INVISIBLE);
        }



        String [] textForPace = {"1", "2", "3", "4"};

        titlePace = new TextView(this);
        titlePace.setText("Starting pace:");
        titlePace.setGravity(Gravity.CENTER);
        titlePace.setTextSize(25);
        mainLL.addView(titlePace);
      //  idsForPace = new ArrayList<Integer>();
        idsForPace = new int [Const.MAX_PACE];
        groupForPace = new RadioGroup(this);
        buttonForPace = new ArrayList<RadioButton>();
        setIdsForPace();
        setButtons(groupForPace, buttonForPace, idsForPace, Const.MAX_PACE,  textForPace, null);
        buttonForPace.get(complex.getPace()).setChecked(true);
        
        
        buttonSave = (Button)findViewById(R.id.button_save_settings);
        buttonCancel = (Button)findViewById(R.id.button_cancel_settings);

        buttonSave.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
    }

    private void setIdsForNumbers(){
        for (int i=0;i<Const.MAX_FIG+1;i++){
            idsForNumbers[i] = i+last_id;
        }
        last_id = idsForNumbers[Const.MAX_FIG];
        //idsForNumbers = getResources().getIntArray(R.array.numb);

    }
    private void setIdsForColors(){
        for (int i=0;i<Const.MAX_FIG;i++){
            idsForColors[i] = new int[Const.MAX_COLOR];
            for (int j=0;j<Const.MAX_COLOR;j++){
                idsForColors[i][j] = last_id+j;
            }
            last_id = idsForColors[i][Const.MAX_COLOR-1];
        }
        /**
        idsForColors[0] =  getResources().getIntArray(R.array.fig_1);
        idsForColors[1] =  getResources().getIntArray(R.array.fig_2);
        idsForColors[2] =  getResources().getIntArray(R.array.fig_3);
        /**/
    }
    private void setIdsForPace(){
       // idsForPace = getResources().getIntArray(R.array.fig_pace);
        for (int i=0;i<Const.MAX_PACE;i++){
            idsForPace[i] = i+last_id;
        }
        last_id = idsForPace[Const.MAX_PACE-1];
    }
    private void setButtons(RadioGroup group,ArrayList<RadioButton> button,
                          int[] ids, int n,String[]mytext,  int[] res){
        RadioGroup.LayoutParams paramsForGroup;
        paramsForGroup = new RadioGroup.LayoutParams
                (RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT, 1);
        group.setLayoutParams(paramsForBig);
        group.setOrientation(LinearLayout.HORIZONTAL);
        group.setWeightSum(n);
        group.setGravity(Gravity.CENTER);

        for (Integer i=0; i<n;i++){
            button.add(new RadioButton(this));
            button.get(i).setLayoutParams(paramsForGroup);
            button.get(i).setGravity(Gravity.CENTER);
            button.get(i).setId(ids[i]);
            button.get(i).setText(mytext[i]);
            if(res!=null)
                button.get(i).setBackgroundResource(res[i]);
            group.addView(button.get(i));
            button.get(i).setOnClickListener(this);
        }

        mainLL.addView(group);
        group.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        for (int i=0; i<Const.MAX_FIG+1;i++){
            if (v.getId()==idsForNumbers[i])
            {
                complex.setNumbers(i);
                for (int j=0; j<i;j++){
                    groupForColors.get(j).setVisibility(View.VISIBLE);
                }
                for (int j=i;j<3;j++){
                    groupForColors.get(j).setVisibility(View.INVISIBLE);
                }
                if(i>0) {
                    titleColors.setVisibility(View.VISIBLE);
                }
                else{
                    titleColors.setVisibility(View.INVISIBLE);
                }
            }
        }
        for (int fig=0; fig<Const.MAX_FIG;fig++)
            for (int col=0; col<Const.MAX_COLOR;col++)
            {
                if (v.getId()==idsForColors[fig][col])
                    {
                        colors[fig]=col;
                        complex.setColorShemes(colors);
                    }
            }

        for (int i=0; i<Const.MAX_PACE;i++){
            if (v.getId()==idsForPace[i])
            {
                complex.setPace(i);
            }
        }
        switch (v.getId()) {
            case R.id.button_save_settings:
                Intent intent = new Intent();
                intent.putExtra(Const.COMPLEXITY, complex);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.button_cancel_settings:
                finish();
                break;
        }
    }
}
