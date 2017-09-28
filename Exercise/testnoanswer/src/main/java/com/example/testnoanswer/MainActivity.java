package com.example.testnoanswer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bn = (Button) findViewById(R.id.button);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long endTime = System.currentTimeMillis() + 10*1000;
                while(System.currentTimeMillis()<endTime)
                    synchronized(this){
                    try{
                        wait();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
