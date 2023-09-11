package com.FoodieSimulator.side.project.dao;

import com.FoodieSimulator.side.project.exception.DaoException;
import com.FoodieSimulator.side.project.model.Cuisine;
import com.FoodieSimulator.side.project.model.Restaurant;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcCuisineDao implements CuisineDAO {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCuisineDao(DataSource dataSource) { jdbcTemplate = new JdbcTemplate(dataSource);}
    String generalSql = "SELECT cuisine_id, name FROM cuisine ";
    @Override
    public Cuisine getCuisineById(int cuisineId) {
        Cuisine cuisine = null;
        String sql = generalSql + "WHERE cuisine_id = ? ";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cuisineId);
            if (results.next()) {
                cuisine = mapRowToCuisine(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return cuisine;
    }

    @Override
    public List<Cuisine> getCuisines() {
        List<Cuisine> cuisines = new ArrayList<>();
        try {
            String sql = generalSql + "ORDER BY name;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while (results.next()) {
                Cuisine cuisine = mapRowToCuisine(results);
                cuisines.add(cuisine);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return cuisines;
    }

    @Override
    public Cuisine createCuisine(Cuisine newCuisine) {
        try {
            if (newCuisine.getName() == null) {
                String insertCuisineSql = "INSERT INTO cuisine (name) values (?) RETURNING cuisine_id";
                int newCuisineId = jdbcTemplate.queryForObject(insertCuisineSql, int.class, newCuisine.getName());
            }
            newCuisine = getCuisineById(newCuisine.getCuisineId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newCuisine;
    }

    @Override
    public Cuisine updateCuisine(Cuisine updatedCuisine) {
        String sql = "UPDATE cuisine SET name = ?" +
                " values (?) WHERE cuisine_id = ?";

        try {
            jdbcTemplate.update(sql, updatedCuisine.getName());
            updatedCuisine = getCuisineById(updatedCuisine.getCuisineId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedCuisine;
    }

    @Override
    public int deleteCuisineById(int cuisineId) {
        int numberOfRows;
        String sql = "DELETE FROM cuisine WHERE cuisine_id = ?;";

        try {
            numberOfRows = jdbcTemplate.update(sql, cuisineId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Cuisine mapRowToCuisine(SqlRowSet rs) {
        Cuisine cuisine = new Cuisine();
        cuisine.setCuisineId(rs.getInt("cuisine_id"));
        cuisine.setName(rs.getString("name"));
        return cuisine;
    }
}
