package com.FoodieSimulator.side.project.model;

public class Rating {
    private int ratingId;
    private boolean isThumbsUp;
    private boolean isThumbsDown;

    public Rating(boolean isThumbsUp, boolean isThumbsDown) {
        this.isThumbsUp = isThumbsUp;
        this.isThumbsDown = isThumbsDown;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public boolean isThumbsUp() {
        return isThumbsUp;
    }

    public void setThumbsUp(boolean thumbsUp) {
        isThumbsUp = thumbsUp;
    }

    public boolean isThumbsDown() {
        return isThumbsDown;
    }

    public void setThumbsDown(boolean thumbsDown) {
        isThumbsDown = thumbsDown;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingId=" + ratingId +
                ", isThumbsUp='" + isThumbsUp + '\'' +
                ", isThumbsDown='" + isThumbsDown +
                '}';
    }
}

