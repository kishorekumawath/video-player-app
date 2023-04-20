package com.example.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class videoslist extends AppCompatActivity {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<mediafile> arrayList = new ArrayList<>();
    private viedofileadapter videofileadpater;
    String folder_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoslist);
        folder_name = getIntent().getStringExtra("foldername");
        getSupportActionBar().setTitle(folder_name);
        recyclerView = findViewById(R.id.videofilelistRV2);
        swipeRefreshLayout =findViewById(R.id.swiperefresh2);

        showvideo();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showvideo();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void showvideo(){
        arrayList = getallviedo(videoslist.this,folder_name);
        videofileadpater = new viedofileadapter(arrayList,this);
        recyclerView.setAdapter(videofileadpater);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        videofileadpater.notifyDataSetChanged();
    }
    public ArrayList<mediafile> getallviedo(Context context,String foldername){
        ArrayList<mediafile> tempvideofile = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Video.Media.DATA+" like?";
        String[] selectionaarg = new String[]{"%"+foldername+"%"};
        String [] projection ={
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATE_ADDED
        };
        Cursor cursor = context.getContentResolver().query(uri,projection,selection,selectionaarg,null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                String id = cursor.getString(0);
                String title = cursor.getString(1);
                String path = cursor.getString(2);
                String size = cursor.getString(3);
                String duration = cursor.getString(4);
                String filename = cursor.getString(5);
                String dataadded = cursor.getString(6);
                mediafile videomodel = new mediafile(path,filename,size,title,duration,dataadded,id);
                tempvideofile.add(videomodel);
            }
            cursor.close();
        }
        return tempvideofile;
    }
}