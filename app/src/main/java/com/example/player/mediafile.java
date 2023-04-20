package com.example.player;

import android.os.Parcel;
import android.os.Parcelable;

public class mediafile implements Parcelable {
    private String path;
    private String filename;
   private String size;
   private String title;
   private String duration;
   private String dataadded;
   private String id;

    public mediafile(String path, String filename, String size, String title, String duration, String dataadded, String id) {
        this.path = path;
        this.filename = filename;
        this.size = size;
        this.title = title;
        this.duration = duration;
        this.dataadded = dataadded;
        this.id = id;
    }

    protected mediafile(Parcel in) {
        path = in.readString();
        filename = in.readString();
        size = in.readString();
        title = in.readString();
        duration = in.readString();
        dataadded = in.readString();
        id = in.readString();
    }

    public static final Creator<mediafile> CREATOR = new Creator<mediafile>() {
        @Override
        public mediafile createFromParcel(Parcel in) {
            return new mediafile(in);
        }

        @Override
        public mediafile[] newArray(int size) {
            return new mediafile[size];
        }
    };

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDataadded() {
        return dataadded;
    }

    public void setDataadded(String dataadded) {
        this.dataadded = dataadded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public mediafile(String path, String filename) {
//        this.path = path;
//        this.filename = filename;
//
//    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(filename);
        dest.writeString(size);
        dest.writeString(title);
        dest.writeString(duration);
        dest.writeString(dataadded);
        dest.writeString(id);
    }
}
