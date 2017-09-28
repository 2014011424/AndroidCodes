package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final  String Tag = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(Tag,"执行了onCreate方法。。。");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        Log.i(Tag,"执行了onStart方法。。。");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(Tag,"执行了onStop方法。。。");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(Tag,"执行了onDestroy方法。。。");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(Tag,"执行了onPause方法。。。");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i(Tag,"执行了onResume方法。。。");
        super.onResume();
    }
}
