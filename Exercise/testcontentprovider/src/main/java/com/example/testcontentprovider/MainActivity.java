package com.example.testcontentprovider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ContentResolver cr;
    private static final String CONTENT_URI_STRING = "content://com.example.wordsprovider/word";
    private static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);
    private static final String TAG = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv_add = (TextView)findViewById(R.id.tv_add);
        final TextView tv_delete = (TextView)findViewById(R.id.tv_delete);
        final TextView tv_update = (TextView)findViewById(R.id.tv_update);
        final TextView tv_query = (TextView)findViewById(R.id.tv_query);

        cr = this.getContentResolver();

        insert();
        tv_add.setText("添加记录成功！");
        tv_add.setBackgroundColor(Color.GREEN);

        if(delete()){
            tv_delete.setText("删除记录成功！");
            tv_delete.setBackgroundColor(Color.GREEN);
        }
        else{
            tv_delete.setText("删除记录失败！");
            tv_delete.setBackgroundColor(Color.RED);
        }

        if(update()){
            tv_update.setText("更改记录成功！");
            tv_update.setBackgroundColor(Color.GREEN);
        }
        else{
            tv_update.setText("更改记录失败！");
            tv_update.setBackgroundColor(Color.RED);
        }

        tv_query.setText(query());
        tv_query.setBackgroundColor(Color.GREEN);
    }

    public void insert(){
        Uri newUri;
        ContentValues cv = new ContentValues();
        cv.put(Word.COLUMN_NAME_WORD, "Tiger");
        cv.put(Word.COLUMN_NAME_MEANING, "laohu");
        cv.put(Word.COLUMN_NAME_SAMPLE, "The Tiger is very strong.");
        newUri = cr.insert(CONTENT_URI, cv);
        Log.i(TAG, "The new URI is : " + newUri);
    }

    public boolean delete(){
        int count;
        String selection = Word._ID + "=?";
        String[] selectionArgs = {"5"};
        count = cr.delete(CONTENT_URI, selection, selectionArgs);
        if(count == 0)
            return false;
        else
            return true;
    }

    public boolean update(){
        int count;
        ContentValues cv = new ContentValues();
        cv.put(Word.COLUMN_NAME_SAMPLE, "It is an apple.");
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI,1);
        count = cr.update(newUri, cv, null, null);
        if(count == 0)
            return false;
        else
            return true;
    }

    public String query(){
        String str = "";
        Cursor cursor;
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI,1);
        String[] projection = {Word._ID,
                Word.COLUMN_NAME_WORD,
                Word.COLUMN_NAME_MEANING,
                Word.COLUMN_NAME_SAMPLE};
        cursor = cr.query(newUri, projection, null, null, null);
        while(cursor.moveToNext())
            str = str + cursor.getString(cursor.getColumnIndex(Word.COLUMN_NAME_SAMPLE)) + " ";
        return str;
    }

    public static abstract class Word implements BaseColumns {
        public static final String COLUMN_NAME_WORD = "word";
        public static final String COLUMN_NAME_MEANING = "meaning";
        public static final String COLUMN_NAME_SAMPLE = "sample";
    }
}
