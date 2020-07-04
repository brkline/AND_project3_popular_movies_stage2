package com.example.and_project3_popular_movies_stage2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.and_project3_popular_movies_stage2.models.Movie;

import java.util.List;

public class MovieDetailActivityViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;

    public MovieDetailActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public LiveData<List<Movie>> isInDb(String movieId) {
        return movieRepository.isInDb(movieId);}

    public void insertFavoriteMovie(Movie movie) {
        movieRepository.insertFavoriteMovie(movie);
    }

    public void deleteFavoriteMovie(Movie movie) {
        movieRepository.deleteFavoriteMovie(movie);
    }

//    public void updateFavoriteMovie(String movieId, boolean isFavorite) {
//        movieRepository.updateFavoriteMovie(movieId, isFavorite);
//    }
}
