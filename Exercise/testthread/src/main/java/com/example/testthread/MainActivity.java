package com.example.testthread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    private boolean isInterrupted = false;
    private ArrayList<Map<String,String>> list = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button bn_1 = (Button)findViewById(R.id.bn_1);
        final Button bn_2 = (Button)findViewById(R.id.bn_2);
        final TextView tv_mh = (TextView)findViewById(R.id.tv_mh);
        final TextView tv_ml = (TextView)findViewById(R.id.tv_ml);
        final TextView tv_sh = (TextView)findViewById(R.id.tv_sh);
        final TextView tv_sl = (TextView)findViewById(R.id.tv_sl);

        //0:mh  1:ml  2:sh  3:sl
        final Handler handler = new Handler(){
            @Override
           public void handleMessage(Message msg){
                Log.i(TAG, "handleMessage: ");
                switch(msg.arg2){
                    case 0:
                        Log.i(TAG, "handleMessage: handle mh");
                        tv_mh.setText(msg.arg1+"");
                        Log.i(TAG, "handleMessage: handle finish");
                        break;
                    case 1:
                        tv_ml.setText(msg.arg1+"");
                        break;
                    case 2:
                        tv_sh.setText(msg.arg1+"");
                        break;
                    case 3:
                        tv_sl.setText(msg.arg1+"");
                        break;
                    default:
                        Toast.makeText(MainActivity.this,"数据处理出错！",Toast.LENGTH_LONG);
                }
            }
        };

        final Runnable worker = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: ");
                boolean end = false;
                Map<Integer,Integer> map = new HashMap<Integer,Integer>();
                int mh = Integer.parseInt(tv_mh.getText().toString());
                int ml = Integer.parseInt(tv_ml.getText().toString());
                int sh = Integer.parseInt(tv_sh.getText().toString());
                int sl = Integer.parseInt(tv_sl.getText().toString());
                Log.i(TAG, "run: 数字处理");
                while(!isInterrupted && !end){
                    try{
                        Thread.sleep(1000);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    if(sl != 9)
                        sl++;
                    else{
                        sl = 0;
                        sh++;
                    }
                    if(sh == 6){
                        sh = 0;
                        ml++;
                    }
                    if(ml == 10){
                        ml = 0;
                        mh++;
                    }
                    if(mh == 6){
                        mh = 0;
                        ml = 0;
                        sh = 0;
                        sl = 0;
                        end = true;
                    }
                    Log.i(TAG, "run: mh="+mh+",ml="+ml+",sh="+sh+",sl="+sl);
                    map.put(0,mh);
                    map.put(1,ml);
                    map.put(2,sh);
                    map.put(3,sl);
                    if(!isInterrupted)
                        updateView(handler,map);
                    map.clear();
                }
            }
        };

        final Thread thread = new Thread(null, worker, "WorkThread");

        bn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = bn_1.getText().toString();
                switch(str){
                    case "开始":
                        isInterrupted = false;
                        Log.i(TAG, "onClick: 开始按钮");
                        thread.start();
                        bn_1.setText("计次");
                        bn_2.setText("暂停");
                        break;
                    case "计次":
                        String time = tv_mh.getText().toString() + tv_ml.getText().toString() +
                                ":" + tv_sh.getText().toString() + tv_sl.getText().toString();
                        updateListView(time);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "应用异常，请关闭后重试！",
                                Toast.LENGTH_LONG).show();
                }
            }
        });

        bn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = bn_2.getText().toString();
                switch(str){
                    case "重置":
                        tv_mh.setText("0");
                        tv_ml.setText("0");
                        tv_sh.setText("0");
                        tv_sl.setText("0");
                        list.clear();
                        updateListView();
                        break;
                    case "暂停":
                        bn_1.setText("开始");
                        bn_2.setText("重置");
                        isInterrupted = true;
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "应用异常，请关闭后重试！",
                                Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateView(Handler handler, Map<Integer,Integer> map){
        final Message[] msg = new Message[4];
        for(int i=0;i<4;i++)
            msg[i] = new Message();
        Log.i(TAG, "updateView: ");
        for(int i=0;i<map.size();i++){
            msg[i].arg2 = i;
            msg[i].arg1 = map.get(msg[i].arg2);
            Log.i(TAG, "sendMessage: arg1="+msg[1].arg1+",arg2="+msg[1].arg2);
            handler.sendMessage(msg[i]);
            Log.i(TAG, "SendMessage: finish");
        }
    }

    public void updateListView(String time){
        ListView lv = (ListView) findViewById(R.id.lv);
        int[] tvIds = {R.id.tv_show1, R.id.tv_show2};
        String[] names = {"num", "time"};
        Map<String,String> times = new HashMap<String,String>();
        times.put("num", "计次" + String.valueOf(list.size()+1) + ":  ");
        times.put("time", time);
        list.add(times);
        ListAdapter adapter = new SimpleAdapter(this, list, R.layout.layout, names, tvIds);
        lv.setAdapter(adapter);
    }

    public void updateListView(){
        ListView lv = (ListView) findViewById(R.id.lv);
        int[] tvIds = {R.id.tv_show1, R.id.tv_show2};
        String[] names = {"num", "time"};
        ListAdapter adapter = new SimpleAdapter(this, list, R.layout.layout, names, tvIds);
        lv.setAdapter(adapter);
    }
}
