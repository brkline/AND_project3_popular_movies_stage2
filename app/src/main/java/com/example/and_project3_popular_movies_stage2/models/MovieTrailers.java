package com.example.and_project3_popular_movies_stage2.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MovieTrailers implements Parcelable {
    private List<Trailer> trailers;

    public MovieTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public MovieTrailers(Parcel in) {in.readTypedList(trailers, Trailer.CREATOR);}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(trailers);
    }

    public static final Creator<MovieTrailers> CREATOR = new Creator<MovieTrailers>() {
        @Override
        public MovieTrailers createFromParcel(Parcel in) {
            return new MovieTrailers(in);
        }

        @Override
        public MovieTrailers[] newArray(int size) {
            return new MovieTrailers[size];
        }
    };
}
