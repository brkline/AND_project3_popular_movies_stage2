package com.example.and_project3_popular_movies_stage2.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.and_project3_popular_movies_stage2.models.Movie;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
    private static final String DATABASE_NAME = "movie_database";

    private static volatile MovieDatabase INSTANCE;
    public static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecture = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MovieDatabase getDatabase(Context context) {
        if (null == INSTANCE) {
            synchronized (MovieDatabase.class) {
                if (null == INSTANCE) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DATABASE_NAME).build();
                }
            }
        }

        return INSTANCE;
    }
}
