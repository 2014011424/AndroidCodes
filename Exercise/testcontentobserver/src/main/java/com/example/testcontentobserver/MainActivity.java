package com.example.testcontentobserver;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "COTag";
    private SMSInboxObserver sio = null;
    private Uri sms_uri = null;
    private static final int REQUEST_SMS = 1;
    private static String[] PERMISSIONS_SMS = {
            "android.permission.READ_SMS"};


    public static void verifySMSPermissions(Activity activity) {

        try {
            //检测是否有查看短信的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.READ_SMS");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有查看短信的权限，去申请查看短信的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_SMS,REQUEST_SMS);
            }
//            updateListView(getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifySMSPermissions(this);

        sms_uri = Uri.parse("content://sms");
        sio = new SMSInboxObserver(new Handler());
        getContentResolver().registerContentObserver(sms_uri, true, sio);
        Log.i(TAG, "ContentObserver注册成功！");
        updateListView(getAll());
    }

    private final class  SMSInboxObserver extends ContentObserver{
        public SMSInboxObserver(Handler handler){
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange){
            updateListView(getAll());
        }
    }

    public void updateListView(Cursor cursor){
        ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
        while(cursor.moveToNext()){
            Map<String,String> word = new HashMap<String,String>();
            word.put("address",cursor.getString(cursor.getColumnIndex("address")));
            word.put("body",cursor.getString(cursor.getColumnIndex("body")));
            list.add(word);
        }
        String[] columnNames = {"address","body"};
        int[] tvIds = {R.id.tv_pnum,R.id.tv_mess};
        ListView lv = (ListView) findViewById(R.id.lv);
        ListAdapter adapter = new SimpleAdapter(this, list, R.layout.layout, columnNames, tvIds);
        lv.setAdapter(adapter);
    }

    public Cursor getAll(){
        Uri smsInbox = Uri.parse("content://sms/inbox");
        String[] projection = {"address","body","date"};
        String order = "date desc";
        Cursor cursor = getContentResolver().query(smsInbox, projection, null, null, order);
        return cursor;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(sio != null) {
            getContentResolver().unregisterContentObserver(sio);
            Log.i(TAG, "ContentObserver已注销！");
        }
        Log.i(TAG, "应用程序已停止运行！");
    }
}
