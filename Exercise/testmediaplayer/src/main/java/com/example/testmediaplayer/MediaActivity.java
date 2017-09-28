package com.example.testmediaplayer;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MediaActivity extends AppCompatActivity {

    private MediaPlayService mpService = null;
    private static final String TAG = "serviceTag";
    private ServiceConnection serviceConnection = null;
    private boolean isInterrupted = false;
    private boolean isStart = true;
    private int nowTime = 0;
    private int totalTime = 0;
    private boolean isLooper = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        final ImageButton bn_style = (ImageButton) findViewById(R.id.bn_style);
        final ImageButton bn_start = (ImageButton) findViewById(R.id.bn_start);
        final ImageButton bn_before = (ImageButton) findViewById(R.id.bn_before);
        final ImageButton bn_next = (ImageButton) findViewById(R.id.bn_next);
        final TextView tv_start = (TextView) findViewById(R.id.start_time);
        final TextView tv_end = (TextView) findViewById(R.id.end_time);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        final TextView tv_musicName = (TextView) findViewById(R.id.tv_musicName);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.i(TAG, "handleMessage: " + msg.arg1);
                Log.i(TAG, "handleMessage: " + totalTime);
                setTime(tv_start, msg.arg1);
                seekBar.setProgress(msg.arg1);
                Log.i(TAG, "handleMessage: end");
            }
        };

        final Runnable worker = new Runnable() {
            @Override
            public void run() {
                boolean end = false;
                while(!end && !isInterrupted){
                    try{
                        Thread.sleep(1000);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    Message message = new Message();
                    Log.i(TAG, "run: start");
                    nowTime = mpService.getCurrentTime();
                    Log.i(TAG, "run: nowTime = " + nowTime);
                    message.arg1 = nowTime;
                    if(!isInterrupted)
                        handler.sendMessage(message);
                    if(nowTime == totalTime){
                        end = true;
                        message.arg1 = 0;
                    }
                }
            }
        };

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "onServiceConnected: start");
                mpService = ((MediaPlayService.MediaBinder)service).getService();
                Log.i(TAG, "onServiceConnected: connected");
                totalTime = mpService.getTotalTime();
                setTime(tv_end, totalTime);
                seekBar.setMax(totalTime);
                Log.i(TAG, "这里显示TotalTime的值：totalTime = " + totalTime);
                tv_musicName.setText(mpService.getMusicName());
                mpService.setOnMusicChangeListener(new MusicChangeListener() {
                    @Override
                    public void onMusicChanged() {
                        tv_musicName.setText(mpService.getMusicName());
                        totalTime = mpService.getTotalTime();
                        setTime(tv_end, totalTime);
                        bn_start.setImageResource(R.drawable.start);
                        isStart = true;
                        try{
                            Thread.sleep(6000);
                        }
                        catch(Exception e){e.printStackTrace();}
                        bn_start.setImageResource(R.drawable.pause);
                        isStart = false;
                        mpService.playMusic();
                    }
                });
                bn_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isStart){
                            isInterrupted = false;
                            bn_start.setImageResource(R.drawable.pause);
                            mpService.playMusic();
                            Thread thread = new Thread(null, worker, "WorkThread");
                            thread.start();
                            isStart = false;
                        }
                        else{
                            isStart = true;
                            bn_start.setImageResource(R.drawable.start);
                            mpService.pauseMusic();
                            isInterrupted = true;
                        }
                    }
                });

                bn_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mpService.getIndex() < 19){
                            int index = mpService.getIndex() + 1;
                            mpService.setIndex(index);
                            mpService.loadMusicFile(mpService.getMusic(index));
                            setTime(tv_end, mpService.getTotalTime());
                            tv_musicName.setText(mpService.getMusicName());
                            if(!isStart)
                                mpService.playMusic();
                            else{
                                seekBar.setProgress(0);
                                setTime(tv_start, 0);
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), "已经是最后一首歌了",
                                    Toast.LENGTH_SHORT);
                    }
                });

                bn_before.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mpService.getIndex() > 0){
                            int index = mpService.getIndex() - 1;
                            mpService.setIndex(index);
                            mpService.loadMusicFile(mpService.getMusic(index));
                            setTime(tv_end, mpService.getTotalTime());
                            tv_musicName.setText(mpService.getMusicName());
                            if(!isStart)
                                mpService.playMusic();
                            else{
                                seekBar.setProgress(0);
                                setTime(tv_start, 0);
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), "已经是第一首歌了",
                                    Toast.LENGTH_SHORT);
                    }
                });

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mpService.seekTo(seekBar.getProgress());
                    }
                });

                seekBar.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(isStart)
                            return true;
                        else
                            return false;
                    }
                });

                bn_style.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!isLooper){
                            bn_style.setImageResource(R.drawable.singlecycle);
                            isLooper = true;
                            mpService.setLooper(isLooper);
                            mpService.setMusicLooping(isLooper);
                            Toast.makeText(MediaActivity.this, "单曲循环", Toast.LENGTH_SHORT)
                                    .show();
                        }
                        else{
                            bn_style.setImageResource(R.drawable.sequence);
                            isLooper = false;
                            mpService.setLooper(isLooper);
                            mpService.setMusicLooping(isLooper);
                            Toast.makeText(MediaActivity.this, "顺序播放", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };

        Log.i(TAG, "onCreate: 即将绑定服务！");
        Intent intent = new Intent(MediaActivity.this, MediaPlayService.class);
        getApplicationContext().bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
        Log.i(TAG, "onCreate: 服务绑定成功！");

    }

    public void setTime(TextView tv, int time){
        int minute;
        int second;
        second = time/1000;
        minute = second/60;
        second = second % 60;
        if(minute<10)
            tv.setText("0" + minute + ":");
        else
            tv.setText(minute + "");
        if(second<10)
            tv.setText(tv.getText() + "0" + second);
        else
            tv.setText(tv.getText() +""+ second);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
