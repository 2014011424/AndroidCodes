package com.example.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class HelloActivity extends AppCompatActivity {

    private final String tag  = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        Integer i = 3;
        String strMsg = "hi";
        Log.v(tag, strMsg);
    }
}
