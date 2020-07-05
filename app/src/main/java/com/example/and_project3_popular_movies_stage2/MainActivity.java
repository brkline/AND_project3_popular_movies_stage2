package com.example.and_project3_popular_movies_stage2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.preference.PreferenceManager;

import com.example.and_project3_popular_movies_stage2.adapters.MovieAdapter;
import com.example.and_project3_popular_movies_stage2.models.Movie;
import com.example.and_project3_popular_movies_stage2.utils.CheckIfOnline;
import com.example.and_project3_popular_movies_stage2.utils.MovieLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    // Constant values
    private static final int MOVIE_LOADER_ID = 1;
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String MOST_POPULAR_SELECTED = "MOST_POPULAR_SELECTED";
    private static final String TOP_RATED_SELECTED = "TOP_RATED_SELECTED";
    private static final String FAVORITES = "favorites";

    private List<Movie> movieList2;
    private boolean mostPopularSelected;
    private boolean topRatedSelected;
    private MainActivityViewModel mainActivityViewModel;
    MovieAdapter movieAdapter;

    @BindView(R.id.empty_view)
    public TextView emptyStateTextView;

    @BindView(R.id.main_fragment_gv)
    public GridView gridView;

    String queryType;
    private boolean favoriteMoviesSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        queryType = POPULAR;
        setTitle(R.string.primary_menu_most_popular_title);

        String storedSortOrder = setupSharedPreferences();
        if (!storedSortOrder.isEmpty()) {
            queryType = storedSortOrder;
        }
        if (queryType.equalsIgnoreCase(POPULAR)) {
            setTitle(R.string.primary_menu_most_popular_title);
            loadMovies(false);
            mostPopularSelected = true;
            topRatedSelected = false;
            favoriteMoviesSelected = false;
        } else if (queryType.equalsIgnoreCase(TOP_RATED)) {
            setTitle(R.string.primary_menu_top_rated_title);
            loadMovies(false);
            mostPopularSelected = false;
            topRatedSelected = true;
            favoriteMoviesSelected = false;
        } else if (queryType.equalsIgnoreCase(FAVORITES)) {
            setTitle(R.string.primary_menu_favorites_title);
            loadFavoriteMovies();
            mostPopularSelected = false;
            topRatedSelected = false;
            favoriteMoviesSelected = true;
        }

        gridView.setOnItemClickListener((parent, view, position, id) -> launchMovieDetailActivity(position));
    }

    private void loadFavoriteMovies() {
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getFavoriteMovies().observe(this, favoriteMovies -> {
            if (null != favoriteMovies && favoriteMoviesSelected) {
                MovieAdapter movieAdapter = new MovieAdapter(this, favoriteMovies);
                gridView.setAdapter(movieAdapter);
                movieList2 = favoriteMovies;
                movieAdapter.notifyDataSetChanged();
            } else {
                // Set empty state text to display "No Internet Connection."
                emptyStateTextView.setText(R.string.no_internet_connection);
            }
        });
    }

    private void loadMovies(boolean resetLoader) {
        // CheckIfOnline class based on https://stackoverflow.com/a/27312494
        new CheckIfOnline(new CheckIfOnline.Consumer() {
            // Anonymous class created using https://stackoverflow.com/a/40826752
            @Override
            public void accept(Boolean internet) {
                /* do something with boolean response */
                if (internet) {
                    // Get a reference to the LoaderManager, in order to interact with loaders.
                    LoaderManager loaderManager = LoaderManager.getInstance(MainActivity.this);
                    if (!resetLoader) {
                        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                        // because this activity implements the LoaderCallbacks interface).
                        loaderManager.initLoader(MOVIE_LOADER_ID, null, MainActivity.this);
                    } else {
                        // This means we are wanting to reload the movies. We need to restart the Loader
                        // so it will fetch them and populate our UI.
                        loaderManager.restartLoader(MOVIE_LOADER_ID, null, MainActivity.this);
                    }
                    emptyStateTextView.setVisibility(View.GONE);
                } else {
                    // Set empty state text to display "No Internet Connection."
                    emptyStateTextView.setText(R.string.no_internet_connection);
                    emptyStateTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int i, @Nullable Bundle bundle) {
        String url = Movie.THEMOVIEDB_REQUEST_URL;
        return new MovieLoader(this, url, queryType);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> movieList) {

        // If there is a valid list of {@link Movie}s, then add them to the adapter's
        // data set.
        if (movieList != null && !movieList.isEmpty()) {

            movieAdapter = new MovieAdapter(this, movieList);
            gridView.setAdapter(movieAdapter);
            movieList2 = movieList;
            emptyStateTextView.setVisibility(View.GONE);

        } else {

            // Set empty state text to display "No Movies found."
            emptyStateTextView.setText(R.string.no_movies);
            emptyStateTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {
        loader.reset();
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
//        getMenuInflater().inflate(R.menu.menu_settings, menu);
        getMenuInflater().inflate(R.menu.primary_menu, menu);
        return super.onCreateOptionsMenu(menu);
//        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            Intent settingsIntent = new Intent(this, MovieSettingsActivity.class);
//            startActivity(settingsIntent);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
        int idOfMenuItemSelected = item.getItemId();
        switch (idOfMenuItemSelected) {
            case R.id.menu_popular:
                mostPopularSelected = true;
                topRatedSelected = false;
                setTitle(R.string.primary_menu_most_popular_title);
                queryType = POPULAR;
//                loadMovies(queryType);
                loadMovies(true);
                break;
            case R.id.menu_top_rated:
                mostPopularSelected = false;
                topRatedSelected = true;
                setTitle(R.string.primary_menu_top_rated_title);
                queryType = TOP_RATED;
//                loadMovies(queryType);
                loadMovies(true);
                break;
            case R.id.menu_favorites:
                mostPopularSelected = false;
                topRatedSelected = false;
                favoriteMoviesSelected = true;
                queryType = FAVORITES;
                setTitle(R.string.primary_menu_favorites_title);
                loadFavoriteMovies();
                break;
        }
        saveSortOrder(queryType);
        return super.onOptionsItemSelected(item);
    }

    private void launchMovieDetailActivity(int position) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        // Add the extra data needed in the Movie Detail activity
        intent.putExtra(MovieDetailActivity.EXTRA_POSITION, position);
        Movie movie = movieList2.get(position);
        intent.putExtra(MovieDetailActivity.MOVIE_EXTRA, movie);
        startActivity(intent);
    }

    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Unregister VisualizerActivity as an OnPreferenceChangedListener to avoid any memory leaks.
//        PreferenceManager.getDefaultSharedPreferences(this)
//                .unregisterOnSharedPreferenceChangeListener(this);
//    }
//
    private String setupSharedPreferences() {
        // Get all of the values from shared preferences to set it up
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //        setTitle(sortOrderSelected);
        return sharedPreferences.getString(getString(R.string
                .movie_settings_sort_order_key), POPULAR);
    }

    private void saveSortOrder(String sortOrderSelected) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putString(getString(R.string.movie_settings_sort_order_key), sortOrderSelected);
        sharedPrefEditor.apply();
    }
//
//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String sortOrderSelected = sharedPreferences.getString(getString(R.string
//                .movie_settings_sort_order_key), getString(R.string.movie_settings_sort_default));
//        setTitle(sortOrderSelected);
//    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        out.putBoolean(MOST_POPULAR_SELECTED, mostPopularSelected);
        out.putBoolean(TOP_RATED_SELECTED, topRatedSelected);
        super.onSaveInstanceState(out);
    }
}