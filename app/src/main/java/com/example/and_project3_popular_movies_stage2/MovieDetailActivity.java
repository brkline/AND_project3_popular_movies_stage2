package com.example.and_project3_popular_movies_stage2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and_project3_popular_movies_stage2.adapters.MovieVideosAdapter;
import com.example.and_project3_popular_movies_stage2.adapters.ReviewsAdapter;
import com.example.and_project3_popular_movies_stage2.models.Movie;
import com.example.and_project3_popular_movies_stage2.models.Review;
import com.example.and_project3_popular_movies_stage2.models.Video;
import com.example.and_project3_popular_movies_stage2.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String MOVIE_EXTRA = "movie";
    private static final int DEFAULT_POSITION = -1;
    private static final String RELEASE_DATE_SEPARATOR = "-";
    public final static String REVIEW_QUERY = "reviews";
    public final static String VIDEO_QUERY = "videos";

    private Movie movie;
    private static String movieId;
    private static List<Review> movieReview;
    private static List<Video> movieVideos;
    private ReviewsAdapter movieReviewsAdapter;
    private MovieDetailActivityViewModel movieDetailActivityViewModel;
    private MovieVideosAdapter movieVideosAdapter;
//    private MovieDetailActivityViewModel movieDetailActivityViewModel;

    @BindView(R.id.review_rv)
    RecyclerView reviewRecyclerView;
    @BindView(R.id.videos_rv)
    RecyclerView videoRecyclerView;
    @BindView(R.id.title_tv)
    TextView titleTextView;
    @BindView(R.id.poster_image_iv)
    ImageView posterImageView;
    @BindView(R.id.release_date_tv)
    TextView releaseDateTextView;
    @BindView(R.id.average_rating_tv)
    TextView averageRatingTextView;
    @BindView(R.id.synopsis_tv)
    TextView synopsisTextView;
    @BindView(R.id.no_reviews_available_tv)
    TextView reviewEmptyStateTextView;
    @BindView(R.id.no_videos_available_tv)
    TextView videoEmptyStateTextView;
    @BindView(R.id.toggle_favorite_btn)
    ToggleButton favoriteToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        MovieDetailActivityViewModel movieDetailActivityViewModel = new ViewModelProvider(this).get(MovieDetailActivityViewModel.class);

        // Code below based on Udacity Sandwich App starter code
        Intent intent = getIntent();
        if (null == intent) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
        }
        // We should have been passed a Movie object so retrieve it
        movie = intent.getParcelableExtra(MOVIE_EXTRA);
        if (null == movie) {
            closeOnError();
        }

        movieId = movie.getId();
        favoriteToggle.setChecked(false);

        // Favorite button logic and animation based on
        // https://medium.com/@rashi.karanpuria/create-beautiful-toggle-buttons-in-android-64d299050dfb
        setFavoriteToggle(movieId);
        favoriteToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    movieDetailActivityViewModel.insertFavoriteMovie(movie);
                    favoriteToggle.setChecked(true);
                } else {
                    movieDetailActivityViewModel.deleteFavoriteMovie(movie);
                    favoriteToggle.setChecked(false);
                }
            }
        });

        populateUI(movie);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Movie movieIn) {

        titleTextView.setText(movieIn.getTitle());

        String posterImageUrl = movieIn.getPosterPath();
        Picasso.get()
                .load(movieIn.getMoviePosterImage(posterImageUrl))
                .into(posterImageView);
        posterImageView.setContentDescription(movieIn.getTitle());

        // Extract the Release Year out of the Release Date so we can populate the
        // TextView
        String releaseDate = movieIn.getReleaseDate();
        if (!(releaseDate.isEmpty())) {
            String[] releaseDateStr = releaseDate.split(RELEASE_DATE_SEPARATOR);
            String releaseYear = releaseDateStr[0];
            releaseDateTextView.setText(releaseYear);
            // We didn't get a Release Date for some reason so populate the TextView with
            // "Unknown"
        } else {
            releaseDateTextView.setText(R.string.release_date_unknown);
        }

        averageRatingTextView.setText(getString(R.string.average_rating, movieIn.getVoteAverage()));
        synopsisTextView.setText(movieIn.getOverview());

        new getMovieReviewsTask().execute(movieId, REVIEW_QUERY);
        new getMovieVideosTask().execute(movieId, VIDEO_QUERY);
    }

    private void setFavoriteToggle(String movieId) {
        MovieDetailActivityViewModel movieDetailActivityViewModel =
                new ViewModelProvider(this).get(MovieDetailActivityViewModel.class);

        movieDetailActivityViewModel.isInDb(movieId).observe(this, returnMovie -> {

            if (!returnMovie.isEmpty()) {
                favoriteToggle.setChecked(true);
            }
        });
    }

    private class getMovieReviewsTask extends AsyncTask<String, Void, List<Review>> {

        @Override
        protected List<Review> doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }

            String movieId = strings[0];
            String queryType = strings[1];
            URL movieReviewUrl = NetworkUtils.urlBuilderQueryMovieId(movieId, queryType);

            try {
                String movieReviewJson = NetworkUtils.makeHttpRequest(movieReviewUrl);
                return NetworkUtils.parseMovieReviewJson(movieReviewJson);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Review> movieReviews) {
            if (!movieReviews.isEmpty()) {
                reviewEmptyStateTextView.setVisibility(View.GONE);
                movieReviewsAdapter = new ReviewsAdapter(MovieDetailActivity.this, movieReviews);
                RecyclerView.LayoutManager reviewLayoutManager = new LinearLayoutManager(MovieDetailActivity.this);
                reviewRecyclerView.setVisibility(View.VISIBLE);
                reviewRecyclerView.setLayoutManager(reviewLayoutManager);
                reviewRecyclerView.setAdapter(movieReviewsAdapter);
            } else {
                reviewEmptyStateTextView.setVisibility(View.VISIBLE);
                reviewRecyclerView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private class getMovieVideosTask extends AsyncTask<String, Void, List<Video>> {

        @Override
        protected List<Video> doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }

            String movieId = strings[0];
            String queryType = strings[1];
            URL movieVideoUrl = NetworkUtils.urlBuilderQueryMovieId(movieId, queryType);

            try {
                String movieTrailerJson = NetworkUtils.makeHttpRequest(movieVideoUrl);
                return NetworkUtils.parseMovieVideoJson(movieTrailerJson);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Video> movieVideos) {
            if (!movieVideos.isEmpty()) {
                videoEmptyStateTextView.setVisibility(View.GONE);
                movieVideosAdapter = new MovieVideosAdapter(MovieDetailActivity.this, movieVideos);
                RecyclerView.LayoutManager videoLayoutManager = new LinearLayoutManager(MovieDetailActivity.this);
                videoRecyclerView.setLayoutManager(videoLayoutManager);
                videoRecyclerView.setVisibility(View.VISIBLE);
                videoRecyclerView.setAdapter(movieVideosAdapter);
            } else {
                videoRecyclerView.setVisibility(View.GONE);
                videoEmptyStateTextView.setVisibility(View.VISIBLE);
            }
        }
    }

//    private class setFavoriteToggle extends AsyncTask<String, Void, Boolean> {
//
//        @Override
//        protected void onPostExecute(Boolean isFavorite) {
//            if (isFavorite) {
//                favoriteToggle.setChecked(true);
//            }
//        }
//
//        @Override
//        protected Boolean doInBackground(String... strings) {
//            if (strings.length == 0) {
//                return null;
//            }
//
//            String movieId = strings[0];
//
//            return movieDetailActivityViewModel.isFavorite(movieId);
//        }
//    }
}
