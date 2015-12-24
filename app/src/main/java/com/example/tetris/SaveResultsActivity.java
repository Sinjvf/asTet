package com.example.tetris;

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
    private Button button_ok, button_cancel;
    private EditText textName;
    private TextView textScore;
    private int type;
    private DBUser db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_results);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        button_ok = (Button)findViewById(R.id.button_save_res);
        button_cancel = (Button)findViewById(R.id.button_cancel_res);
        button_ok.setOnClickListener(this);
        button_cancel.setOnClickListener(this);
        textName = (EditText)findViewById(R.id.winnerName);
        textScore = (TextView)findViewById(R.id.textScore);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        type = intent.getIntExtra("type", 0);
        db = new DBUser(this, type);
      //  Log.d(Const.LOG_TAG, "score = "+score);
        textScore.setText(score.toString());
        textName.setText(db.bestRes());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_save_res:
                saveRes();
                finish();
                break;
            case R.id.button_cancel_res:
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
