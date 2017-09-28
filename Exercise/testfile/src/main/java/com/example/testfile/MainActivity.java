package com.example.testfile;

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
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private static final String MyFileName = "myfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bw = (Button) findViewById(R.id.button_write);
        Button br = (Button) findViewById(R.id.button_read);

        bw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutputStream out = null;
                try{
                    FileOutputStream fos = openFileOutput(MyFileName,MODE_PRIVATE);
                    out = new BufferedOutputStream(fos);
                    final String content = "Hello World!";
                    try{
                        out.write(content.getBytes(StandardCharsets.UTF_8));
                        Toast.makeText(MainActivity.this,"Operation Successfully!",
                                Toast.LENGTH_SHORT);
                    }
                    finally {
                        if(out != null)
                            out.close();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream in = null;
                try{
                    FileInputStream fis = openFileInput(MyFileName);
                    in = new BufferedInputStream(fis);

                    int c;
                    StringBuilder str = new StringBuilder("");
                    try{
                        while((c = in.read()) != -1)
                            str.append((char) c);
                        Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
                    }
                    finally {
                        if(in != null)
                            in.close();
                    }
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,"No Such File!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
