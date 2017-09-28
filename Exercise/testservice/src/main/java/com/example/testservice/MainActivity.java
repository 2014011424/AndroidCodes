package com.example.testservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyServiceTag";
    MyService myService=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button bn_start = (Button) findViewById(R.id.bn_start);
        final Button bn_stop = (Button) findViewById(R.id.bn_stop);
        final Button bn_use = (Button) findViewById(R.id.bn_use);

        bn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: start");
                Intent intent = new Intent(MainActivity.this, MyService.class);
//                intent.putExtra("num",10);
//                startService(intent);
//                Log.i(TAG, "服务已启动！");
                bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
            }
        });

        bn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: stop");
//                Intent intent = new Intent(MainActivity.this, MyService.class);
//                stopService(intent);
                unbindService(serviceConnection);
                Log.i(TAG, "服务已停止！");
            }
        });

        bn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myService != null)
                    Toast.makeText(MainActivity.this,
                            "myService计算结果为：" + myService.add(1,1), Toast.LENGTH_LONG).show();
            }
        });
    }

    final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.v(TAG,"onServiceConnected");
            myService=((MyService.LocalBinder)iBinder).getMyService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.v(TAG,"onServiceDisconnected");
        }
    } ;

}
