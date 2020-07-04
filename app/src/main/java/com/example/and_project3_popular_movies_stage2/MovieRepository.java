package com.example.and_project3_popular_movies_stage2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.and_project3_popular_movies_stage2.data.MovieDao;
import com.example.and_project3_popular_movies_stage2.data.MovieDatabase;
import com.example.and_project3_popular_movies_stage2.models.Movie;

import java.util.List;
import java.util.concurrent.ExecutorService;

class MovieRepository {

    private MovieDao movieDao;
    private LiveData<List<Movie>> favoriteMovies;
    private ExecutorService executorService;

    public MovieRepository(Application application) {
        movieDao = MovieDatabase.getDatabase(application).movieDao();
        favoriteMovies = movieDao.getAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void insertFavoriteMovie(Movie movie) {
        MovieDatabase.databaseWriteExecture.execute(() -> {
            movieDao.insertFavoriteMovie(movie);
        });
    }

    public void deleteFavoriteMovie(Movie movie) {
        MovieDatabase.databaseWriteExecture.execute(() -> {
            movieDao.deleteFavoriteMovie(movie);
        });
    }

//    public void updateFavoriteMovie(String movieId, boolean isFavorite) {
//        MovieDatabase.databaseWriteExecture.execute(() -> {
//            movieDao.updateFavoriteMovie(movieId, isFavorite);
//        });
//    }

    public LiveData<List<Movie>> isInDb(String movieId) {
        return movieDao.isInDb(movieId);
    }
}
