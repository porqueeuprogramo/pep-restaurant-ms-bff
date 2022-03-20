package com.pep.restaurant.ms.bff.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.pep.restaurant.ms.bff.config.ApplicationProperties;
import com.pep.restaurant.ms.bff.domain.Employee;
import com.pep.restaurant.ms.bff.domain.Restaurant;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.service.mapper.RestaurantMapper;
import com.pep.restaurant.ms.bff.web.api.model.EmployeeDTO;
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

import java.util.ArrayList;
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
        Restaurant restaurant = applicationDataProvider.getRestaurantWithEmployees();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployeeListDTO =
                new ArrayList<>(restaurantDTO.getEmployeeList());
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
        List<Employee> restaurantResultEmployeeList =
                new ArrayList<>(restaurantResult.getEmployeeList());

        //Then
        Assertions.assertEquals(restaurant.getId(), restaurantResult.getId());
        Assertions.assertEquals(restaurant.getMenu().getId(), restaurantResult.getMenu().getId());
        Assertions.assertEquals(restaurant.getMenu().getLanguage(), restaurantResult.getMenu().getLanguage());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getName(),
                restaurantResult.getLocation().getAddress().getName());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getCity(),
                restaurantResult.getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getCountry(),
                restaurantResult.getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getPostalCode(),
                restaurantResult.getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurant.getLocation().getCoordinate().getLatitude(),
                restaurantResult.getLocation().getCoordinate().getLatitude());
        Assertions.assertEquals(restaurant.getLocation().getCoordinate().getLongitude(),
                restaurantResult.getLocation().getCoordinate().getLongitude());
        Assertions.assertEquals(restaurant.getHereId(),
                restaurantResult.getHereId());
        Assertions.assertEquals(restaurant.getSchedule().getScheduleRoutine().size(),
                restaurantResult.getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(restaurant.getCapacity(), restaurant.getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getId(),
                restaurantResultEmployeeList.get(0).getId());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getRole(),
                restaurantResultEmployeeList.get(0).getRole());

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
        Restaurant restaurant = applicationDataProvider.getRestaurantWithEmployees();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployeeListDTO =
                new ArrayList<>(restaurantDTO.getEmployeeList());
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
        List<Employee> restaurantResultEmployeeList =
                new ArrayList<>(restaurantResult.getEmployeeList());

        //Then
        Assertions.assertEquals(restaurant.getId(), restaurantResult.getId());
        Assertions.assertEquals(restaurant.getMenu().getId(), restaurantResult.getMenu().getId());
        Assertions.assertEquals(restaurant.getMenu().getLanguage(), restaurantResult.getMenu().getLanguage());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getName(),
                restaurantResult.getLocation().getAddress().getName());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getCity(),
                restaurantResult.getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getCountry(),
                restaurantResult.getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getPostalCode(),
                restaurantResult.getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurant.getLocation().getCoordinate().getLatitude(),
                restaurantResult.getLocation().getCoordinate().getLatitude());
        Assertions.assertEquals(restaurant.getLocation().getCoordinate().getLongitude(),
                restaurantResult.getLocation().getCoordinate().getLongitude());
        Assertions.assertEquals(restaurant.getHereId(),
                restaurantResult.getHereId());
        Assertions.assertEquals(restaurant.getSchedule().getScheduleRoutine().size(),
                restaurantResult.getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(restaurant.getCapacity(), restaurant.getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getId(),
                restaurantResultEmployeeList.get(0).getId());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getRole(),
                restaurantResultEmployeeList.get(0).getRole());
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
        Restaurant restaurant = applicationDataProvider.getRestaurantWithEmployees();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployeeListDTO =
                new ArrayList<>(restaurantDTO.getEmployeeList());
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
        List<Employee> restaurantResultEmployeeList =
                new ArrayList<>(restaurantResult.getEmployeeList());

        //Then
        Assertions.assertEquals(restaurant.getId(), restaurantResult.getId());
        Assertions.assertEquals(restaurant.getMenu().getId(), restaurantResult.getMenu().getId());
        Assertions.assertEquals(restaurant.getMenu().getLanguage(), restaurantResult.getMenu().getLanguage());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getName(),
                restaurantResult.getLocation().getAddress().getName());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getCity(),
                restaurantResult.getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getCountry(),
                restaurantResult.getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getPostalCode(),
                restaurantResult.getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurant.getLocation().getCoordinate().getLatitude(),
                restaurantResult.getLocation().getCoordinate().getLatitude());
        Assertions.assertEquals(restaurant.getLocation().getCoordinate().getLongitude(),
                restaurantResult.getLocation().getCoordinate().getLongitude());
        Assertions.assertEquals(restaurant.getHereId(),
                restaurantResult.getHereId());
        Assertions.assertEquals(restaurant.getSchedule().getScheduleRoutine().size(),
                restaurantResult.getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(restaurant.getCapacity(), restaurant.getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getId(),
                restaurantResultEmployeeList.get(0).getId());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getRole(),
                restaurantResultEmployeeList.get(0).getRole());
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
        Restaurant restaurant = applicationDataProvider.getRestaurantWithEmployees();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployeeListDTO =
                new ArrayList<>(restaurantDTO.getEmployeeList());
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
        List<Employee> restaurantResultEmployeeList =
                new ArrayList<>(restaurantResult.getEmployeeList());

        //Then
        Assertions.assertEquals(restaurant.getId(), restaurantResult.getId());
        Assertions.assertEquals(restaurant.getMenu().getId(), restaurantResult.getMenu().getId());
        Assertions.assertEquals(restaurant.getMenu().getLanguage(), restaurantResult.getMenu().getLanguage());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getName(),
                restaurantResult.getLocation().getAddress().getName());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getCity(),
                restaurantResult.getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getCountry(),
                restaurantResult.getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getPostalCode(),
                restaurantResult.getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurant.getLocation().getCoordinate().getLatitude(),
                restaurantResult.getLocation().getCoordinate().getLatitude());
        Assertions.assertEquals(restaurant.getLocation().getCoordinate().getLongitude(),
                restaurantResult.getLocation().getCoordinate().getLongitude());
        Assertions.assertEquals(restaurant.getHereId(),
                restaurantResult.getHereId());
        Assertions.assertEquals(restaurant.getSchedule().getScheduleRoutine().size(),
                restaurantResult.getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(restaurant.getCapacity(), restaurant.getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getId(),
                restaurantResultEmployeeList.get(0).getId());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getRole(),
                restaurantResultEmployeeList.get(0).getRole());
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
        Assertions.assertEquals(restaurant.getMenu().getId(), restaurantResult.getMenu().getId());
        Assertions.assertEquals(restaurant.getMenu().getLanguage(), restaurantResult.getMenu().getLanguage());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getName(),
                restaurantResult.getLocation().getAddress().getName());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getCity(),
                restaurantResult.getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getCountry(),
                restaurantResult.getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getPostalCode(),
                restaurantResult.getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurant.getLocation().getCoordinate().getLatitude(),
                restaurantResult.getLocation().getCoordinate().getLatitude());
        Assertions.assertEquals(restaurant.getLocation().getCoordinate().getLongitude(),
                restaurantResult.getLocation().getCoordinate().getLongitude());
        Assertions.assertEquals(restaurant.getHereId(),
                restaurantResult.getHereId());
        Assertions.assertEquals(restaurant.getSchedule().getScheduleRoutine().size(),
                restaurantResult.getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(restaurant.getCapacity(), restaurant.getCapacity());
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
        Restaurant restaurant = applicationDataProvider.getRestaurantWithEmployees();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployeeListDTO =
                new ArrayList<>(restaurantDTO.getEmployeeList());
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
        List<Employee> restaurantResultEmployeeList =
                new ArrayList<>(restaurantResult.getEmployeeList());

        //Then
        Assertions.assertEquals(restaurant.getId(), restaurantResult.getId());
        Assertions.assertEquals(restaurant.getMenu().getId(), restaurantResult.getMenu().getId());
        Assertions.assertEquals(restaurant.getMenu().getLanguage(), restaurantResult.getMenu().getLanguage());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getName(),
                restaurantResult.getLocation().getAddress().getName());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getCity(),
                restaurantResult.getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getCountry(),
                restaurantResult.getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getPostalCode(),
                restaurantResult.getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurant.getLocation().getCoordinate().getLatitude(),
                restaurantResult.getLocation().getCoordinate().getLatitude());
        Assertions.assertEquals(restaurant.getLocation().getCoordinate().getLongitude(),
                restaurantResult.getLocation().getCoordinate().getLongitude());
        Assertions.assertEquals(restaurant.getHereId(),
                restaurantResult.getHereId());
        Assertions.assertEquals(restaurant.getSchedule().getScheduleRoutine().size(),
                restaurantResult.getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(restaurant.getCapacity(), restaurant.getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getId(),
                restaurantResultEmployeeList.get(0).getId());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getRole(),
                restaurantResultEmployeeList.get(0).getRole());
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
        Restaurant restaurant = applicationDataProvider.getRestaurantWithEmployees();
        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployeeListDTO =
                new ArrayList<>(restaurantDTO.getEmployeeList());
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
        List<Employee> restaurantResultEmployeeList =
                new ArrayList<>(restaurantResult.get(0).getEmployeeList());

        //Then
        Assertions.assertEquals(restaurant.getId(), restaurantResult.get(0).getId());
        Assertions.assertEquals(restaurant.getMenu().getId(), restaurantResult.get(0).getMenu().getId());
        Assertions.assertEquals(restaurant.getMenu().getLanguage(), restaurantResult.get(0).getMenu().getLanguage());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getName(),
                restaurantResult.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getCity(),
                restaurantResult.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getCountry(),
                restaurantResult.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurant.getLocation().getAddress().getPostalCode(),
                restaurantResult.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurant.getLocation().getCoordinate().getLatitude(),
                restaurantResult.get(0).getLocation().getCoordinate().getLatitude());
        Assertions.assertEquals(restaurant.getLocation().getCoordinate().getLongitude(),
                restaurantResult.get(0).getLocation().getCoordinate().getLongitude());
        Assertions.assertEquals(restaurant.getHereId(),
                restaurantResult.get(0).getHereId());
        Assertions.assertEquals(restaurant.getSchedule().getScheduleRoutine().size(),
                restaurantResult.get(0).getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(restaurant.getCapacity(), restaurant.getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getId(),
                restaurantResultEmployeeList.get(0).getId());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getRole(),
                restaurantResultEmployeeList.get(0).getRole());
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
