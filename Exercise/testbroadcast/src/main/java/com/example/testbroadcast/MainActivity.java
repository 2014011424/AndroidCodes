package com.example.testbroadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bn = (Button)findViewById(R.id.button);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.MY_BROADCAST");
                intent.putExtra("msg", "Welcome to use Broadcast!");
                sendBroadcast(intent);
                Toast.makeText(MainActivity.this, "消息已广播，等待接收者", Toast.LENGTH_LONG).show();
            }
        });
    }
}
