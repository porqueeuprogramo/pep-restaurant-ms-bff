package com.pep.restaurant.ms.bff.service;

import com.pep.restaurant.ms.bff.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.client.RestaurantClient;
import com.pep.restaurant.ms.bff.domain.Restaurant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantClient restaurantClient;

    private ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void passingRestaurantId_checkIfGetRestaurantRestaurantClientIsCalled() {

        //Given
        Restaurant restaurantGiven = applicationDataProvider.getRestaurant();

        //When
        when(restaurantClient.getRestaurant(anyLong())).thenReturn(restaurantGiven);
        Restaurant restaurant = restaurantService.getRestaurant(anyLong());

        //Then
        verify(restaurantClient, Mockito.times(1)).getRestaurant(anyLong());
    }

    @Test
    public void passingARestaurant_checkIfCreateRestaurantRestaurantClientIsCalled() {
        //Given
        Restaurant restaurantGiven = applicationDataProvider.getRestaurant();

        //When
        when(restaurantClient.createRestaurant(restaurantGiven)).thenReturn(restaurantGiven);
        Restaurant restaurant = restaurantService.createRestaurant(restaurantGiven);

        //Then
        verify(restaurantClient, Mockito.times(1)).createRestaurant(restaurantGiven);
    }

    @Test
    public void passingARestaurantIdAndARestaurant_checkIfEditRestaurantRestaurantClientIsCalled() {
        //Given
        Restaurant restaurantGiven = applicationDataProvider.getRestaurant();

        //When
        when(restaurantClient.editRestaurant(1L, restaurantGiven)).thenReturn(restaurantGiven);
        Restaurant restaurant = restaurantService.editRestaurant(1L, restaurantGiven);

        //Then
        verify(restaurantClient, Mockito.times(1))
                .editRestaurant(1L, restaurantGiven);
    }

    @Test
    public void passingARestaurantIdAndAnEmployeeId_checkIfAddEmployeeToRestaurantRestaurantClientIsCalled() {
        //Given
        Restaurant restaurantGiven = applicationDataProvider.getRestaurant();

        //When
        when(restaurantClient.addEmployee(1L, 1L)).thenReturn(restaurantGiven);
        Restaurant restaurant = restaurantService.addEmployee(1L, 1L);

        //Then
        verify(restaurantClient, Mockito.times(1))
                .addEmployee(1L, 1L);
    }

    @Test
    public void passingARestaurantIdAndAnEmployeeId_checkIfRemoveEmployeeFromRestaurantRestaurantClientIsCalled() {
        //Given
        Restaurant restaurantGiven = applicationDataProvider.getRestaurant();

        //When
        when(restaurantClient.removeEmployee(1L, 1L)).thenReturn(restaurantGiven);
        Restaurant restaurant = restaurantService.removeEmployee(1L, 1L);

        //Then
        verify(restaurantClient, Mockito.times(1))
                .removeEmployee(1L, 1L);
    }

    @Test
    public void passingRestaurantId_checkIfDeleteRestaurantRestaurantClientIsCalled() {
        //Given
        Restaurant restaurantGiven = applicationDataProvider.getRestaurant();

        //When
        when(restaurantClient.deleteRestaurant(1L)).thenReturn(restaurantGiven);
        Restaurant restaurant = restaurantService.deleteRestaurant(1L);

        //Then
        verify(restaurantClient, Mockito.times(1))
                .deleteRestaurant(1L);
    }

    @Test
    public void checkIfGetAllRestaurantsRestaurantClientIsCalled() {

        //Given
        Restaurant restaurantGiven = applicationDataProvider.getRestaurant();

        //When
        when(restaurantClient.getAllRestaurants()).thenReturn(Collections.singletonList(restaurantGiven));
        List<Restaurant> restaurant = restaurantService.getAllRestaurants();

        //Then
        verify(restaurantClient, Mockito.times(1))
                .getAllRestaurants();
    }
}
