package com.FoodieSimulator.side.project.dao;

import com.FoodieSimulator.side.project.model.Rating;

import java.util.List;

public interface RatingDAO {
    Rating getRatingById(int ratingId);
    List<Rating> getAllRatings();
    Rating createRatingThumbsUp(Rating RatingThumbsUp);
    Rating createRatingThumbsDown(Rating RatingThumbsDown);
    Rating updateRatingThumbsUp(Rating updateRatingThumbsUp);
    Rating updateRatingThumbsDown(Rating updateRatingThumbsDown);
    int deleteRating(int ratingId);
    //do i need to delete separate ratings for thumbs up and thumbs down?
    List<Rating> getRatingsByThumbsUp();
    List<Rating> getRatingsByThumbsDown();
}
