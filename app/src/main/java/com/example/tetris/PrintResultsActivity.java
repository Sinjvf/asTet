package com.example.tetris;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sinjvf on 20.11.15.
 */
public class PrintResultsActivity extends Activity implements View.OnClickListener {
    private Button buttonExit, buttonErase;
    private TableLayout resTable;
    private int type;
    private GameProperties gameProperties;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        buttonExit = (Button)findViewById(R.id.button_exit_from_res);
        buttonErase = (Button)findViewById(R.id.button_erase);
        resTable = (TableLayout)findViewById(R.id.res_table);
        buttonExit.setOnClickListener(this);
        buttonErase.setOnClickListener(this);
        Intent intent= getIntent();
        gameProperties = (GameProperties) getIntent().getParcelableExtra("properties");
        type = gameProperties.getType();
        printResults();
/**
        TextView t2 = (TextView)findViewById(R.id.textView);
        Linkify.addLinks(t2, Linkify.ALL);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
   /**/
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_exit_from_res:

                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.button_erase:
                DBUser dbUser = new DBUser(this, type);
                dbUser.deleteRes();
                intent = new Intent(this, PrintResultsActivity.class);
                intent.putExtra("properties", gameProperties);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void printResults(){
        DBUser dbUser = new DBUser(this, type);
        ArrayList<ArrayList<String>> res = dbUser.allResults();
        for (Integer i=1;i<=res.size(); i++){
            TableRow record = new TableRow(this);
            record.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView place = new TextView(this);
            TextView tname = new TextView(this);
            TextView tscore = new TextView(this);
            TableRow.LayoutParams params = new TableRow.LayoutParams
                    (TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 0.4f);
            params.setMargins(0, 0, 4, 0);
            place.setLayoutParams(params);
            tname.setLayoutParams(new TableRow.LayoutParams
                    (TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 0.3f));
            tscore.setLayoutParams(new TableRow.LayoutParams
                    (TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 0.3f));

            place.setTextSize(25);
            tname.setTextSize(25);
            tscore.setTextSize(25);
            place.setTextColor(Color.parseColor("#ff0060a0"));
            tname.setTextColor(Color.parseColor("#ff0060a0"));
            tscore.setTextColor(Color.parseColor("#ff0060a0"));
            place.setGravity(Gravity.RIGHT);
            tname.setGravity(Gravity.LEFT);
            tscore.setGravity(Gravity.CENTER);
            tname.setText(res.get(i-1).get(0));
            tscore.setText(res.get(i-1).get(1));
            place.setText(i.toString());
            record.addView(place);
            record.addView(tname);
            record.addView(tscore);
            resTable.addView(record);


        }
    }
}
