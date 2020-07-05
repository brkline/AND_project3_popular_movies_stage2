package com.example.and_project3_popular_movies_stage2.utils;

import android.util.Log;

import com.example.and_project3_popular_movies_stage2.models.Movie;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MovieHelper {
    private static final String LOG_TAG = MovieHelper.class.getSimpleName();
    private static final int URL_READ_TIMEOUT = 10000;
    private static final int URL_SET_CONNECTION_TIMEOUT = 15000;

    /**
     * Query the MovieDB dataset and return a list of {@link Movie} objects.
     */
    public static List<Movie> fetchMovies(String requestUrl) {

        // Create URL object
        URL url = NetworkUtils.createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = NetworkUtils.makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Return the list of {@link Movie}s
        return NetworkUtils.parseMovieJson(jsonResponse);
    }
}
