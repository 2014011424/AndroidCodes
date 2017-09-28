package com.example.testmediaplayer;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaPlayService extends Service {

    private static final String TAG = "serviceTag";
    private MediaBinder mediaBinder = new MediaBinder();
    private MediaPlayer mp = new MediaPlayer();
    private List<String> list = new ArrayList<String>();
    private int index = 0;
    private MusicChangeListener listener;
    private Map<String, String> musicMap = null;
    private boolean isLooper = false;

    public MediaPlayService() {

        list.add("music_1.mp3");
        list.add("music_2.mp3");
        list.add("music_3.mp3");
        list.add("music_4.mp3");
        list.add("music_5.mp3");
        list.add("music_6.mp3");
        list.add("music_7.mp3");
        list.add("music_8.aac");
        list.add("music_9.mp3");
        list.add("music_10.mp3");
        list.add("music_11.mp3");
        list.add("music_12.aac");
        list.add("music_13.aac");
        list.add("music_14.aac");
        list.add("music_15.aac");
        list.add("music_16.mp3");
        list.add("music_17.mp3");
        list.add("music_18.mp3");
        list.add("music_19.aac");
        list.add("music_20.aac");

        musicMap = new HashMap<String, String>();

        musicMap.put("music_1.mp3","白桦林");
        musicMap.put("music_2.mp3","滴答");
        musicMap.put("music_3.mp3","冬季到台北来看雨");
        musicMap.put("music_4.mp3","冬天的秘密");
        musicMap.put("music_5.mp3","后会无期");
        musicMap.put("music_6.mp3","化身孤岛的鲸");
        musicMap.put("music_7.mp3","流年");
        musicMap.put("music_8.aac","梦里水乡");
        musicMap.put("music_9.mp3","暖暖");
        musicMap.put("music_10.mp3","是谁在敲打我窗");
        musicMap.put("music_11.mp3","酸酸甜甜就是我");
        musicMap.put("music_12.aac","天空");
        musicMap.put("music_13.aac","蜗牛与黄鹂鸟");
        musicMap.put("music_14.aac","我依然爱你");
        musicMap.put("music_15.aac","我愿意");
        musicMap.put("music_16.mp3","有形的翅膀");
        musicMap.put("music_17.mp3","遇见");
        musicMap.put("music_18.mp3","愿得一人心");
        musicMap.put("music_19.aac","烛光里的妈妈");
        musicMap.put("music_20.aac","走过咖啡屋");
    }

    public class MediaBinder extends Binder {
        MediaPlayService getService(){
            Log.i(TAG, "getService: ");
            return MediaPlayService.this;
        }
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.i(TAG, "onBind: start");
        // TODO: Return the communication channel to the service.
        loadMusicFile(list.get(index));
//        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        Uri uri = Uri.parse("http://10.18.2.179:8081/nexus/service/local/repositories/thirdparty/content/music/music_1.mp3");
//        loadMusicByURL(uri);
        Log.i(TAG, "onBind: 文件操作被跳过？是/否");

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(!isLooper){
                    index++;
                    if(index<20){
                        loadMusicFile(list.get(index));
                        if(listener != null)
                            listener.onMusicChanged();
                    }
                    else
                        index = 0;
                }
            }
        });

        return mediaBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
//        stopMusic();
        mp.release();
        return super.onUnbind(intent);
    }

    public void setOnMusicChangeListener(MusicChangeListener listener){
        this.listener = listener;
    }

    public void playMusic(){
        mp.start();
    }

    public void pauseMusic(){
        mp.pause();
    }

    public void stopMusic(){
        mp.stop();
    }

    public void seekTo(int time){
        mp.seekTo(time);
    }

    public int getTotalTime(){
        Log.i(TAG, "getTotalTime: 执行getDuration操作！");
        return mp.getDuration();

    }

    public String getMusicName(){
        return musicMap.get(list.get(index));
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMusic(int i){
        return list.get(i);
    }

    public int getCurrentTime(){
        return mp.getCurrentPosition();
    }

    public void loadMusicFile(String str){
        try{
            mp.reset();
            AssetManager manager = getAssets();
            AssetFileDescriptor afd = manager.openFd(str);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
            mp.setLooping(isLooper);
            Log.i(TAG, "onBind: 文件加载完成！");
        }
        catch(Exception e){
            Log.i(TAG, "Exception: 文件加载失败！");
            Toast.makeText(getApplicationContext(), "找不到文件！", Toast.LENGTH_SHORT).show();
        }
    }

    public void setLooper(boolean isLooper){
        this.isLooper = isLooper;
    }

    public void setMusicLooping(boolean isLooper){
        mp.setLooping(isLooper);
    }

//    public void loadMusicByURL(Uri url){
//        try{
//            mp.reset();
//            mp.setDataSource(getApplicationContext(), url);
//            mp.prepare();
//            mp.setLooping(isLooper);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
