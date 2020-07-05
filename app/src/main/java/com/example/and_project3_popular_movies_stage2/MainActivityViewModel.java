package com.example.and_project3_popular_movies_stage2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.and_project3_popular_movies_stage2.models.Movie;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    private LiveData<List<Movie>> favoriteMovies;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        MovieRepository movieRepository = new MovieRepository(application);
        favoriteMovies = movieRepository.getFavoriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovies;
    }
}
