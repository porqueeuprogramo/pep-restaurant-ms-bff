package com.pep.restaurant.ms.bff.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.pep.restaurant.ms.bff.config.ApplicationProperties;
import com.pep.restaurant.ms.bff.domain.Restaurant;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.service.mapper.RestaurantMapper;
import com.pep.restaurant.ms.bff.web.api.model.RestaurantDTO;
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
public class RestaurantClientTest {

    @InjectMocks
    private RestaurantClient restaurantClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private RestaurantMapper restaurantMapper;

    private ApplicationProperties.MsManager msManagerProperty = new ApplicationProperties.MsManager();

    ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void requestingARestaurantIdToGet_checkRestaurantResult() {

        //Given
        Restaurant restaurant = applicationDataProvider.getRestaurant();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(restaurantDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                anyMap())).thenReturn(responseEntity);
        when(restaurantMapper.mapRestaurantDTOToRestaurant(restaurantDTO)).thenReturn(restaurant);
        Restaurant restaurantResult = restaurantClient.getRestaurant(1L);

        //Then
        Assertions.assertEquals(restaurant.getId(), restaurantResult.getId());
        Assertions.assertEquals(restaurant.getName(), restaurantResult.getName());
        Assertions.assertEquals(restaurant.getLocation(), restaurantResult.getLocation());
        Assertions.assertEquals(restaurant.getCapacity(), restaurantResult.getCapacity());
        Assertions.assertEquals(restaurant.getMenu().getLanguage(), restaurantResult.getMenu().getLanguage());
        Assertions.assertEquals(0, restaurantResult.getEmployeeList().size());
    }

    @Test
    public void requestingARestaurantIdToGet_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(RestaurantClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Restaurant restaurant = applicationDataProvider.getRestaurant();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(restaurantDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                anyMap())).thenThrow(RestClientException.class);
        Restaurant restaurantResult = restaurantClient.getRestaurant(1L);

        //Then
        Assertions.assertEquals("[CLIENT, RESTAURANTS, RETRIEVED] Restaurant id not found",
                logsList.get(1).getMessage());
    }


    @Test
    public void requestingARestaurantToCreate_checkRestaurantResult() {

        //Given
        Restaurant restaurant = applicationDataProvider.getRestaurant();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(restaurantDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<RestaurantDTO>) any())).thenReturn(responseEntity);
        when(restaurantMapper.mapRestaurantDTOToRestaurant(restaurantDTO)).thenReturn(restaurant);
        Restaurant restaurantResult = restaurantClient.createRestaurant(restaurant);

        //Then
        Assertions.assertEquals(restaurant.getId(), restaurantResult.getId());
        Assertions.assertEquals(restaurant.getName(), restaurantResult.getName());
        Assertions.assertEquals(restaurant.getLocation(), restaurantResult.getLocation());
        Assertions.assertEquals(restaurant.getCapacity(), restaurantResult.getCapacity());
        Assertions.assertEquals(restaurant.getMenu().getLanguage(), restaurantResult.getMenu().getLanguage());
        Assertions.assertEquals(0, restaurantResult.getEmployeeList().size());
    }

    @Test
    public void requestingARestaurantToCreate_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(RestaurantClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Restaurant restaurant = applicationDataProvider.getRestaurant();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(restaurantDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<RestaurantDTO>) any())).thenThrow(RestClientException.class);
        Restaurant restaurantResult = restaurantClient.createRestaurant(restaurant);

        //Then
        Assertions.assertEquals("[CLIENT, RESTAURANTS, PERSISTED] Restaurant was not created!!",
                logsList.get(1).getMessage());

    }

    @Test
    public void requestingARestaurantIdToEditAndARestaurantEdited_checkRestaurantResult() {

        //Given
        Restaurant restaurant = applicationDataProvider.getRestaurant();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(restaurantDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<RestaurantDTO>) any(), anyMap())).thenReturn(responseEntity);
        when(restaurantMapper.mapRestaurantDTOToRestaurant(restaurantDTO)).thenReturn(restaurant);
        Restaurant restaurantResult = restaurantClient.editRestaurant(1L, restaurant);

        //Then
        Assertions.assertEquals(restaurant.getId(), restaurantResult.getId());
        Assertions.assertEquals(restaurant.getName(), restaurantResult.getName());
        Assertions.assertEquals(restaurant.getLocation(), restaurantResult.getLocation());
        Assertions.assertEquals(restaurant.getCapacity(), restaurantResult.getCapacity());
        Assertions.assertEquals(restaurant.getMenu().getLanguage(), restaurantResult.getMenu().getLanguage());
        Assertions.assertEquals(0, restaurantResult.getEmployeeList().size());
    }

    @Test
    public void requestingARestaurantIdToEditAndARestaurantEdited_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(RestaurantClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Restaurant restaurant = applicationDataProvider.getRestaurant();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(restaurantDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<RestaurantDTO>) any(), anyMap())).thenThrow(RestClientException.class);
        Restaurant restaurantResult = restaurantClient.editRestaurant(1L, restaurant);

        //Then
        Assertions.assertEquals("[CLIENT, RESTAURANTS, EDITED] Restaurant was not edited!!",
                logsList.get(1).getMessage());
    }

    @Test
    public void requestingARestaurantIdAndAnEmployeeIdToBeAdded_checkRestaurantResult() {

        //Given
        Restaurant restaurant = applicationDataProvider.getRestaurant();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(restaurantDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenReturn(responseEntity);
        when(restaurantMapper.mapRestaurantDTOToRestaurant(restaurantDTO)).thenReturn(restaurant);
        Restaurant restaurantResult = restaurantClient.addEmployee(1L, 1L);

        //Then
        Assertions.assertEquals(restaurant.getId(), restaurantResult.getId());
        Assertions.assertEquals(restaurant.getName(), restaurantResult.getName());
        Assertions.assertEquals(restaurant.getLocation(), restaurantResult.getLocation());
        Assertions.assertEquals(restaurant.getCapacity(), restaurantResult.getCapacity());
        Assertions.assertEquals(restaurant.getMenu().getLanguage(), restaurantResult.getMenu().getLanguage());
        Assertions.assertEquals(0, restaurantResult.getEmployeeList().size());
    }

    @Test
    public void requestingARestaurantIdAndAnEmployeeIdToBeAdded_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(RestaurantClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Restaurant restaurant = applicationDataProvider.getRestaurant();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(restaurantDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenThrow(RestClientException.class);
        Restaurant restaurantResult = restaurantClient.addEmployee(1L, 1L);

        //Then
        Assertions.assertEquals("[CLIENT, RESTAURANTS, EDITED] Employee was not added to Restaurant!!",
                logsList.get(1).getMessage());
    }

    @Test
    public void requestingARestaurantIdAndAnEmployeeIdToBeRemoved_checkRestaurantResult() {

        //Given
        Restaurant restaurant = applicationDataProvider.getRestaurant();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(restaurantDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenReturn(responseEntity);
        when(restaurantMapper.mapRestaurantDTOToRestaurant(restaurantDTO)).thenReturn(restaurant);
        Restaurant restaurantResult = restaurantClient.removeEmployee(1L, 1L);

        //Then
        Assertions.assertEquals(restaurant.getId(), restaurantResult.getId());
        Assertions.assertEquals(restaurant.getName(), restaurantResult.getName());
        Assertions.assertEquals(restaurant.getLocation(), restaurantResult.getLocation());
        Assertions.assertEquals(restaurant.getCapacity(), restaurantResult.getCapacity());
        Assertions.assertEquals(restaurant.getMenu().getLanguage(), restaurantResult.getMenu().getLanguage());
        Assertions.assertEquals(0, restaurantResult.getEmployeeList().size());
    }

    @Test
    public void requestingARestaurantIdAndAnEmployeeIdToBeRemoved_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(RestaurantClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Restaurant restaurant = applicationDataProvider.getRestaurant();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(restaurantDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenThrow(RestClientException.class);
        Restaurant restaurantResult = restaurantClient.removeEmployee(1L, 1L);

        //Then
        Assertions.assertEquals("[CLIENT, RESTAURANTS, EDITED] Employee was not removed from Restaurant!!",
                logsList.get(1).getMessage());
    }

    @Test
    public void requestingARestaurantIdToDelete_checkRestaurantResult() {

        //Given
        Restaurant restaurant = applicationDataProvider.getRestaurant();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(restaurantDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenReturn(responseEntity);
        when(restaurantMapper.mapRestaurantDTOToRestaurant(restaurantDTO)).thenReturn(restaurant);
        Restaurant restaurantResult = restaurantClient.deleteRestaurant(1L);

        //Then
        Assertions.assertEquals(restaurant.getId(), restaurantResult.getId());
        Assertions.assertEquals(restaurant.getName(), restaurantResult.getName());
        Assertions.assertEquals(restaurant.getLocation(), restaurantResult.getLocation());
        Assertions.assertEquals(restaurant.getCapacity(), restaurantResult.getCapacity());
        Assertions.assertEquals(restaurant.getMenu().getLanguage(), restaurantResult.getMenu().getLanguage());
        Assertions.assertEquals(0, restaurantResult.getEmployeeList().size());
    }

    @Test
    public void requestingARestaurantIdToDelete_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(RestaurantClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Restaurant restaurant = applicationDataProvider.getRestaurant();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(restaurantDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenThrow(RestClientException.class);
        Restaurant restaurantResult = restaurantClient.deleteRestaurant(1L);

        //Then
        Assertions.assertEquals("[CLIENT, RESTAURANTS, DELETED] Restaurant id not found",
                logsList.get(1).getMessage());

    }

    @Test
    public void checkRestaurantResultList() {

        //Given
        Restaurant restaurant = applicationDataProvider.getRestaurant();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(Collections.singletonList(restaurantDTO), HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(responseEntity);
        when(restaurantMapper.mapRestaurantDTOListToRestaurantList(
                Collections.singletonList(restaurantDTO))).thenReturn(Collections.singletonList(restaurant));
        List<Restaurant> restaurantResult = restaurantClient.getAllRestaurants();

        //Then
        Assertions.assertEquals(restaurant.getId(), restaurantResult.get(0).getId());
        Assertions.assertEquals(restaurant.getName(), restaurantResult.get(0).getName());
        Assertions.assertEquals(restaurant.getLocation(), restaurantResult.get(0).getLocation());
        Assertions.assertEquals(restaurant.getCapacity(), restaurantResult.get(0).getCapacity());
        Assertions.assertEquals(restaurant.getMenu().getLanguage(), restaurantResult.get(0).getMenu().getLanguage());
        Assertions.assertEquals(0, restaurantResult.get(0).getEmployeeList().size());
    }

    @Test
    public void checkLogThrownWhenGettingTheRestaurantResultList() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(RestaurantClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Restaurant restaurant = applicationDataProvider.getRestaurant();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(Collections.singletonList(restaurantDTO), HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);
        List<Restaurant> restaurantResult = restaurantClient.getAllRestaurants();

        //Then
        Assertions.assertEquals("[CLIENT, RESTAURANTS, RETRIEVED] Restaurant list not found",
                logsList.get(1).getMessage());

    }

}
