package com.pep.restaurant.ms.bff.web.rest;

import com.pep.restaurant.ms.bff.service.LocationService;
import com.pep.restaurant.ms.bff.service.mapper.LocationMapper;
import com.pep.restaurant.ms.bff.web.api.LocationApi;
import com.pep.restaurant.ms.bff.web.api.model.LocationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
public class LocationController implements LocationApi, ApiController {

    public static final String LOCATION_LOCATION_ID = "/location/{locationId}";
    public static final String LOCATION = "/location";
    private final LocationService locationService;
    private final LocationMapper locationMapper;

    /**
     * Constructor for Location Controller.
     *
     * @param locationService Location Service.
     * @param locationMapper  Location Mapper.
     */
    public LocationController(final LocationService locationService, final LocationMapper locationMapper) {
        this.locationService = locationService;
        this.locationMapper = locationMapper;
    }

    /**
     * Controller to get a location by id.
     *
     * @param locationId id of location to get.
     * @return LocationDTO with the provided id.
     */

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = LOCATION_LOCATION_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<LocationDTO> getLocation(final Long locationId) {
        return ResponseEntity.ok(locationMapper.mapLocationToLocationDTO(
                locationService.getLocation(locationId)));
    }

    /**
     * Controller to create a location.
     *
     * @param locationDTO locationDTO to create.
     * @return LocationDTO created.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(value = LOCATION,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<LocationDTO> createLocation(@RequestBody final LocationDTO locationDTO) {
        return ResponseEntity.ok(locationMapper.mapLocationToLocationDTO(
                locationService.createLocation(locationMapper.mapLocationDTOToLocation(locationDTO))));
    }

    /**
     * Controller to edit a location.
     *
     * @param locationId location to be edited.
     * @param locationDTO locationDTO new data.
     * @return LocationDTO created.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(value = LOCATION_LOCATION_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<LocationDTO> editLocation(final Long locationId,
                                                        @RequestBody final LocationDTO locationDTO) {
        return ResponseEntity.ok(locationMapper.mapLocationToLocationDTO(
                locationService.editLocation(locationId,
                        locationMapper.mapLocationDTOToLocation(locationDTO))));

    }

    /**
     * Controller to delete location by id.
     * @param locationId location Id.
     * @return Location deleted.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping(value = LOCATION_LOCATION_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<LocationDTO> deleteLocation(final Long locationId) {
        return ResponseEntity.ok(locationMapper.mapLocationToLocationDTO(
                locationService.deleteLocation(locationId)));
    }

    /**
     * Controller to Get all locations.
     * @return Locations List.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = LOCATION,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        return ResponseEntity.ok(locationMapper.mapLocationListToLocationDTOList(
                locationService.getAllLocations()));
    }
}
