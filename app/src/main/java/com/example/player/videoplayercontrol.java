package com.example.player;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.TelephonyNetworkSpecifier;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class videoplayercontrol extends AppCompatActivity {
    private TextView videotime;
    private SeekBar seekBar;
    private RelativeLayout Rcontrols,videoRL;
    private ImageView  back,forward,playpause,backintent;
    private String path;
    private VideoView videoView;
    boolean isopen = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayercontrol);
        path = getIntent().getStringExtra("path");
        videoRL= findViewById(R.id.videoRL);
        Rcontrols = findViewById(R.id.Rlcontrols);
        videotime = findViewById(R.id.timeduration);
         videoView = findViewById(R.id.videoplayer);
         seekBar  = findViewById(R.id.seekbar);
         back= findViewById(R.id.rewind);
         forward= findViewById(R.id.forward10);
         playpause = findViewById(R.id.pause);
         backintent = findViewById(R.id.backvideo);
         videoView.setVideoURI(Uri.parse(path));
         videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(videoView.getDuration());
                videoView.start();
            }
         });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.seekTo(videoView.getCurrentPosition()-10000);
            }
        });
        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoView.isPlaying()){
                    videoView.pause();
                    playpause.setImageDrawable(getResources().getDrawable(R.drawable.ic_round_play));
                }
                else {
                    videoView.start();
                    playpause.setImageDrawable(getResources().getDrawable(R.drawable.ic_round_pause));
                }
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.seekTo(videoView.getCurrentPosition()+10000);
            }
        });
        videoRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isopen){
                    hidecontrol();
                    isopen=false;
                }
                else {
                    showcontrol();
                    isopen=true;
                }
            }
        });
        backintent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });
        SetHandler();
        initializeseekbar();

    }
    public void SetHandler(){
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(videoView.getDuration()>0){
                    int cupos = videoView.getCurrentPosition();
                    seekBar.setProgress(cupos);
                    double millesecond = Double.parseDouble(String.valueOf(videoView.getDuration()-cupos));
                    videotime.setText(""+timeconvertion((long) millesecond));
                }
                handler.postDelayed(this,0);
            }
        };
        handler.postDelayed(runnable,500);
    }
    public void hidecontrol(){
        Rcontrols.setVisibility(View.GONE);
        final Window window = getWindow();
        if(window==null){
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        View decorView =window.getDecorView();
        if(decorView!=null){
            int uioption = decorView.getSystemUiVisibility();
            if(Build.VERSION.SDK_INT >= 14){
                uioption |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            if(Build.VERSION.SDK_INT >= 16){
                uioption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            if(Build.VERSION.SDK_INT >= 19){
                uioption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    public void showcontrol(){
        Rcontrols.setVisibility(View.VISIBLE);
        final Window window = getWindow();
        if(window==null){
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        View decorView =window.getDecorView();
        if(decorView!=null){
            int uioption = decorView.getSystemUiVisibility();
            if(Build.VERSION.SDK_INT >= 14){
                uioption &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            if(Build.VERSION.SDK_INT >= 16){
                uioption &= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            if(Build.VERSION.SDK_INT >= 19){
                uioption &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
        }
    }
    public void initializeseekbar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(seekBar.getId()==R.id.seekbar){
                    if(fromUser){
                        videoView.seekTo(progress);
                        videoView.start();
                        int currentpos = videoView.getCurrentPosition();
                        double millesecond = Double.parseDouble(String.valueOf(videoView.getDuration()-currentpos));
                        videotime.setText(""+timeconvertion((long) millesecond));
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public String timeconvertion(long size){
        String videotime;
        int duration = (int) size;
        int hrs = (duration/3600000);
        int min = (duration/60000)%60000;
        int sec = duration%60000/1000;
        if(hrs>0){
            videotime = String.format("%02d:%02d:%02d",hrs,min,sec);

        }
        else{
            videotime = String.format("%02d:%02d",min,sec);
        }
        return  videotime;
    }
}