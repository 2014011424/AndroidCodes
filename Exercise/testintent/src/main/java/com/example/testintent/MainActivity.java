package com.example.testintent;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 123;
    private static String[] PERMISSIONS_CALL = {
            "android.permission.CALL_PHONE"};


    public static void verifyCallPermissions(Activity activity) {

        try {
            //检测是否有打电话的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.CALL_PHONE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有打电话的权限，去申请打电话的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_CALL,REQUEST_CALL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyCallPermissions(this);

        final String act1 = Intent.ACTION_VIEW;
        final String act2 = Intent.ACTION_CALL;
        final String act3 = Intent.ACTION_DIAL;

        final String str1 = "geo:30，120";
        final String str2 = "https://www.baidu.com/";
        final String str3 = "tel:13269743763";

        Button bn = (Button) findViewById(R.id.button);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(act2);
//              intent.addCategory("");
                intent.setData(Uri.parse(str3));
                startActivity(intent);
            }
        });
    }
}
