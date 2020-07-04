package com.example.and_project3_popular_movies_stage2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and_project3_popular_movies_stage2.adapters.ReviewsAdapter;
import com.example.and_project3_popular_movies_stage2.data.MovieDatabase;
import com.example.and_project3_popular_movies_stage2.models.Movie;
import com.example.and_project3_popular_movies_stage2.models.Review;
import com.example.and_project3_popular_movies_stage2.models.Trailer;
import com.example.and_project3_popular_movies_stage2.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String MOVIE_EXTRA = "movie";
    private static final int DEFAULT_POSITION = -1;
    private static final String RELEASE_DATE_SEPARATOR = "-";
    public final static String REVIEW_QUERY = "reviews";
    public final static String VIDEO_QUERY = "videos";
    public final static String YOUTUBE_BASE_APP = "vnd.youtube:";
    public final static String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";

    private static String movieId;
    private static List<Trailer> movieTrailers;
    private ReviewsAdapter movieReviewsAdapter;
    private MovieDetailActivityViewModel movieDetailActivityViewModel;

    @BindView(R.id.review_rv)
    RecyclerView reviewRecyclerView;
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
    @Nullable
    @BindView(R.id.reviewer_tv)
    TextView reviewAuthor;
    @Nullable
    @BindView(R.id.review_content_tv)
    TextView reviewContent;
    @BindView(R.id.no_reviews_available_tv)
    TextView reviewEmptyStateTextView;


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
        Movie movie = intent.getParcelableExtra(MOVIE_EXTRA);
        if (null == movie) {
            closeOnError();
        }

        movieId = movie.getId();
        boolean isFavorite = Boolean.parseBoolean(movie.getIsFavorite());
//        AsyncTask.execute(() -> {
//            if (isFavorite)
////                movie = MovieDatabase.getDatabase(this).movieDao().getFavoriteMovieById(movieId);
//
//        });



//        RecyclerView.LayoutManager reviewLayoutManager = new LinearLayoutManager(this);
//        reviewRecyclerView.setLayoutManager(reviewLayoutManager);
//        reviewRecyclerView.setAdapter(movieReviewsAdapter);
//        RecyclerView.LayoutManager trailerLayoutManager = new LinearLayoutManager(this);
//        activityMovieDetailBinding.trailersRv.setLayoutManager(trailerLayoutManager);
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
                movieReviewsAdapter = new ReviewsAdapter(MovieDetailActivity.this, movieReviews);
                RecyclerView.LayoutManager reviewLayoutManager = new LinearLayoutManager(MovieDetailActivity.this);
                reviewRecyclerView.setLayoutManager(reviewLayoutManager);
                reviewRecyclerView.setAdapter(movieReviewsAdapter);
            }
        }
    }
}
