package com.example.player;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class viedofileadapter extends RecyclerView.Adapter<viedofileadapter.viewholder> {

        private ArrayList<mediafile> arrayList;
        private Context context;
        // private  videoclickinterface videointerface;

        public viedofileadapter(ArrayList<mediafile> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
            //this.videointerface = videointerface;
        }

        @NonNull
        @Override
        public viedofileadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.videofiles,parent,false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
            mediafile videomodel = arrayList.get(position);
            holder.filename.setText(videomodel.getFilename());
            Glide.with(context).load(new File(arrayList.get(position).getPath())).into(holder.thumbnail);
            String size = arrayList.get(position).getSize();
            holder.size.setText(android.text.format.Formatter.formatFileSize(context, Long.parseLong(size)));
            double millisecond = Double.parseDouble( arrayList.get(position).getDuration());
            holder.duration.setText(timeconvertion( (long) millisecond));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,videoplayercontrol.class);
                    intent.putExtra("path",videomodel.getPath());
                    intent.putExtra("filename ",videomodel.getFilename());
//                    intent.putExtra("postion",position);
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelableArrayList("videoarraylist",arrayList);
//                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class viewholder extends RecyclerView.ViewHolder{
            private TextView filename,duration,size;
            private ImageView thumbnail,more;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                filename = itemView.findViewById(R.id.filename);
                thumbnail = itemView.findViewById(R.id.img_thumbnail);
                size = itemView.findViewById(R.id.filesize);
                duration = itemView.findViewById(R.id.viedo_duration);
            }
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
