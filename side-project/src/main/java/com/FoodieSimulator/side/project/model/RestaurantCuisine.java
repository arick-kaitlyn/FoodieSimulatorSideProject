package com.FoodieSimulator.side.project.model;

public class RestaurantCuisine {
    private int restaurantId;
    private int cuisineId;

    public RestaurantCuisine(int restaurantId, int cuisineId) {
        this.restaurantId = restaurantId;
        this.cuisineId = cuisineId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(int cuisineId) {
        this.cuisineId = cuisineId;
    }

    @Override
    public String toString() {
        return "RestaurantCuisine{" +
                "restaurantId=" + restaurantId +
                "cuisineId=" + cuisineId + '\'' +
                '}';
    }
}

