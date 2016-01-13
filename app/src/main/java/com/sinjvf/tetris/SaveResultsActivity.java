package com.sinjvf.tetris;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by sinjvf on 23.11.15.
 */
public class SaveResultsActivity extends Activity implements View.OnClickListener {
    private Integer score;
    private String name;
    private Button buttonOk, buttonCancel;
    private EditText textName;
    private TextView textScore;
    private int type;
    private DBUser db;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_results_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        buttonOk = (Button)findViewById(R.id.button_save);
        buttonCancel = (Button)findViewById(R.id.button_cancel);
        buttonOk.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        textName = (EditText)findViewById(R.id.winnerName);
        textScore = (TextView)findViewById(R.id.textScore);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        type = intent.getIntExtra("type", 0);
        db = new DBUser(this, type);
        textScore.setText(score.toString());
        textName.setText(db.bestRes());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_save:
                saveRes();
            case R.id.button_cancel:

                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }
    private void saveRes(){
        DBUser db = new DBUser(this, type);
        name = textName.getText().toString();

        db.printResult(name, score);
    }
}
