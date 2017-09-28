package com.example.testsqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private WordsDBHelper dbHelper = null;
    private SQLiteDatabase db = null;
    private static final String TAG = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "主界面onCreate方法正在使用");
        dbHelper = new WordsDBHelper(this);
        db = dbHelper.getReadableDatabase();
        Log.i(TAG, "dbHelper、db已分配内存");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        updateListView();
    }

    //设置按钮事件监听方法
    public void onButtonClicked(View v){
        Log.i(TAG,v.getId()+"");
        EditText et_id = (EditText) findViewById(R.id.et_id);
        EditText et_word = (EditText) findViewById(R.id.et_word);
        EditText et_meaning = (EditText) findViewById(R.id.et_meaning);
        EditText et_sample = (EditText) findViewById(R.id.et_sample);

        String strId = et_id.getText().toString();
        String strWord = et_word.getText().toString();
        String strMeaning = et_meaning.getText().toString();
        String strSample = et_sample.getText().toString();

        Log.i(TAG,"Id = " + strId + ",word = " + strWord + ",meaning = " + strMeaning +
                ",sample = " + strSample);

        int btId = v.getId();
        switch(btId){
            case R.id.bt_add:
                if(insert(strWord,strMeaning,strSample))
                    Toast.makeText(this,"插入数据成功",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"插入失败，请检查记录是否存在",Toast.LENGTH_SHORT).show();
                updateListView();
                break;
            case R.id.bt_delete:
                if(delete(strId))
                    Toast.makeText(this,"删除数据成功",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"删除失败，请检查记录是否存在",Toast.LENGTH_SHORT).show();
                updateListView();
                break;
            case R.id.bt_update:
                if(update(strId,strWord,strMeaning,strSample))
                    Toast.makeText(this,"更新数据成功",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"更新失败，请检查记录是否存在",Toast.LENGTH_SHORT).show();
                updateListView();
                break;
            case R.id.bt_query:
                ArrayList<Map<String,String>> words;
                if((words = search(strWord)) != null)
                    Toast.makeText(this,"已查询到相关记录",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"没有找到相关结果",Toast.LENGTH_SHORT).show();
                updateListView(words);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dbHelper != null)
            dbHelper.close();
    }

    //更新列表显示信息
    public void updateListView(){
        ListView listView = (ListView) findViewById(R.id.lv);
        String[] columnNames = {Words.Word._ID, Words.Word.COLUMN_NAME_WORD,
                Words.Word.COLUMN_NAME_MEANING, Words.Word.COLUMN_NAME_SAMPLE};
        List<Map<String,String>> list = getAll();
        int[] tvIds = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4};
        ListAdapter listAdapter = new SimpleAdapter(this, list,
                R.layout.layout, columnNames, tvIds);
        listView.setAdapter(listAdapter);
    }

    //根据查询结果更新列表显示信息
    public void updateListView(List<Map<String,String>> words){
        ListView listView = (ListView) findViewById(R.id.lv);
        String[] columnNames = {Words.Word._ID, Words.Word.COLUMN_NAME_WORD,
                Words.Word.COLUMN_NAME_MEANING, Words.Word.COLUMN_NAME_SAMPLE};
        int[] tvIds = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4};
        ListAdapter listAdapter = new SimpleAdapter(this, words,
                R.layout.layout, columnNames, tvIds);
        listView.setAdapter(listAdapter);
    }

    //通过SQL语句插入单词
    private boolean insertWordSql(String strWord,String strMeaning,String strSample){
        String[] args = {strWord,strMeaning,strSample};
        String sql = "insert into words(word,meaning,sample) values(?,?,?)";
        try{
            db.execSQL(sql,args);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //通过insert方法插入单词
    private boolean insert(String strWord,String strMeaning,String strSample){
        try{
            ContentValues values = new ContentValues();
            values.put(Words.Word.COLUMN_NAME_WORD, strWord);
            values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
            values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);
            long newRowId;
            newRowId = db.insert(Words.Word.TABLE_NAME, null, values);
            if(newRowId == -1)
                return false;
            else
                return true;
        }
        catch(Exception e){
            return false;
        }
    }

    //通过SQL语句删除单词
    private boolean deleteWordSql(String strId){
        String sql = "delete from words where _id = ?";
        try{
            int a  = Integer.parseInt(strId);
            db.execSQL(sql, new Object[]{a});
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    //通过delete方法删除单词
    private boolean delete(String strId){
        try{
            String selection = Words.Word._ID + "=?";
            String[] selectionArgs = {strId};
            int num = db.delete(Words.Word.TABLE_NAME,selection,selectionArgs);
            if(num == 0)
                return false;
            else
                return true;
        }
        catch(Exception e){
            return false;
        }
    }

    //通过SQL语句修改单词
    private boolean updateWordSql(String strId,String strWord,String strMeaning,String strSample){
        Object[] args = {strWord,strMeaning,strSample,Integer.parseInt(strId)};
        String sql = "update words set word = ?,meaning = ?,sample = ? where _id = ?";
        try{
            db.execSQL(sql,args);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    //通过update方法修改单词
    private boolean update(String strId,String strWord,String strMeaning,String strSample){
        ContentValues values = new ContentValues();
        values.put(Words.Word.COLUMN_NAME_WORD,strWord);
        values.put(Words.Word.COLUMN_NAME_MEANING,strMeaning);
        values.put(Words.Word.COLUMN_NAME_SAMPLE,strSample);
        String selection = Words.Word._ID + "=?";
        String[] selectionArgs = {strId};
        try{
            int count = db.update(Words.Word.TABLE_NAME,values,selection,selectionArgs);
            if(count == 0)
                return false;
            else
                return true;
        }
        catch(Exception e){
            return false;
        }
    }

    //通过SQL语句查询单词
    private ArrayList<Map<String,String>> searchWordSql(String strWord){
        String sql = "select *  from words where word like ? order by word desc";
        String[] args = {"%" + strWord + "%"};
        Cursor c = db.rawQuery(sql,args);
        return convertCursor(c);
    }

    //通过query方法查询单词
    private ArrayList<Map<String,String>> search(String strWord){
        String[] projection = {Words.Word._ID, Words.Word.COLUMN_NAME_WORD,
                Words.Word.COLUMN_NAME_MEANING, Words.Word.COLUMN_NAME_SAMPLE};
        String sortOrder = Words.Word.COLUMN_NAME_WORD + " DESC";
        String selection = Words.Word.COLUMN_NAME_WORD + " LIKE ?";
        String[] selectionArgs = {"%" + strWord + "%"};
        Cursor c = db.query(Words.Word.TABLE_NAME, projection, selection,
                selectionArgs, null, null, sortOrder);
        if(c != null)
            return convertCursor(c);
        else
            return null;
    }

    //获得全部单词
    private ArrayList<Map<String,String>> getAll(){
        Cursor c = db.query(Words.Word.TABLE_NAME,null,null,null,null,null,null);
        if(c != null)
            return convertCursor(c);
        else
            return null;
    }

    //将游标对象转化为列表
    private ArrayList<Map<String,String>> convertCursor(Cursor cursor){
        ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
        while(cursor.moveToNext()){
            Map<String,String> word = new HashMap<String,String>();
            word.put(Words.Word._ID,String.valueOf(cursor.getInt(cursor.getColumnIndex(Words.Word._ID))));
            word.put(Words.Word.COLUMN_NAME_WORD,cursor.
                    getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD)));
            word.put(Words.Word.COLUMN_NAME_MEANING,cursor.
                    getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_MEANING)));
            word.put(Words.Word.COLUMN_NAME_SAMPLE,cursor.
                    getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_SAMPLE)));
            list.add(word);
        }
        return list;
    }
}
