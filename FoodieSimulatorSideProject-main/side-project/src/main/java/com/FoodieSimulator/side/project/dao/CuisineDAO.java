package com.FoodieSimulator.side.project.dao;

import com.FoodieSimulator.side.project.model.Cuisine;
import org.springframework.data.repository.core.CrudMethods;

import java.util.List;

public interface CuisineDAO {
    Cuisine getCuisineById(int cuisineId);
    List<Cuisine> getCuisines();
    Cuisine createCuisine(Cuisine newCuisine);
    Cuisine updateCuisine(Cuisine updatedCuisine);
    int deleteCuisineById(int cuisineId);
}
