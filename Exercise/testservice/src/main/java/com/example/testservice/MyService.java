package com.example.testservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private static final String TAG = "MyServiceTag";
    private LocalBinder myBinder = new LocalBinder();

    public class LocalBinder extends Binder{
        MyService getMyService(){
            return MyService.this;
        }
    }

    public MyService() {
    }

    public int add(int x,int y){
        return x + y;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i(TAG, "onBind: ");
        return myBinder;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int n = intent.getIntExtra("num",0);
        Log.i(TAG, "onStartCommand: 从Activity传入数据为" + n);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "onRebind: ");
        super.onRebind(intent);
    }
}
