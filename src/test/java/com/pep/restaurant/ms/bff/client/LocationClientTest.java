package com.pep.restaurant.ms.bff.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.pep.restaurant.ms.bff.config.ApplicationProperties;
import com.pep.restaurant.ms.bff.domain.Location;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.service.mapper.LocationMapper;
import com.pep.restaurant.ms.bff.web.api.model.LocationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class LocationClientTest {

    @InjectMocks
    private LocationClient locationClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private LocationMapper locationMapper;

    private ApplicationProperties.MsManager msManagerProperty = new ApplicationProperties.MsManager();

    ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void requestingALocationIdToGet_checkLocationResult() {

        //Given
        Location location = applicationDataProvider.getLocation();
        LocationDTO locationDTO = applicationDataProvider.getLocationDTO();

        ResponseEntity responseEntity = new ResponseEntity<>(locationDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                anyMap())).thenReturn(responseEntity);
        when(locationMapper.mapLocationDTOToLocation(locationDTO)).thenReturn(location);
        Location locationResult = locationClient.getLocation(1L);

        //Then
        Assertions.assertEquals(location.getAddress().getName(),
                locationResult.getAddress().getName());
        Assertions.assertEquals(location.getAddress().getCity(),
                locationResult.getAddress().getCity());
        Assertions.assertEquals(location.getAddress().getCountry(),
                locationResult.getAddress().getCountry());
        Assertions.assertEquals(location.getAddress().getPostalCode(),
                locationResult.getAddress().getPostalCode());
        Assertions.assertEquals(location.getCoordinate().getLatitude(),
                locationResult.getCoordinate().getLatitude());
        Assertions.assertEquals(location.getCoordinate().getLongitude(),
                locationResult.getCoordinate().getLongitude());

    }

    @Test
    public void requestingALocationIdToGet_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(LocationClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                anyMap())).thenThrow(RestClientException.class);
        Location locationResult = locationClient.getLocation(1L);

        //Then
        Assertions.assertEquals("[CLIENT, LOCATIONS, RETRIEVED] Location id not found",
                logsList.get(1).getMessage());
    }

    @Test
    public void requestingALocationToCreate_checkLocationResult() {

        //Given
        Location location = applicationDataProvider.getLocation();
        LocationDTO locationDTO = applicationDataProvider.getLocationDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(locationDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<LocationDTO>) any())).thenReturn(responseEntity);
        when(locationMapper.mapLocationDTOToLocation(locationDTO)).thenReturn(location);
        Location locationResult = locationClient.createLocation(location);

        //Then
        Assertions.assertEquals(location.getAddress().getName(),
                locationResult.getAddress().getName());
        Assertions.assertEquals(location.getAddress().getCity(),
                locationResult.getAddress().getCity());
        Assertions.assertEquals(location.getAddress().getCountry(),
                locationResult.getAddress().getCountry());
        Assertions.assertEquals(location.getAddress().getPostalCode(),
                locationResult.getAddress().getPostalCode());
        Assertions.assertEquals(location.getCoordinate().getLatitude(),
                locationResult.getCoordinate().getLatitude());
        Assertions.assertEquals(location.getCoordinate().getLongitude(),
                locationResult.getCoordinate().getLongitude());
    }

    @Test
    public void requestingALocationToCreate_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(LocationClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Location location = applicationDataProvider.getLocation();

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<LocationDTO>) any())).thenThrow(RestClientException.class);
        Location locationResult = locationClient.createLocation(location);

        //Then
        Assertions.assertEquals("[CLIENT, LOCATIONS, PERSISTED] Location was not created!!",
                logsList.get(1).getMessage());

    }

    @Test
    public void requestingALocationIdToEditAndALocationEdited_checkLocationResult() {

        //Given
        Location location = applicationDataProvider.getLocation();
        LocationDTO locationDTO = applicationDataProvider.getLocationDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(locationDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<LocationDTO>) any(), anyMap())).thenReturn(responseEntity);
        when(locationMapper.mapLocationDTOToLocation(locationDTO)).thenReturn(location);
        Location locationResult = locationClient.editLocation(1L, location);

        //Then
        Assertions.assertEquals(location.getAddress().getName(),
                locationResult.getAddress().getName());
        Assertions.assertEquals(location.getAddress().getCity(),
                locationResult.getAddress().getCity());
        Assertions.assertEquals(location.getAddress().getCountry(),
                locationResult.getAddress().getCountry());
        Assertions.assertEquals(location.getAddress().getPostalCode(),
                locationResult.getAddress().getPostalCode());
        Assertions.assertEquals(location.getCoordinate().getLatitude(),
                locationResult.getCoordinate().getLatitude());
        Assertions.assertEquals(location.getCoordinate().getLongitude(),
                locationResult.getCoordinate().getLongitude());
    }

    @Test
    public void requestingALocationIdToEditAndALocationEdited_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(LocationClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Location location = applicationDataProvider.getLocation();
        LocationDTO locationDTO = applicationDataProvider.getLocationDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(locationDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<LocationDTO>) any(), anyMap())).thenThrow(RestClientException.class);
        Location locationResult = locationClient.editLocation(1L, location);

        //Then
        Assertions.assertEquals("[CLIENT, LOCATIONS, EDITED] Location was not edited!!",
                logsList.get(1).getMessage());
    }

    @Test
    public void requestingALocationIdToDelete_checkLocationResult() {

        //Given
        Location location = applicationDataProvider.getLocation();
        LocationDTO locationDTO = applicationDataProvider.getLocationDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(locationDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenReturn(responseEntity);
        when(locationMapper.mapLocationDTOToLocation(locationDTO)).thenReturn(location);
        Location locationResult = locationClient.deleteLocation(1L);

        //Then
        Assertions.assertEquals(location.getAddress().getName(),
                locationResult.getAddress().getName());
        Assertions.assertEquals(location.getAddress().getCity(),
                locationResult.getAddress().getCity());
        Assertions.assertEquals(location.getAddress().getCountry(),
                locationResult.getAddress().getCountry());
        Assertions.assertEquals(location.getAddress().getPostalCode(),
                locationResult.getAddress().getPostalCode());
        Assertions.assertEquals(location.getCoordinate().getLatitude(),
                locationResult.getCoordinate().getLatitude());
        Assertions.assertEquals(location.getCoordinate().getLongitude(),
                locationResult.getCoordinate().getLongitude());
    }

    @Test
    public void requestingALocationIdToDelete_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(LocationClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Location location = applicationDataProvider.getLocation();
        LocationDTO locationDTO = applicationDataProvider.getLocationDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(locationDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenThrow(RestClientException.class);
        Location locationResult = locationClient.deleteLocation(1L);

        //Then
        Assertions.assertEquals("[CLIENT, LOCATIONS, DELETED] Location id not found",
                logsList.get(1).getMessage());

    }

    @Test
    public void checkLocationResultList() {

        //Given
        Location location = applicationDataProvider.getLocation();
        LocationDTO locationDTO = applicationDataProvider.getLocationDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(Collections.singletonList(locationDTO), HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(responseEntity);
        when(locationMapper.mapLocationDTOListToLocationList(
                Collections.singletonList(locationDTO))).thenReturn(Collections.singletonList(location));
        List<Location> locationResult = locationClient.getAllLocations();

        //Then
        Assertions.assertEquals(location.getAddress().getName(),
                locationResult.get(0).getAddress().getName());
        Assertions.assertEquals(location.getAddress().getCity(),
                locationResult.get(0).getAddress().getCity());
        Assertions.assertEquals(location.getAddress().getCountry(),
                locationResult.get(0).getAddress().getCountry());
        Assertions.assertEquals(location.getAddress().getPostalCode(),
                locationResult.get(0).getAddress().getPostalCode());
        Assertions.assertEquals(location.getCoordinate().getLatitude(),
                locationResult.get(0).getCoordinate().getLatitude());
        Assertions.assertEquals(location.getCoordinate().getLongitude(),
                locationResult.get(0).getCoordinate().getLongitude());
    }

    @Test
    public void checkLogThrownWhenGettingTheLocationResultList() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(LocationClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        LocationDTO locationDTO = applicationDataProvider.getLocationDTO();

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);
        List<Location> locationResult = locationClient.getAllLocations();

        //Then
        Assertions.assertEquals("[CLIENT, LOCATIONS, RETRIEVED] Location list not found",
                logsList.get(1).getMessage());

    }
}
