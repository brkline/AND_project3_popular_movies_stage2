package com.example.and_project3_popular_movies_stage2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.and_project3_popular_movies_stage2.MovieRepository;
import com.example.and_project3_popular_movies_stage2.data.MovieDatabase;
import com.example.and_project3_popular_movies_stage2.models.Movie;

public class MovieDetailActivityViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;

    public MovieDetailActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public boolean isFavorite(String movieId) {
        return movieRepository.isFavorite(movieId);}

    public void insertFavoriteMovie(Movie movie) {
        movieRepository.insertFavoriteMovie(movie);
    }

    public void deleteFavoriteMovie(Movie movie) {
        movieRepository.deleteFavoriteMovie(movie);
    }

    public void updateFavoriteMovie(String movieId, boolean isFavorite) {
        movieRepository.updateFavoriteMovie(movieId, isFavorite);
    }
}
