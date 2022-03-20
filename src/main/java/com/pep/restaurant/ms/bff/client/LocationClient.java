package com.pep.restaurant.ms.bff.client;

import com.pep.restaurant.ms.bff.config.ApplicationProperties;
import com.pep.restaurant.ms.bff.domain.Location;
import com.pep.restaurant.ms.bff.logging.Logger;
import com.pep.restaurant.ms.bff.logging.enumeration.LogTag;
import com.pep.restaurant.ms.bff.service.mapper.LocationMapper;
import com.pep.restaurant.ms.bff.web.api.model.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class LocationClient {

    private static final Logger LOGGER = new Logger(LocationClient.class);
    public static final String LOCATION_LOCATION_ID = "/api/location/{locationId}";
    public static final String LOCATION = "/api/location";
    private final RestTemplate restTemplate;
    private final LocationMapper locationMapper;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public LocationClient(final RestTemplate restTemplate,
                          final LocationMapper locationMapper,
                          final ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.locationMapper = locationMapper;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Client to get Location by id.
     * @param locationId location id.
     * @return location.
     */
    public Location getLocation(final long locationId){
        final String url =  applicationProperties.getMsManager().getUrl().concat(LOCATION_LOCATION_ID);
        final String correlationId = UUID.randomUUID().toString();
        LocationDTO locationDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity requestGetLocationId = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("locationId", String.valueOf(locationId));

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.LOCATIONS, LogTag.RETRIEVED),
                "Get Location by id: " + locationId);

        try{
            final ResponseEntity<LocationDTO> responseLocation = restTemplate.exchange(url, HttpMethod.GET,
                    requestGetLocationId,
                    new ParameterizedTypeReference<LocationDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseLocation.getStatusCode())){
                locationDTO = responseLocation.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.LOCATIONS, LogTag.RETRIEVED),
                    "Location id not found");
        }

        return locationMapper.mapLocationDTOToLocation(locationDTO);
    }

    /**
     * Client to create location.
     * @param location location.
     * @return location.
     */
    public Location createLocation(final Location location){
        final String url =  applicationProperties.getMsManager().getUrl().concat(LOCATION);
        final String correlationId = UUID.randomUUID().toString();
        LocationDTO locationDTO = null;

        final LocationDTO requestLocationDTO = locationMapper.mapLocationToLocationDTO(location);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity<LocationDTO> requestCreateLocationClient = new HttpEntity(requestLocationDTO ,headers);

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.LOCATIONS, LogTag.PERSISTED),
                "Create Location: " + location.toString());

        try{
            final ResponseEntity<LocationDTO> responseLocationCreated = restTemplate.exchange(url, HttpMethod.POST,
                    requestCreateLocationClient, LocationDTO.class);
            if(HttpStatus.OK.equals(responseLocationCreated.getStatusCode())){
                locationDTO = responseLocationCreated.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.LOCATIONS, LogTag.PERSISTED)
                    , "Location was not created!!");
        }

        return locationMapper.mapLocationDTOToLocation(locationDTO);
    }

    /**
     * Client to edit location.
     * @param locationId location id.
     * @param location location.
     * @return location.
     */
    public Location editLocation(final long locationId, final Location location){
        final String url =  applicationProperties.getMsManager().getUrl().concat(LOCATION_LOCATION_ID);
        final String correlationId = UUID.randomUUID().toString();
        LocationDTO locationDTO = null;

        final LocationDTO requestLocationDTO = locationMapper.mapLocationToLocationDTO(location);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity<LocationDTO> requestEditLocationClient = new HttpEntity(requestLocationDTO ,headers);
        final Map<String, String> params = new HashMap<>();
        params.put("locationId", String.valueOf(locationId));

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.LOCATIONS, LogTag.EDITED),
                "Edit Location by id " + locationId);

        try{
            final ResponseEntity<LocationDTO> responseLocationEdited = restTemplate.exchange(url, HttpMethod.PUT,
                    requestEditLocationClient, LocationDTO.class, params);
            if(HttpStatus.OK.equals(responseLocationEdited.getStatusCode())){
                locationDTO = responseLocationEdited.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.LOCATIONS, LogTag.EDITED)
                    , "Location was not edited!!");
        }

        return locationMapper.mapLocationDTOToLocation(locationDTO);
    }

    /**
     * Client to delete location.
     * @param locationId location id.
     * @return location deleted.
     */
    public Location deleteLocation(final Long locationId) {
        final String url =  applicationProperties.getMsManager().getUrl().concat(LOCATION_LOCATION_ID);
        final String correlationId = UUID.randomUUID().toString();
        LocationDTO locationDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity requestGetLocationId = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("locationId", String.valueOf(locationId));

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.LOCATIONS, LogTag.DELETED),
                "Delete Location by id: " + locationId);

        try{
            final ResponseEntity<LocationDTO> responseLocation = restTemplate.exchange(url, HttpMethod.DELETE,
                    requestGetLocationId,
                    new ParameterizedTypeReference<LocationDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseLocation.getStatusCode())){
                locationDTO = responseLocation.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.LOCATIONS, LogTag.DELETED),
                    "Location id not found");
        }

        return locationMapper.mapLocationDTOToLocation(locationDTO);
    }

    /**
     * Client to get all locations.
     * @return locations list.
     */
    public List<Location> getAllLocations() {
        final String url =  applicationProperties.getMsManager().getUrl().concat(LOCATION);
        final String correlationId = UUID.randomUUID().toString();
        List<LocationDTO> locationDTOList = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity requestGetLocationId = new HttpEntity(headers);

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.LOCATIONS, LogTag.RETRIEVED),
                "Get All Locations list");

        try{
            final ResponseEntity<List<LocationDTO>> responseLocation = restTemplate.exchange(url, HttpMethod.GET,
                    requestGetLocationId,
                    new ParameterizedTypeReference<List<LocationDTO>>() {
                    });
            if(HttpStatus.OK.equals(responseLocation.getStatusCode())){
                locationDTOList = responseLocation.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.LOCATIONS, LogTag.RETRIEVED),
                    "Location list not found");
        }

        return locationMapper.mapLocationDTOListToLocationList(locationDTOList);
    }
}
