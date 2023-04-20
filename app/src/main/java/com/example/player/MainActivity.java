package com.example.player;

import static com.example.player.Allowaccess.REQUEST_PERMISSION_SETTING;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<mediafile> mediafiles = new ArrayList<>();
    private ArrayList<String> allfolderlist = new ArrayList<>();
    RecyclerView recyclerView ;
    videofolderadapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //checking if user remove the storage permission while he entered in the main activity
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "click on permission to allow Storage", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package",getPackageName(),null);
            intent.setData(uri);
            startActivityForResult(intent,REQUEST_PERMISSION_SETTING);
        }
        recyclerView = findViewById(R.id.videofolderlist);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);

        showfolder();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showfolder();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void showfolder(){
        //getting all the folder in the mediafiles arraylist variable
        mediafiles = fetchdata();
        //creating adapter
        adapter = new videofolderadapter(mediafiles,allfolderlist,this);
        recyclerView.setAdapter(adapter);//setting the adapter to the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        adapter.notifyDataSetChanged();// notify if any data or folder includes in the array list or adapter
    }
    public ArrayList<mediafile> fetchdata(){
        //fetching all the data from the files
        ArrayList<mediafile> mediafilesarraylist = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String [] projection ={
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATE_ADDED
        };
        Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
        if(cursor!=null&&cursor.moveToNext()){
            do{
                String id = cursor.getString(0);
                String title = cursor.getString(1);
                String path = cursor.getString(2);
                String size = cursor.getString(3);
                String duration = cursor.getString(4);
                String filename = cursor.getString(5);
                String dataadded = cursor.getString(6);
                int index = path.lastIndexOf("/");// taking the path where the path end with /
                String substring = path.substring(0,index);
                if(!allfolderlist.contains(substring)) {
                    allfolderlist.add(substring);          // adding the files names to the allfolderlist arraylist variable
                }
                mediafile videomodel = new mediafile(path ,filename,size,title,duration,dataadded,id);
                mediafilesarraylist.add(videomodel);
            }while (cursor.moveToNext());
        }
        return  mediafilesarraylist;
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.folder_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.rateus:

                break;
            case R.id.refresh:
                    finish();
                    startActivity(getIntent());
                break;
            case R.id.share:

                break;
        }
        return true;
    }
}