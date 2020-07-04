package com.example.and_project3_popular_movies_stage2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and_project3_popular_movies_stage2.R;
import com.example.and_project3_popular_movies_stage2.models.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MovieTrailerViewHolder> {

    private List<Review> movieReviews;
    private Context context;

    public TrailerAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.movieReviews = reviewList;
    }

    @NonNull
    @Override
    public MovieTrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MovieTrailerViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_review_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailerViewHolder holder, int position) {

        final Review review = movieReviews.get(position);
        holder.bindReview(movieReviews.get(position));
    }

    class MovieTrailerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.reviewer_tv)
        TextView reviewAuthor;
        @BindView(R.id.review_content_tv)
        TextView reviewContent;

        MovieTrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindReview(Review review) {
            reviewAuthor.setText(review.getReviewAuthor());
            reviewContent.setText(review.getReviewContent());
        }
    }

    @Override
    public int getItemCount() {
        if (null == movieReviews) {
            return 0;
        } else {
            return movieReviews.size();
        }
    }
}
