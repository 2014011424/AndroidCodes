package com.example.testmediaplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyTag";
    private Map<String, String> musicMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View main = findViewById(R.id.mainlayout);
        main.getBackground().setAlpha(70);

        String[] musicNames = {"白桦林","滴答","冬季到台北来看雨","冬天的秘密","后会无期",
                "化身孤岛的鲸","流年","梦里水乡","暖暖","是谁在敲打我窗","酸酸甜甜就是我",
                "天空","蜗牛与黄鹂鸟","我依然爱你","我愿意","有形的翅膀","遇见",
                "愿得一人心","烛光里的妈妈","走过咖啡屋"};

        musicMap.put("白桦林","music_1.mp3");
        musicMap.put("滴答","music_2.mp3");
        musicMap.put("冬季到台北来看雨","music_3.mp3");
        musicMap.put("冬天的秘密","music_4.mp3");
        musicMap.put("后会无期","music_5.mp3");
        musicMap.put("化身孤岛的鲸","music_6.mp3");
        musicMap.put("流年","music_7.mp3");
        musicMap.put("梦里水乡","music_8.aac");
        musicMap.put("暖暖","music_9.mp3");
        musicMap.put("是谁在敲打我窗","music_10.mp3");
        musicMap.put("酸酸甜甜就是我","music_11.mp3");
        musicMap.put("天空","music_12.aac");
        musicMap.put("蜗牛与黄鹂鸟","music_13.aac");
        musicMap.put("我依然爱你","music_14.aac");
        musicMap.put("我愿意","music_15.aac");
        musicMap.put("有形的翅膀","music_16.mp3");
        musicMap.put("遇见","music_17.mp3");
        musicMap.put("愿得一人心","music_18.mp3");
        musicMap.put("烛光里的妈妈","music_19.aac");
        musicMap.put("走过咖啡屋","music_20.aac");

        ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
        for(int i=0;i<musicNames.length;i++){
            Map<String,String> map = new HashMap<String, String>();
            map.put("musicName", musicNames[i]);
            list.add(map);
        }
        updateListView(list);
    }

    private void updateListView(ArrayList<Map<String,String>> list){
        int[] tvs_id = {R.id.tv_music};
        String[] musicNames = {"musicName"};
        ListView lv = (ListView) findViewById(R.id.lv);
        ListAdapter adapter = new SimpleAdapter(this, list, R.layout.layout, musicNames, tvs_id);
        lv.setAdapter(adapter);
    }

}
