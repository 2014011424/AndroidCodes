package com.example.testfilesd;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private static final String MyFileName = "myfile";
    private static final String state = Environment.getExternalStorageState();

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(this);

        Button bt = (Button) findViewById(R.id.button_test);
        final Button bw = (Button) findViewById(R.id.button_write);
        final Button br = (Button) findViewById(R.id.button_read);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExternalStorageWritable()){
                    Toast.makeText(MainActivity.this,"SD卡可读写，请进行下一步的操作",
                            Toast.LENGTH_LONG).show();
                    br.setEnabled(true);
                    bw.setEnabled(true);
                }
                else
                    Toast.makeText(MainActivity.this,"SD卡不可读写，请检查权限后重试",
                            Toast.LENGTH_LONG).show();
            }
        });

        bw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutputStream out = null;
                try{
                    File file = Environment.getExternalStorageDirectory();
                    String fd = file.getCanonicalPath() + File.separator +MyFileName;
                    bw.setText(fd);
                    FileOutputStream fos = new FileOutputStream(fd);
                    out = new BufferedOutputStream(fos);
                    String content = "Hello World!\n";
                    try{
                        out.write(content.getBytes(StandardCharsets.UTF_8));
                        Toast.makeText(MainActivity.this,"写入内容成功！",
                                Toast.LENGTH_LONG).show();
                    }
                    finally {
                        if(out != null)
                            out.close();
                    }
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream in = null;
                try{
                    File file = Environment.getExternalStorageDirectory();
                    String fd = file.getCanonicalPath() + "/" +MyFileName;
                    FileInputStream fis = new FileInputStream(fd);
                    in = new BufferedInputStream(fis);

                    int c;
                    StringBuilder str = new StringBuilder("");
                    try{
                        while((c = in.read()) != -1)
                            str.append((char)c);
                        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
                    }
                    finally {
                        if(in != null)
                            in.close();
                    }
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this,"没有找到文件，请创建文件后重试",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * *检查是否对SD卡具有写权限
     * */
    public boolean isExternalStorageWritable(){
        if(state.equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 检查是否对SD卡具有读权限
     * **/
    public boolean isExternalStorageReadable(){
        if(state.equals(Environment.MEDIA_MOUNTED)
                || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
            return true;
        else
            return false;
    }
}
