package com.sinjvf.tetris;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by sinjvf on 23.11.15.
 */
public class DBUser {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private static String queryCount;
    private static String having = "MIN("+Const.SCORE_COLUMN+")";
    private static String groupBy = Const.SCORE_COLUMN;
    private int type;
    DBUser( Context context, int type){
        dbHelper = new DBHelper(context);
        this.type=type;
        queryCount = "SELECT COUNT(*) FROM "+Const.TABLE_NAME[type];
        db = dbHelper.getWritableDatabase();

    }


    public void printResult(String name, int score){
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
      //  if (name.length()!=0)
        //    name=null;
        cv.put("name", name);
        cv.put("score", score);
        Cursor cursor2 = db.rawQuery(queryCount, null);
        if (cursor2!=null) {
            if (cursor2.moveToFirst()) {
                if(cursor2.getInt(0)<Const.N_BEST){
                    db.insert(Const.TABLE_NAME[type], null, cv);
                }
                else {

                    Cursor cursor = db.query(Const.TABLE_NAME[type],
                            null, null, null,
                            null, null, Const.SCORE_COLUMN+" DESC") ;
                    if (cursor!=null) {
                        if (cursor.moveToFirst()) {
                            db.update(Const.TABLE_NAME[type], cv, "id="+cursor.getInt(cursor.getColumnIndex("id")),null );
//Log.d(Const.LOG_TAG, "try to update" +"id="+cursor.getInt(cursor.getColumnIndex("id")));
                        }
                        cursor.close();
                    }

                }
            }
            cursor2.close();
        }
        db.close();
    }

    public void deleteRes(){
        db = dbHelper.getWritableDatabase();
        db.delete(Const.TABLE_NAME[type], null,null );
        db.close();
    }
    //shows is result one of the best
    public boolean isPrintedResult(int score){
        Integer minScore=0;
        Integer rowCount=0;

        db = dbHelper.getWritableDatabase();
        Cursor cursor2 = db.rawQuery(queryCount, null);
        if (cursor2!=null) {
            if (cursor2.moveToFirst()) {
                rowCount = cursor2.getInt(0);
            }
            cursor2.close();
        }
        if (rowCount<Const.N_BEST) {
            db.close();
            return true;}
        Cursor cursor = db.query(Const.TABLE_NAME[type],
                null, null, null,
                null, null, Const.SCORE_COLUMN) ;

        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                minScore = cursor.getInt(cursor.getColumnIndex(Const.SCORE_COLUMN));
            }
            cursor.close();
        }
        Log.d(Const.LOG_TAG, "min= "+minScore+" now= "+score);
        db.close();
        if (score> minScore) {
            return true;
        }
        return false;
    }

    public String bestRes(){
        String name="Sinjvf";

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(Const.TABLE_NAME[type],
                null, null, null,
                null, null, Const.SCORE_COLUMN+" DESC") ;

        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex(Const.NAME_COLUMN));
            }
            cursor.close();
        }
        db.close();
        return name;
    }

    //returns rows as Sring[]
    //int row : place,name, score
    public ArrayList<ArrayList<String>> allResults(){
        db = dbHelper.getWritableDatabase();
        ArrayList<ArrayList<String>> table=new ArrayList<ArrayList<String>>();
        ArrayList<String> row;
        Cursor cursor = db.query(Const.TABLE_NAME[type],
                null, null, null,
                null, null, Const.SCORE_COLUMN+" DESC") ;

        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                int nameColIndex = cursor.getColumnIndex("name");
                int scoreColIndex = cursor.getColumnIndex("score");
                do {
                    row=new ArrayList<String>();
                    row.add(cursor.getString(nameColIndex));
                    row.add(cursor.getString(scoreColIndex));
                    table.add(row);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return table;
    }
}
