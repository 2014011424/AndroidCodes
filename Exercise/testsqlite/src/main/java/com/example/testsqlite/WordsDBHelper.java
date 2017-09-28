package com.example.testsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by LiDafei on 2017/9/4.
 */

public class WordsDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "words.db";
    private static final int DATABASE_VERSION = 1;
    private static Context context;
    private static final String tag = "MyTag";

    private static final String SQL_CREATE_DATABASE = "CREATE TABLE " + Words.Word.TABLE_NAME +
            "(" +
            Words.Word._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Words.Word.COLUMN_NAME_WORD + " TEXT," +
            Words.Word.COLUMN_NAME_MEANING + " TEXT," +
            Words.Word.COLUMN_NAME_SAMPLE + " TEXT" +
            ")";
    private static final String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS "+ Words.Word.TABLE_NAME;

    public WordsDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(tag, "onCreate方法被调用");
        db.execSQL(SQL_CREATE_DATABASE);
        Log.i(tag, "数据库wordsdb及表words创建成功");
        Toast.makeText(context,"数据库wordsdb及表words创建成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_DATABASE);
        onCreate(db);
    }
}
