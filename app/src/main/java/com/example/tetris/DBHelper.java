package com.example.tetris;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sinjvf on 20.11.15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, Const.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(Const.LOG_TAG+22, "--- onCreate database ---");
        // создаем таблицу с полями
        for (int i=0;i<Const.TABLE_NAME.length;i++){
        db.execSQL("create table "+ Const.TABLE_NAME[i] +"("
                + "id integer primary key autoincrement, "
                + Const.NAME_COLUMN+" text, "
                + Const.SCORE_COLUMN+" integer" + ");");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i=0;i<Const.TABLE_NAME.length;i++) {
            db.execSQL("create table if not exists " + Const.TABLE_NAME[i] +"("
                    + "id integer primary key autoincrement, "
                    + Const.NAME_COLUMN+" text, "
                    + Const.SCORE_COLUMN+" integer" + ");");;
        }
    }


}

