package com.example.testintentservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bn_1 = (Button) findViewById(R.id.button);
        Button bn_2 = (Button) findViewById(R.id.button2);

        bn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntentService.startActionFoo(MainActivity.this, "1", "2");
            }
        });

        bn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntentService.startActionBaz(MainActivity.this, "3", "4");
            }
        });
    }
}
