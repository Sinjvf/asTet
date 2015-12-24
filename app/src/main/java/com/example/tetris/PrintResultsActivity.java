package com.example.tetris;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sinjvf on 20.11.15.
 */
public class PrintResultsActivity extends Activity implements View.OnClickListener {
    private Button button_exit, button_erase;
  //  private DBHelper dbHelper;
    private TableLayout resTable;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        button_exit = (Button)findViewById(R.id.button_exit_from_res);
        button_erase = (Button)findViewById(R.id.button_erase);
        resTable = (TableLayout)findViewById(R.id.res_table);
        button_exit.setOnClickListener(this);
        button_erase.setOnClickListener(this);
        Intent intent= getIntent();
        type = intent.getIntExtra("type", 0);

Log.d(Const.LOG_TAG, "--------Print res!----------");
        printResults();
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_exit_from_res:
                this.finish();
                break;
            case R.id.button_erase:
                DBUser dbUser = new DBUser(this, type);
                dbUser.deleteRes();
                Intent intentr = new Intent(this, PrintResultsActivity.class);
                intentr.putExtra("type", type);
                startActivity(intentr);
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

            place.setLayoutParams(new TableRow.LayoutParams
                                        (TableRow.LayoutParams.WRAP_CONTENT,
                                                TableRow.LayoutParams.WRAP_CONTENT, 0.2f));
            tname.setLayoutParams(new TableRow.LayoutParams
                                        (TableRow.LayoutParams.WRAP_CONTENT,
                                                TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
            tscore.setLayoutParams(new TableRow.LayoutParams
                                        (TableRow.LayoutParams.WRAP_CONTENT,
                                                TableRow.LayoutParams.WRAP_CONTENT, 0.3f));

            place.setTextSize(25);
            tname.setTextSize(25);
            tscore.setTextSize(25);
            place.setTextColor(Color.GREEN);
            tname.setTextColor(Color.GREEN);
            tscore.setTextColor(Color.GREEN);
            /*
                    place.setGravity(Gravity.LEFT);
                    tname.setGravity(Gravity.LEFT);
                    tscore.setGravity(Gravity.RIGHT);*/
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
