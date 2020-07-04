package com.example.and_project3_popular_movies_stage2.models;

import java.net.URL;

public class Review {
    public static final String AUTHOR_KEY = "author";
    public static final String CONTENT_KEY = "content";
    public static final String REVIEW_URL = "url";
    private String reviewId;
    private String reviewAuthor;
    private String reviewContent;
    private URL reviewUrl;

    public Review(String reviewId, String reviewAuthor, String reviewContent, URL reviewUrl) {
        this.reviewId = reviewId;
        this.reviewAuthor = reviewAuthor;
        this.reviewContent = reviewContent;
        this.reviewUrl = reviewUrl;
    }

    public String getReviewAuthor() {return reviewAuthor;}

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String  getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public URL getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(URL reviewUrl) {
        this.reviewUrl = reviewUrl;
    }
}
