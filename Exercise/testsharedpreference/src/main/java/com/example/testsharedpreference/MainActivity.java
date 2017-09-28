package com.example.testsharedpreference;

import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final static String sharedPreferenceName = "config";
    private final static String Key_UserName = "UserName";
    private final static String Key_LoginDate = "LoginDate";
    private final static String Key_UserType = "UserType";

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        editor = sp.edit();
        Button btw = (Button) findViewById(R.id.button_w);
        Button btr = (Button) findViewById(R.id.button_r);

        btw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String strLoginDate = simpleDateFormat.format(new Date());
                editor.putString(Key_UserName,"lisi");
                editor.putString(Key_LoginDate,strLoginDate);
                editor.putInt(Key_UserType,1);

                editor.apply();
            }
        });

        btr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUserName = sp.getString(Key_UserName, null);
                String strLoginDate = sp.getString(Key_LoginDate, null);
                int nUserType=sp.getInt(Key_UserType,0);
                if (strUserName != null && strLoginDate != null)
                    Toast.makeText(MainActivity.this, "用户名:" + strUserName + "登录时间:" +
                            strLoginDate+"用户类型:"+nUserType, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "无数据", Toast.LENGTH_LONG).show();

            }
        });
    }
}
