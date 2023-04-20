package com.example.player;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class Allowaccess extends AppCompatActivity {
    public static final int STORAGE_PERMISSION = 1;
    public static final int REQUEST_PERMISSION_SETTING=12;
     Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allowaccess);

        SharedPreferences preferences = getSharedPreferences("accessallow",MODE_PRIVATE);
        String value = preferences.getString("allow","");
        if(value.equals("ok")){
            startActivity(new Intent(Allowaccess.this,MainActivity.class));
            finish();
        }
        else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("allow", "ok");
            editor.apply();
        }

        button = findViewById(R.id.allowpermission);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    startActivity(new Intent(Allowaccess.this,MainActivity.class));
                    finish();
                }
                else{
                    ActivityCompat.requestPermissions(Allowaccess.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION);

                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==STORAGE_PERMISSION){
            for(int i=0;i<permissions.length;i++){
                String per = permissions[i];
                if(grantResults[i]==PackageManager.PERMISSION_DENIED){
                    boolean showRationale = shouldShowRequestPermissionRationale(per);
                    if(!showRationale){
                        //if user click dont show again we use dilog box
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("APP PERMISSION").setMessage("allow permission ")
                                .setPositiveButton("Open Setting", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package",getPackageName(),null);
                                        intent.setData(uri);
                                        startActivityForResult(intent,REQUEST_PERMISSION_SETTING);
                                    }
                                }).create().show();


                    }
                    else {
                        //if user click deny button
                        ActivityCompat.requestPermissions(Allowaccess.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION);
                    }
                }
                else{
                    //if user click on allow button
                    startActivity(new Intent(Allowaccess.this,MainActivity.class));
                    finish();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        //if the user permission in the setting then the activity will be pause so we use the resume methond
        if( checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            startActivity(new Intent(Allowaccess.this,MainActivity.class));
            finish();
        }
    }
}