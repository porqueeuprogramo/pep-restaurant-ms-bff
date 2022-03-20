package com.pep.restaurant.ms.bff.service;

import com.pep.restaurant.ms.bff.client.HereClient;
import com.pep.restaurant.ms.bff.domain.HereRestaurantInfo;
import com.pep.restaurant.ms.bff.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HereService {

    private final HereClient hereClient;

    @Autowired
    public HereService(final HereClient hereClient) {
        this.hereClient = hereClient;
    }

    /**
     * Persist Restaurants Near The Coordinate.
     * @param lat latitude.
     * @param lng longitude.
     * @return Restaurant List.
     */
    public List<Restaurant> persistRestaurantsNearTheCoordinate(final String lat, final String lng){
        //Get Restaurant List By Coordinate from Here.
        final List<HereRestaurantInfo> hereRestaurantInfoList = hereClient.getRestaurantsNearTheCoordinate(lat, lng);
        //TODO
        //Check if Restaurant Info List is already present on db.
        //Map Here Restaurant Info List to Restaurant.
        //Persist Restaurants.
        //return Persisted Restaurant List.
        return List.of(new Restaurant());
    }

}
