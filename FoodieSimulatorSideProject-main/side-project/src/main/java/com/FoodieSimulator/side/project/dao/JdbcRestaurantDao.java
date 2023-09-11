package com.FoodieSimulator.side.project.dao;

import com.FoodieSimulator.side.project.exception.DaoException;
import com.FoodieSimulator.side.project.model.Restaurant;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
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
        String sql = generalSql + "WHERE restaurantId = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, restaurantId);
            if (results.next()) {
                restaurant = mapRowToRestaurant(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
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
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return restaurants;
    }

    //check if this should be a list or return a restaurant + check memberId to member_id?
    @Override
    public List<Restaurant> getRestaurantByMemberId(int memberId) {
        List<Restaurant> restaurants = new ArrayList<>();

        try {
            String sql = "SELECT memberId FROM member WHERE memberId = ?";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while (results.next()) {
                Restaurant restaurant = mapRowToRestaurant(results);
                restaurants.add(restaurant);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return restaurants;
    }

    @Override
    public List<Restaurant> getFlaggedRestaurant() {
        return null;
    }

    @Override
    public List<Restaurant> filterRestaurant(String name, boolean useWildCard) {
        List<Restaurant> restaurant = new ArrayList<>();
        String sql = "SELECT restaurant_id, name, address1, address2, city, state, zipCode" +
                " FROM restaurant " + "WHERE name ILIKE ?;";

        try {
            if (useWildCard) {
                name = "%" + name + "%";
            }
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);

            while (results.next()) {
                restaurant.add(mapRowToRestaurant(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return restaurant;
    }

    @Override
    public Restaurant createRestaurant(Restaurant newRestaurant) {
        try {
            if (newRestaurant.getName() == null) {
                String insertRestaurantSql = "INSERT INTO restaurant (name, address1, address2, city, state, zipCode) values (?,?,?,?,?,?) RETURNING restaurantId";
                int newRestaurantId = jdbcTemplate.queryForObject(insertRestaurantSql, int.class, newRestaurant.getName(), newRestaurant.getAddress1(), newRestaurant.getAddress2(), newRestaurant.getCity(),
                        newRestaurant.getState(), newRestaurant.getZipCode());
            }
            //FIX THIS PART!!!
            //newRestaurant = getRestaurantById(newRestaurant.getRestaurantId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newRestaurant;
    }


    @Override
    public Restaurant updateRestaurant(Restaurant updatedRestaurant) {
        String sql = "UPDATE restaurant SET name = ?, address1 = ?, address2 = ?, city = ?, state = ?, zipCode = ?" +
                " values (?,?,?,?,?,?) WHERE restaurantId = ?'";

        try {
            jdbcTemplate.update(sql, updatedRestaurant.getName(), updatedRestaurant.getAddress1(), updatedRestaurant.getAddress2(),
                    updatedRestaurant.getCity(), updatedRestaurant.getState(), updatedRestaurant.getZipCode());

            updatedRestaurant = getRestaurantById(updatedRestaurant.getRestaurantId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedRestaurant;
    }

    //is there any foreign keys i need to delete here?
    @Override
    public int deleteRestaurantById(int restaurantId) {
        int numberOfRows;
        String sql = "DELETE FROM restaurant WHERE restaurantId = ?;";

        try {
            numberOfRows = jdbcTemplate.update(sql, restaurantId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
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