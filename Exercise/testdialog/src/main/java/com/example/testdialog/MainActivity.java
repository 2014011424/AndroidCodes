package com.example.testdialog;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int where;
    private boolean mulFlags[] = new boolean[] { true, false, false, false};
    private static final String TAG = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] items = getResources().getStringArray(R.array.items);

        Button button1 = (Button) findViewById(R.id.button_inf);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("这是一个提醒对话框！")
                        .setTitle("对话框")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"用户点击了确定按钮",Toast.LENGTH_LONG)
                                .show();
                    }
                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"用户点击了取消按钮",Toast.LENGTH_LONG)
                                .show();
                    }
                });
                builder.show();
            }
        });

        Button button2 = (Button) findViewById(R.id.button_list);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final  CharSequence[] items={"Item 1","Item 2","Item 3"};
//                final String[] items = getResources().getStringArray(R.array.items);
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("对话框")//对话框标题
                        .setIcon(android.R.drawable.ic_dialog_info)//设置图标
                        .setItems(R.array.items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TextView tv=(TextView)findViewById(R.id.textView);
                                tv.setText("用户选择了" + items[which]);
                            }
                        });
                builder.show();

            }
        });


        Button button3 = (Button) findViewById(R.id.button_sc);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("对话框")
                        .setIcon(R.mipmap.ic_launcher)
                        .setSingleChoiceItems(R.array.items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                where = which;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TextView tv=(TextView)findViewById(R.id.textView);
                                tv.setText("用户选择了" + items[where]);
                            }
                        });
                builder.show();
            }
        });


        Button btn = (Button) this.findViewById(R.id.button_mc);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 记录初始复选情况
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("对话框")
                        // 对话框标题
                        .setIcon(R.mipmap.ic_launcher)
                        // 设置图标
                        .setMultiChoiceItems(
                                R.array.items,
                                mulFlags,
                                new DialogInterface.OnMultiChoiceClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which, boolean isChecked) {
                                        mulFlags[which] = isChecked;
                                    }
                                });
                builder.setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                String str = "";
                                TextView tv=(TextView)findViewById(R.id.textView);
                                for(int i=0;i<mulFlags.length;i++)
                                    if(mulFlags[i])
                                        str = str + "/" + (i+1);
                                if(!str.equals(""))
                                    tv.setText("用户选择了item" + str);
                            }
                        });
                builder.show();
            }
        });


        Button button = (Button) findViewById(R.id.button_custom);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.login_dialog, null);
                builder.setView(layout)
                        .setTitle("Login")
                        // Add action buttons
                        .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.w(TAG, "点击了登录按钮");
                                // login
                                TextView tv=(TextView)findViewById(R.id.textView);
                                EditText etu = (EditText)layout.findViewById(R.id.editTextUserId);
                                EditText etp = (EditText)layout.findViewById(R.id.editTextPwd);
                                String userId = etu.getText().toString();
                                String password = etp.getText().toString();

                                Log.w(TAG, "id:" + userId + ",password:" + password);

                                if(userId.equals("lidafei") && password.equals("123456"))
                                    tv.setText("登录成功！");
                                else
                                    tv.setText("用户名或密码错误！");
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //cancel
                            }
                        });
                builder.show();
            }
        });

    }
}
