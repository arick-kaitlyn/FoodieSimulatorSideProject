package com.FoodieSimulator.side.project.model;

public class Cuisine {
    private int cuisineId;
    private String name;

    public Cuisine(int cuisineId, String name) {
        this.cuisineId = cuisineId;
        this.name = name;
    }

    public Cuisine(String name) {
        this.name = name;
    }

    public int getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(int cuisineId) {
        this.cuisineId = cuisineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RestaurantRating{" +
                "cuisineId=" + cuisineId +
                "name='" + name + '\'' +
                '}';
    }
}

