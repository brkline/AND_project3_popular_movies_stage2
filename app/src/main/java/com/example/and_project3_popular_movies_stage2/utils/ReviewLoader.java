package com.example.and_project3_popular_movies_stage2.utils;

import android.content.Context;
import android.net.Uri;

import androidx.loader.content.AsyncTaskLoader;

import com.example.and_project3_popular_movies_stage2.models.Movie;
import com.example.and_project3_popular_movies_stage2.models.Review;

import java.util.List;

public class ReviewLoader extends AsyncTaskLoader<List<Review>> {

    /**
     * Query URL
     */
    private String url;

    /**
     * Constructs a new {@link ReviewLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public ReviewLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Review> loadInBackground() {
        if (url == null) {
            return null;
        }

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(Movie.THEMOVIEDB_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value.
        uriBuilder.appendQueryParameter("api_key", Movie.API_KEY);
        url = uriBuilder.toString();
        // Perform the network request, parse the response, and extract a list of movies
        // items.
        return ReviewHelper.fetchReviews(url);
    }
}
