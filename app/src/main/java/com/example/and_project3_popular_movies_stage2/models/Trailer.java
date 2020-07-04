package com.example.and_project3_popular_movies_stage2.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailer implements Parcelable {
    private String trailerKey;
    private String trailerSiteUrl;
    private String trailerTitle;

    public Trailer(String trailerKey, String trailerSiteUrl, String trailerTitle) {
        this.trailerKey = trailerKey;
        this.trailerSiteUrl = trailerSiteUrl;
        this.trailerTitle = trailerTitle;
    }

    protected Trailer(Parcel in) {
        trailerKey = in.readString();
        trailerSiteUrl = in.readString();
        trailerTitle = in.readString();
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    public String getTrailerSiteUrl() {
        return trailerSiteUrl;
    }

    public String getTrailerTitle() {
        return trailerTitle;
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trailerKey);
        dest.writeString(trailerSiteUrl);
        dest.writeString(trailerTitle);
    }
}
