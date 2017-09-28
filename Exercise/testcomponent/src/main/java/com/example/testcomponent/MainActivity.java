package com.example.testcomponent;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

//    boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplicationContext(),"WiFi已开启",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"WiFi已关闭",Toast.LENGTH_LONG).show();
                }
            }
        });


//        final Chronometer ch=(Chronometer)findViewById(R.id.timer);
//
//        final Button btn=(Button)findViewById(R.id.buttonTimer);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(flag){
//                    ch.setBase(SystemClock.elapsedRealtime());
//                    ch.start();
//                    btn.setText("停止计时");
//
//                    flag=false;
//                }else{
//                    ch.stop();
//                    flag=true;
//                    btn.setText("开始计时");
//                }
//
//            }
//        });


    }

    public void onCheckboxClicked(View v){
        boolean checked = ((CheckBox)v).isChecked();

        switch(v.getId()){
            case R.id.checkbox_C:
                if(checked)
                    Toast.makeText(this,"选择C语言",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this,"取消选择C语言",Toast.LENGTH_LONG).show();
                break;
            case R.id.checkbox_java:
                if(checked)
                    Toast.makeText(this,"选择java语言",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this,"取消选择java语言",Toast.LENGTH_LONG).show();
                break;
            case R.id.checkbox_Python:
                if(checked)
                    Toast.makeText(this,"选择Python语言",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this,"取消选择Python语言",Toast.LENGTH_LONG).show();
        }
    }

    public void onRadioButtonClicked(View v){
        boolean checked = ((RadioButton)v).isChecked();
        TextView tv = (TextView)findViewById(R.id.textViewSex);

        switch (v.getId()){
            case R.id.RadioButtonMale:
                //if(checked)
                    tv.setText("您的性别为男");
                break;
            case R.id.RadioButtonFeMale:
                //if(checked)
                    tv.setText("您的性别为女");
                break;
            case R.id.RadioButtonNone:
                //if(checked)
                    tv.setText("您的性别未知，哈哈");
                break;
        }
    }
}
