package com.FoodieSimulator.side.project.dao;

import com.FoodieSimulator.side.project.exception.DaoException;
import com.FoodieSimulator.side.project.model.Restaurant;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;



public class JdbcRestaurantDao implements RestaurantDAO {

    private final JdbcTemplate jdbcTemplate;

    public JdbcRestaurantDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

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
    public List<Restaurant> getFlaggedRestaurant() {
        return null;
    }

    @Override
    public List<Restaurant> filterRestaurant(String searchTerm, boolean publicOnly, boolean useWildCard) {
        return null;
    }

    @Override
    public Restaurant createRestaurant(Restaurant newRestaurant) {
        Restaurant restaurant = null;

        try {
            if (newRestaurant.getName() == null) {
                int newRestaurantId;
                String insertRestaurantSql = "INSERT INTO restaurant (name, address1, address2, city, state, zipCode) values (?,?,?,?,?,?) RETURNING restaurantId";
                newRestaurantId = jdbcTemplate.queryForObject(insertRestaurantSql, int.class, restaurant.getName(), restaurant.getAddress1(), restaurant.getAddress2(), restaurant.getCity(),
                        restaurant.getState(), restaurant.getZipCode());
            }
            newRestaurant = getRestaurantById(newRestaurant.getRestaurantId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newRestaurant;
    }


    @Override
    public Restaurant updateRestaurant(Restaurant modifiedRestaurant) {
        return null;
    }

    @Override
    public int deleteRestaurantById(int restaurantId) {
        return 0;
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