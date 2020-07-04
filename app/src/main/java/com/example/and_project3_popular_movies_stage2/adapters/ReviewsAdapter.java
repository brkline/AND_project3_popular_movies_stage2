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

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MovieReviewViewHolder> {

    private List<Review> movieReviews;
    private Context context;

    public ReviewsAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.movieReviews = reviewList;
    }

    @NonNull
    @Override
    public MovieReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MovieReviewViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_review_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewViewHolder holder, int position) {

        holder.bindReview(movieReviews.get(position));
    }

    class MovieReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.reviewer_tv)
        TextView reviewAuthor;
        @BindView(R.id.review_content_tv)
        TextView reviewContent;

        MovieReviewViewHolder(View itemView) {
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
