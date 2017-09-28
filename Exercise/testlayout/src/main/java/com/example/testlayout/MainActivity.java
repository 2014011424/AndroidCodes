package com.example.testlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOne = (Button) findViewById(R.id.buttonOne);
        final TextView tv = (TextView) findViewById(R.id.textView);

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv.getText().equals("0"))
                    tv.setText("1");
                else
                    tv.setText(tv.getText()+"1");
            }
        });
    }
}
