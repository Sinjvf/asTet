package com.sinjvf.tetris;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by sinjvf on 24.12.15.
 */
public class ComplexityChoiceActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private GameProperties.Complex complex;
    private int []colors;

    private LinearLayout.LayoutParams paramsForBig;
    private LinearLayout numberLL, mainLL, switchLL;
    private TextView titleColors;
    private TextView titlePace;
    private TextView twSwitcher;
    private int last_id=0;

    private RadioGroup groupForNumbers;
    private ArrayList<RadioButton> buttonForNumbers;
   // private ArrayList<Integer> idsForNumbers;
    private int [] idsForNumbers;

    private Switch mySwitcher;
    private ArrayList<RadioGroup> groupForColors;
    private ArrayList<ArrayList<RadioButton> > buttonForColors;
    //private ArrayList<ArrayList<Integer> > idsForColors;
    private int [][]idsForColors;


    private RadioGroup groupForPace;
    private ArrayList<RadioButton> buttonForPace;
   // private ArrayList<Integer> idsForPace;
    private int []idsForPace;
    

    private Button buttonCancel, buttonSave;
    private static int[] resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        complex  = (GameProperties.Complex) getIntent().getParcelableExtra(Const.COMPLEXITY);
        colors = complex.getColorSchemes();
        resources = new int [5];
        resources[0]=R.drawable.red;
        resources[1]=R.drawable.green;
        resources[2]=R.drawable.yellow;
        resources[3]=R.drawable.blue;
        resources[4]=R.drawable.random;
        
        setContentView(R.layout.set_complexity_layout);
        mySwitcher = (Switch)findViewById(R.id.switch1);
        mySwitcher.setOnCheckedChangeListener(this);

        mySwitcher.setChecked(complex.getPrevent()==Const.SWITCH_PREVENT);
      //  twSwitcher = (TextView)findViewById(R.id.tw_switcher);
    //    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        switchLL = (LinearLayout)findViewById(R.id.linearLayout_for_switcher);
        numberLL = (LinearLayout)findViewById(R.id.linearLayout_1);
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
                Const.MAX_FIG + 1, textForNumb, null, numberLL);
        buttonForNumbers.get(complex.getNumbers()).setChecked(true);

        titleColors = new TextView(this);
        titleColors.setText(getResources().getString(R.string.color));
        titleColors.setGravity(Gravity.CENTER);

      //  titleColors.setTextColor(ContextCompat.getColor(this, R.color.dark_pressed));
        titleColors.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_about_d_size));
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
                    Const.MAX_COLOR, textForColor1, resources, mainLL);
            buttonForColors.get(i).get(complex.getColorSchemes()[i]).setChecked(true);
        }
        int i=complex.getNumbers();
        for (int j = 0; j < i; j++) {
            groupForColors.get(j).setVisibility(View.VISIBLE);
        }
        for (int j = i; j < 3; j++) {
            groupForColors.get(j).setVisibility(View.INVISIBLE);
        }
        setTitleVisible(i);

        String [] textForPace = {"1", "2", "3", "4"};

        titlePace = new TextView(this);
        titlePace.setText(getResources().getString(R.string.pace));
        titlePace.setGravity(Gravity.CENTER);
        //titlePace.setTextColor(ContextCompat.getColor(this, R.color.dark_pressed));
        //titlePace.setTextSize(25);
        titlePace.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_about_d_size));
        mainLL.addView(titlePace);
      //  idsForPace = new ArrayList<Integer>();
        idsForPace = new int [Const.MAX_PACE];
        groupForPace = new RadioGroup(this);
        buttonForPace = new ArrayList<RadioButton>();
        setIdsForPace();
        setButtons(groupForPace, buttonForPace, idsForPace, Const.MAX_PACE,  textForPace, null, mainLL);
        buttonForPace.get(complex.getPace()).setChecked(true);
        
        
        buttonSave = (Button)findViewById(R.id.button_save);
        buttonCancel = (Button)findViewById(R.id.button_cancel);

        buttonSave.setOnClickListener(this);
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

    private void setIdsForNumbers(){
        for (int i=0;i<Const.MAX_FIG+1;i++){
            idsForNumbers[i] = i+last_id;
        }
        last_id = idsForNumbers[Const.MAX_FIG];
        //idsForNumbers = getResources().getIntArray(R.array.numb);

    }

    private void setTitleVisible(int i){
        if (i > 0) {
            titleColors.setVisibility(View.VISIBLE);
            switchLL.setVisibility(View.VISIBLE);

        } else {
            titleColors.setVisibility(View.INVISIBLE);
            switchLL.setVisibility(View.INVISIBLE);
        }
    }

    private void setIdsForColors(){
        for (int i=0;i<Const.MAX_FIG;i++){
            idsForColors[i] = new int[Const.MAX_COLOR];
            for (int j=0;j<Const.MAX_COLOR;j++){
                idsForColors[i][j] = last_id+j;
            }
            last_id = idsForColors[i][Const.MAX_COLOR-1];
        }
    }
    private void setIdsForPace(){
       // idsForPace = getResources().getIntArray(R.array.fig_pace);
        for (int i=0;i<Const.MAX_PACE;i++){
            idsForPace[i] = i+last_id;
        }
        last_id = idsForPace[Const.MAX_PACE-1];
    }
    private void setButtons(RadioGroup group,ArrayList<RadioButton> button,
                          int[] ids, int n,String[]mytext,  int[] res, LinearLayout ll){
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
            button.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_radio_button));
            if(res!=null)
                button.get(i).setBackgroundResource(res[i]);
            group.addView(button.get(i));
            button.get(i).setOnClickListener(this);
        }

        ll.addView(group);
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
                setTitleVisible(i);
            }
        }
        for (int fig=0; fig<Const.MAX_FIG;fig++)
            for (int col=0; col<Const.MAX_COLOR;col++)
            {
                if (v.getId()==idsForColors[fig][col])
                    {
                        colors[fig]=col;
                        complex.setColorSchemes(colors);
                    }
            }

        for (int i=0; i<Const.MAX_PACE;i++){
            if (v.getId()==idsForPace[i])
            {
                complex.setPace(i);
            }
        }
        switch (v.getId()) {
            case R.id.button_save:
                Intent intent = new Intent();
                intent.putExtra(Const.COMPLEXITY, complex);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.button_cancel:
                finish();
                break;
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int prevent;
        prevent = isChecked ? Const.SWITCH_PREVENT : Const.SWITCH_DISTRACT;
        complex.setPrevent(prevent);
    }
}
