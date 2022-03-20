package com.pep.restaurant.ms.bff.service;

import com.pep.restaurant.ms.bff.client.LocationClient;
import com.pep.restaurant.ms.bff.domain.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationClient locationClient;

    @Autowired
    public LocationService(final LocationClient locationClient) {
        this.locationClient = locationClient;
    }

    /**
     * Call get location client.
     * @param locationId location id.
     * @return location.
     */
    public Location getLocation(final long locationId) {
        return locationClient.getLocation(locationId);
    }

    /**
     * Call create location client.
     * @param location location to create.
     * @return location.
     */
    public Location createLocation(final Location location) {
        return locationClient.createLocation(location);
    }

    /**
     * Call edit location client.
     * @param locationId location id to edit.
     * @param location location to be edited
     * @return location edited.
     */
    public Location editLocation(final Long locationId, final Location location) {
        return locationClient.editLocation(locationId, location);
    }

    /**
     * Call remove location.
     * @param locationId location id.
     * @return Location Deleted.
     */
    public Location deleteLocation(final Long locationId) {
        return locationClient.deleteLocation(locationId);
    }

    /**
     * Call Get all locations.
     * @return Location List.
     */
    public List<Location> getAllLocations() {
        return locationClient.getAllLocations();
    }

}
