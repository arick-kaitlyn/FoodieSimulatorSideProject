package com.FoodieSimulator.side.project.model;

public class RestaurantRating {
    private int ratingId;
    private int restaurantId;

    public RestaurantRating(int ratingId, int restaurantId) {
        this.ratingId = ratingId;
        this.restaurantId = restaurantId;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }


    @Override
    public String toString() {
        return "RestaurantRating{" +
                "ratingId=" + ratingId +
                "restaurantId=" + restaurantId + '\'' +
                '}';
    }
}
