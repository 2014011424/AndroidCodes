package com.example.testfragment;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.testfragment.word.WordsContent;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener ,
DetailFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListFragmentInteraction(WordsContent.WordItem item) {
        Bundle args = new Bundle();
        args.putString("id",item.getId());
        args.putString("content",item.getContent());
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        getFragmentManager().beginTransaction().replace(R.id.worddetail,fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
