package com.example.and_project3_popular_movies_stage2.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable {
    private String videoId;
    private String videoKey;
    private String videoSite;
    private String videoTitle;
    private String videoType;
    public static final String VIDEO_TYPE_KEY = "type";
    public static final String VIDEO_TITLE_KEY = "name";
    public static final String VIDEO_SITE_KEY = "site";
    public static final String VIDEO_KEY_KEY = "key";

    public Video(String videoId, String videoKey, String videoSite, String videoTitle, String videoType) {
        this.videoId = videoId;
        this.videoKey = videoKey;
        this.videoSite = videoSite;
        this.videoTitle = videoTitle;
        this.videoType = videoType;
    }

    protected Video(Parcel in) {
        videoId = in.readString();
        videoKey = in.readString();
        videoSite = in.readString();
        videoTitle = in.readString();
        videoType = in.readString();
    }

    public String getVideoKey() {
        return videoKey;
    }

    public String getVideoSite() {
        return videoSite;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoId);
        dest.writeString(videoKey);
        dest.writeString(videoSite);
        dest.writeString(videoTitle);
        dest.writeString(videoType);
    }
}
