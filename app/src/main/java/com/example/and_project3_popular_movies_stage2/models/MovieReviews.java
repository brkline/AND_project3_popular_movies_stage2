package com.example.and_project3_popular_movies_stage2.models;

import java.util.List;

public class MovieReviews {
    private List<Review> reviews;

    public MovieReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
