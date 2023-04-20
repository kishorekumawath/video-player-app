package com.example.player;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class videofolderadapter extends RecyclerView.Adapter<videofolderadapter.viewholder> {
   private ArrayList <mediafile> folderlist;
    private ArrayList<String> folderpath;
    private Context context;

    public videofolderadapter(ArrayList<mediafile> folderlist, ArrayList<String> folderpath, Context context) {
        this.folderlist = folderlist;
        this.folderpath = folderpath;
        this.context = context;
    }

    public videofolderadapter(ArrayList<mediafile> folderlist, Context context) {
        this.folderlist = folderlist;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.videoitem,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        mediafile mf = folderlist.get(position);
        int indexpath = folderpath.get(position).lastIndexOf("/");
        String nameoffolder = folderpath.get(position).substring(indexpath+1);
        holder.foldername.setText(nameoffolder);
        holder.folderpathview.setText(folderpath.get(position));
        holder.noofvideo.setText("5 video");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, videoslist.class);
                intent.putExtra("foldername",nameoffolder);
                intent.putExtra("path",mf.getPath());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return folderpath.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView foldername,folderpathview,noofvideo;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            folderpathview = itemView.findViewById(R.id.folderpath);
            foldername = itemView.findViewById(R.id.foldername);
            noofvideo= itemView.findViewById(R.id.NoOfVideo);
        }
    }
}
