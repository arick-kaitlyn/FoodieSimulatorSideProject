package com.FoodieSimulator.side.project.dao;

import com.FoodieSimulator.side.project.exception.DaoException;
import com.FoodieSimulator.side.project.model.Cuisine;
import com.FoodieSimulator.side.project.model.Rating;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcRatingDao implements RatingDAO {
    private JdbcTemplate jdbcTemplate;
    private JdbcRatingDao (DataSource dataSource) { jdbcTemplate = new JdbcTemplate(dataSource);}
    String generalSQL = "SELECT rating_id, is_thumbs_up, is_thumbs_down FROM rating ";

    @Override
    public Rating getRatingById(int ratingId) {
        Rating rating = null;
        String sql = generalSQL + "WHERE rating_id = ? ";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, ratingId);
            if (results.next()) {
                rating = mapRowToRating(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return rating;
    }

    @Override
    public List<Rating> getAllRatings() {
        List<Rating> ratings = new ArrayList<>();
        try {
            String sql = generalSQL + "ORDER by name;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while (results.next()) {
                Rating rating = mapRowToRating(results);
                ratings.add(rating);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return ratings;
    }

    @Override
    public Rating createRatingThumbsUp(Rating RatingThumbsUp) {
        try {
            String insertRatingSql = "INSERT INTO rating (is_thumbs_up, is_thumbs_down) values (?, ?) RETURNING rating_id ";
            int newRatingId = jdbcTemplate.queryForObject(insertRatingSql, int.class, RatingThumbsUp.isThumbsUp(), RatingThumbsUp.isThumbsDown());
            RatingThumbsUp = getRatingById(RatingThumbsUp.getRatingId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return RatingThumbsUp;
    }

    @Override
    public Rating createRatingThumbsDown(Rating RatingThumbsDown) {
        try {
            String insertRatingSql = "INSERT INTO rating (is_thumbs_up, is_thumbs_down) values (?, ?) RETURNING rating_id ";
            int newRatingId = jdbcTemplate.queryForObject(insertRatingSql, int.class, RatingThumbsDown.isThumbsUp(), RatingThumbsDown.isThumbsDown());
            RatingThumbsDown = getRatingById(RatingThumbsDown.getRatingId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return RatingThumbsDown;
    }

    @Override
    public Rating updateRatingThumbsUp(Rating updateRatingThumbsUp) {
        String sql = "UPDATE rating SET is_thumbs_up = ?" +
                "WHERE rating_id = ? ";
        try {
            jdbcTemplate.update(sql, updateRatingThumbsUp.isThumbsUp());
            updateRatingThumbsUp = getRatingById(updateRatingThumbsUp.getRatingId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updateRatingThumbsUp;
    }

    @Override
    public Rating updateRatingThumbsDown(Rating updateRatingThumbsDown) {
        String sql = "UPDATE rating SET is_thumbs_down = ?" +
                "WHERE rating_id = ? ";
        try {
            jdbcTemplate.update(sql, updateRatingThumbsDown.isThumbsDown());
            updateRatingThumbsDown = getRatingById(updateRatingThumbsDown.getRatingId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updateRatingThumbsDown;
    }

    @Override
    public int deleteRating(int ratingId) {
        int numberOfRows;
        String sql = "DELETE FROM rating WHERE rating_id = ?;";

        try {
            numberOfRows = jdbcTemplate.update(sql, ratingId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    @Override
    public List<Rating> getRatingsByThumbsUp() {
        List<Rating> ratings = new ArrayList<>();
        try {
            String sql = "SELECT rating.rating_id, rating.is_thumbs_up FROM rating " +
            "JOIN restaurant_rating ON rating.rating_id = restaurant_rating.rating_id " +
            "JOIN restaurant ON restaurant_rating.restaurant_id = restaurant.restaurant_id " +
            "WHERE rating.is_thumbs_up = true;";

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while (results.next()) {
                Rating rating = mapRowToRating(results);
                ratings.add(rating);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return ratings;
    }

    @Override
    public List<Rating> getRatingsByThumbsDown() {
        List<Rating> ratings = new ArrayList<>();
        try {
            String sql = "SELECT rating.rating_id, rating.is_thumbs_down FROM rating " +
            "JOIN restaurant_rating ON rating.rating_id = restaurant_rating.rating_id " +
            "JOIN restaurant ON restaurant_rating.restaurant_id = restaurant.restaurant_id " +
            "WHERE rating.is_thumbs_down = true;";

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while (results.next()) {
                Rating rating = mapRowToRating(results);
                ratings.add(rating);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return ratings;
    }


    private Rating mapRowToRating(SqlRowSet rs) {
        Rating rating = new Rating();
        rating.setRatingId(rs.getInt("rating_id"));
        rating.setThumbsUp(rs.getBoolean("is_thumbs_up"));
        rating.setThumbsDown(rs.getBoolean("is_thumbs_down"));
        return rating;
    }
}
