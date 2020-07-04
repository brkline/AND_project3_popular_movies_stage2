package com.example.and_project3_popular_movies_stage2.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MovieVideos implements Parcelable {
    private List<Video> videos;

    public MovieVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public MovieVideos(Parcel in) {in.readTypedList(videos, Video.CREATOR);}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(videos);
    }

    public static final Creator<MovieVideos> CREATOR = new Creator<MovieVideos>() {
        @Override
        public MovieVideos createFromParcel(Parcel in) {
            return new MovieVideos(in);
        }

        @Override
        public MovieVideos[] newArray(int size) {
            return new MovieVideos[size];
        }
    };
}
