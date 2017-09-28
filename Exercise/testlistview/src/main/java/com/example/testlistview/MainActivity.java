package com.example.testlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
//    private final static String PRODUCT = "product";
//    private final static String PRICE = "price";
//    private final static String CONFIG = "configuration";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager_2;
    private RecyclerView.LayoutManager layoutManager_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/***
 * 简单的ListView的使用
 */
//        String[] array={"小米","华为","魅族","锤子"};
//        ListView list=(ListView)findViewById(R.id.list1);
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.item,array);
//        list.setAdapter(adapter);

  /**
   * 下拉列表的使用
   * */
//        Spinner spinner = (Spinner)findViewById(R.id.spinner);
//        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.items,
//                android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position>=0)
//                    Toast.makeText(MainActivity.this,parent.getItemAtPosition(position).toString(),
//                            Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


/**
 * 复杂的ListView，每一项有三个TextView
 * */
//        String[] product = {"小米Note","华为荣耀7","魅族MX5","锤子T1"};
//        String[] price = {"1999","1999","1999","2480"};
//        String[] config = {"高通骁龙 801，3GB RAM，16GB ROM","麒麟 935，3GB RAM，16GB ROM",
//                "联发科（MTK)Helio X10 Turbo，3GB RAM，32GB ROM","高通骁龙 801，2GB RAM，32GB ROM"};
//
//        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
//        //Map<String,Object> item = new HashMap<String,Object>();
//
//        for(int i = 0;i<product.length;i++){
//            Map<String,Object> item = new HashMap<String,Object>();
//            item.put(PRODUCT,product[i]);
//            item.put(PRICE,price[i]);
//            item.put(CONFIG,config[i]);
//            items.add(item);
//            //item.clear();    //此处不能使用clear方法清除item中的映射，因为“=”是浅拷贝
//        }
//        SimpleAdapter adapter = new SimpleAdapter(this,items,R.layout.item_another,
//                 new String[]{PRODUCT,PRICE,CONFIG},new int[]{R.id.tvProduct,
//                R.id.tvPrice,R.id.tvConfiguration});
//        list.setAdapter(adapter);
        /**
         * RecyclerView的使用
         * */
        mRecyclerView = (RecyclerView)findViewById(R.id.recylerView);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager_2 = new GridLayoutManager(this,4);
        layoutManager_3 = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager_3);
        String[] dataset = {"北京","上海","深圳","广州"};
        adapter = new MyAdapter(dataset,MainActivity.this);
        mRecyclerView.setAdapter(adapter);
    }
}
