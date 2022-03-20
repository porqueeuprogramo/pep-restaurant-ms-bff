package com.pep.restaurant.ms.bff.service;

import com.pep.restaurant.ms.bff.client.RestaurantClient;
import com.pep.restaurant.ms.bff.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantClient restaurantClient;

    @Autowired
    public RestaurantService(final RestaurantClient restaurantClient) {
        this.restaurantClient = restaurantClient;
    }

    /**
     * Call get restaurant client.
     * @param restaurantId restaurant id.
     * @return restaurant.
     */
    public Restaurant getRestaurant(final long restaurantId) {
        return restaurantClient.getRestaurant(restaurantId);
    }

    /**
     * Call create restaurant client.
     * @param restaurant restaurant to create.
     * @return restaurant.
     */
    public Restaurant createRestaurant(final Restaurant restaurant) {
        return restaurantClient.createRestaurant(restaurant);
    }

    /**
     * Call edit restaurant client.
     * @param restaurantId restaurant id to edit.
     * @param restaurant restaurant to be edited
     * @return restaurant edited.
     */
    public Restaurant editRestaurant(final Long restaurantId, final Restaurant restaurant) {
        return restaurantClient.editRestaurant(restaurantId, restaurant);
    }

    /**
     * Call add employee to restaurant.
     * @param restaurantId restaurant id.
     * @param employeeId employee id.
     * @return restaurant with employee added.
     */
    public Restaurant addEmployee(final Long restaurantId, final Long employeeId) {
        return restaurantClient.addEmployee(restaurantId, employeeId);
    }

    /**
     * Call remove employee from restaurant.
     * @param restaurantId restaurant id.
     * @param employeeId employee id.
     * @return restaurant with employee removed.
     */
    public Restaurant removeEmployee(final Long restaurantId, final Long employeeId) {
        return restaurantClient.removeEmployee(restaurantId, employeeId);
    }

    /**
     * Call remove restaurant.
     * @param restaurantId restaurant id.
     * @return Restaurant Deleted.
     */
    public Restaurant deleteRestaurant(final Long restaurantId) {
        return restaurantClient.deleteRestaurant(restaurantId);
    }

    /**
     * Call Get all restaurants.
     * @return Restaurant List.
     */
    public List<Restaurant> getAllRestaurants() {
        return restaurantClient.getAllRestaurants();
    }

}
