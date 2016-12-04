package com.whu.Gongyinchao.schoolservice.searchmodule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 龚银超 on 2016/10/22.
 */

public class RecordSQLiteOpenHelper extends SQLiteOpenHelper {
    private static String name = "temp.db";
    private static Integer version = 1;




    public RecordSQLiteOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table records(id integer primary key autoincrement,name varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
