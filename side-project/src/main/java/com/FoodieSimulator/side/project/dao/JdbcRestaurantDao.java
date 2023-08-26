package com.FoodieSimulator.side.project.dao;

import com.FoodieSimulator.side.project.exception.DaoException;
import com.FoodieSimulator.side.project.model.Restaurant;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;



public class JdbcRestaurantDao implements RestaurantDAO {

    private final JdbcRestaurantDao

    public JdbcRestaurantDao(DataSource dataSource) {jdbcTemplate = new JdbcTemplate(dataSource)};

    String generalSql = "SELECT restaurantId, name, address1, address2, city, state, zipCode FROM restaurant";

    @Override
    public Restaurant getRestaurantById(int restaurantId) {
        Restaurant restaurant = null;
        String sql = "SELECT restaurantId, name, address1, address2, city, state, zipCode FROM restaurant" +
                "WHERE restaurantId = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, restaurantId);
            if (results.next()) {
                restaurant = mapRowToRestaurant(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return restaurant;
    }


    @Override
    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();

        try {
            String sql = generalSql + "ORDER BY name;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while (results.next()) {
                Restaurant restaurant = mapRowToRestaurant(results);
                restaurants.add(restaurant);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return restaurants;
    }

    @Override
    public List<Restaurant> getRestaurantByMemberId(int memberId) {
        return null;
    }

    @Override
    public List<Restaurant> getPublicRestaurantByMemberId(int memberId) {
        return null;
    }

    @Override
    public List<Restaurant> getPublicRestaurants() {
        return null;
    }

    @Override
    public List<Restaurant> getFlaggedRestaurant() {
        return null;
    }

    @Override
    public List<Restaurant> filterRestaurant(String searchTerm, boolean publicOnly, boolean useWildCard) {
        return null;
    }

    @Override
    public Restaurant createRestaurant(Restaurant newRestaurant) {
        return null;
    }

    @Override
    public int deleteRestaurantById(int restaurantId) {
        return 0;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant modifiedRestaurant) {
        return null;
    }

    private Restaurant mapRowToRestaurant(SqlRowSet rs) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(rs.getInt("restaurantId"));
        restaurant.setName(rs.getString("name"));
        restaurant.setAddress1(rs.getString("address1"));
        restaurant.setAddress2(rs.getString("address2"));
        restaurant.setCity(rs.getString("city"));
        restaurant.setState(rs.getString("state"));
        restaurant.setZipCode(rs.getString("zipCode"));
        restaurant.setCuisineId(rs.getInt("cuisineId"));
        return restaurant;
    }
}