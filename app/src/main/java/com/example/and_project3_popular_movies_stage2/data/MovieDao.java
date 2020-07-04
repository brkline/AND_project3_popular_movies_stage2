package com.example.and_project3_popular_movies_stage2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.and_project3_popular_movies_stage2.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovie(Movie movie);

    @Delete
    void deleteFavoriteMovie(Movie movie);

    @Query("SELECT isFavorite FROM movies_table WHERE id = :movieId")
    boolean isFavorite(String movieId);

    @Query("DELETE FROM movies_table")
    void deleteAllFavorites();

    @Query("SELECT * FROM movies_table where id = :movieId")
    Movie getFavoriteMovieById(String movieId);

    @Query("SELECT * FROM movies_table")
    LiveData<List<Movie>> getAllFavoriteMovies();

    @Query("UPDATE movies_table SET isFavorite = :isFavorite WHERE id = :movieId")
    void updateFavoriteMovie(String movieId, boolean isFavorite);
}
