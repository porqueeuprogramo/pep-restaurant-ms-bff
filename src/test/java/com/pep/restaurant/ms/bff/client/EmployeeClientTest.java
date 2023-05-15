package com.pep.restaurant.ms.bff.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.pep.restaurant.ms.bff.config.ApplicationProperties;
import com.pep.restaurant.ms.bff.domain.Employee;
import com.pep.restaurant.ms.bff.domain.Restaurant;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.service.mapper.EmployeeMapper;
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
public class EmployeeClientTest {

    @InjectMocks
    private EmployeeClient employeeClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private EmployeeMapper employeeMapper;

    private ApplicationProperties.MsManager msManagerProperty = new ApplicationProperties.MsManager();

    ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void requestingAnEmployeeIdToGet_checkEmployeeResult() {

        //Given
        Employee employee = applicationDataProvider.getEmployeeWithIdOne();
        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTOWithRestaurantIdZero();
        List<RestaurantDTO> employeeGivenRestaurantListDTO =
                new ArrayList<>(employeeDTO.getRestaurantList());
        ResponseEntity responseEntity = new ResponseEntity<>(employeeDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                anyMap())).thenReturn(responseEntity);
        when(employeeMapper.mapEmployeeDTOToEmployee(employeeDTO)).thenReturn(employee);
        Employee employeeResult = employeeClient.getEmployee("1L");
        List<Restaurant> employeeResultRestaurantList =
                new ArrayList<>(employeeResult.getRestaurantList());

        //Then
        Assertions.assertEquals(employee.getUid(), employeeResult.getUid());
        Assertions.assertEquals(employee.getRole(), employeeResult.getRole());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getUid(), employeeResultRestaurantList.get(0).getUid());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getLocationCoordinate().getLatitude()
                , employeeResultRestaurantList.get(0).getLocation().getCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getLocationCoordinate().getLongitude()
                , employeeResultRestaurantList.get(0).getLocation().getCoordinate().getLongitude(),0);
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getName(), employeeResultRestaurantList.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getHereId(), employeeResultRestaurantList.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getSchedule().getDaysScheduleMap().size(),
                employeeResultRestaurantList.get(0).getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());

    }

    @Test
    public void requestingAnEmployeeIdToGet_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(EmployeeClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Employee employee = applicationDataProvider.getEmployee();
        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(employeeDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                anyMap())).thenThrow(RestClientException.class);
        Employee employeeResult = employeeClient.getEmployee("1L");

        //Then
        Assertions.assertEquals("[CLIENT, EMPLOYEES, RETRIEVED] Employee id not found",
                logsList.get(1).getMessage());

    }

    @Test
    public void requestingAnEmployeeToCreate_checkEmployeeResult() {

        //Given
        Employee employee = applicationDataProvider.getEmployeeWithIdOne();
        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTOWithRestaurantIdZero();
        List<RestaurantDTO> employeeGivenRestaurantListDTO =
                new ArrayList<>(employeeDTO.getRestaurantList());
        ResponseEntity responseEntity = new ResponseEntity<>(employeeDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<EmployeeDTO>) any())).thenReturn(responseEntity);
        when(employeeMapper.mapEmployeeDTOToEmployee(employeeDTO)).thenReturn(employee);
        Employee employeeResult = employeeClient.createEmployee(employee);
        List<Restaurant> employeeResultRestaurantList =
                new ArrayList<>(employeeResult.getRestaurantList());

        //Then
        Assertions.assertEquals(employee.getUid(), employeeResult.getUid());
        Assertions.assertEquals(employee.getRole(), employeeResult.getRole());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getUid(), employeeResultRestaurantList.get(0).getUid());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getLocationCoordinate().getLatitude()
                , employeeResultRestaurantList.get(0).getLocation().getCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getLocationCoordinate().getLongitude()
                , employeeResultRestaurantList.get(0).getLocation().getCoordinate().getLongitude(),0);
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getName(), employeeResultRestaurantList.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getHereId(), employeeResultRestaurantList.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getSchedule().getDaysScheduleMap().size(),
                employeeResultRestaurantList.get(0).getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());

    }

    @Test
    public void requestingAnEmployeeToCreate_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(EmployeeClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Employee employee = applicationDataProvider.getEmployee();
        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(employeeDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<EmployeeDTO>) any())).thenThrow(RestClientException.class);
        Employee employeeResult = employeeClient.createEmployee(employee);

        //Then
        Assertions.assertEquals("[CLIENT, EMPLOYEES, PERSISTED] Employee was not created!!", logsList.get(1).getMessage());

    }

    @Test
    public void requestingAnEmployeeIdToEditAndAEmployeeEdited_checkEmployeeResult() {

        //Given
        Employee employee = applicationDataProvider.getEmployeeWithIdOne();
        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTOWithRestaurantIdZero();
        List<RestaurantDTO> employeeGivenRestaurantListDTO =
                new ArrayList<>(employeeDTO.getRestaurantList());
        ResponseEntity responseEntity = new ResponseEntity<>(employeeDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<EmployeeDTO>) any(), anyMap())).thenReturn(responseEntity);
        when(employeeMapper.mapEmployeeDTOToEmployee(employeeDTO)).thenReturn(employee);
        Employee employeeResult = employeeClient.editEmployee("1L", employee);
        List<Restaurant> employeeResultRestaurantList =
                new ArrayList<>(employeeResult.getRestaurantList());

        //Then
        Assertions.assertEquals(employee.getUid(), employeeResult.getUid());
        Assertions.assertEquals(employee.getRole(), employeeResult.getRole());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getUid(), employeeResultRestaurantList.get(0).getUid());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getLocationCoordinate().getLatitude()
                , employeeResultRestaurantList.get(0).getLocation().getCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getLocationCoordinate().getLongitude()
                , employeeResultRestaurantList.get(0).getLocation().getCoordinate().getLongitude(),0);
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getName(), employeeResultRestaurantList.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getHereId(), employeeResultRestaurantList.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getSchedule().getDaysScheduleMap().size(),
                employeeResultRestaurantList.get(0).getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());

    }

    @Test
    public void requestingAnEmployeeIdToEditAndAEmployeeEdited_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(EmployeeClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Employee employee = applicationDataProvider.getEmployee();
        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(employeeDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<EmployeeDTO>) any(), anyMap())).thenThrow(RestClientException.class);
        Employee employeeResult = employeeClient.editEmployee("1L", employee);

        //Then
        Assertions.assertEquals("[CLIENT, EMPLOYEES, EDITED] Employee was not edited!!",
                logsList.get(1).getMessage());

    }

    @Test
    public void requestingAnEmployeeIdAndARestaurantIdToBeAdded_checkEmployeeResult() {

        //Given
        Employee employee = applicationDataProvider.getEmployeeWithIdOne();
        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTOWithRestaurantIdZero();
        List<RestaurantDTO> employeeGivenRestaurantListDTO =
                new ArrayList<>(employeeDTO.getRestaurantList());
        ResponseEntity responseEntity = new ResponseEntity<>(employeeDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenReturn(responseEntity);
        when(employeeMapper.mapEmployeeDTOToEmployee(employeeDTO)).thenReturn(employee);
        Employee employeeResult = employeeClient.addRestaurant("1L", "1L");
        List<Restaurant> employeeResultRestaurantList =
                new ArrayList<>(employeeResult.getRestaurantList());

        //Then
        Assertions.assertEquals(employee.getUid(), employeeResult.getUid());
        Assertions.assertEquals(employee.getRole(), employeeResult.getRole());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getUid(), employeeResultRestaurantList.get(0).getUid());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getLocationCoordinate().getLatitude()
                , employeeResultRestaurantList.get(0).getLocation().getCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getLocationCoordinate().getLongitude()
                , employeeResultRestaurantList.get(0).getLocation().getCoordinate().getLongitude(),0);
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getName(), employeeResultRestaurantList.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getHereId(), employeeResultRestaurantList.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getSchedule().getDaysScheduleMap().size(),
                employeeResultRestaurantList.get(0).getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());

    }

    @Test
    public void requestingAnEmployeeIdAndARestaurantIdToBeAdded_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(EmployeeClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Employee employee = applicationDataProvider.getEmployee();
        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(employeeDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenThrow(RestClientException.class);
        Employee employeeResult = employeeClient.addRestaurant("1L", "1L");

        //Then
        Assertions.assertEquals("[CLIENT, EMPLOYEES, EDITED] Employee was not added to Employee!!",
                logsList.get(1).getMessage());

    }

    @Test
    public void requestingAnEmployeeIdAndARestaurantIdToBeRemoved_checkEmployeeResult() {

        //Given
        Employee employee = applicationDataProvider.getEmployeeWithoutRestaurantList();
        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTOWithoutRestaurantListDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(employeeDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenReturn(responseEntity);
        when(employeeMapper.mapEmployeeDTOToEmployee(employeeDTO)).thenReturn(employee);
        Employee employeeResult = employeeClient.removeRestaurant("1L", "1L");

        //Then
        Assertions.assertEquals(employee.getUid(), employeeResult.getUid());
        Assertions.assertEquals(employee.getRole(), employeeResult.getRole());
        Assertions.assertEquals(0, employeeResult.getRestaurantList().size());

    }

    @Test
    public void requestingAnEmployeeIdAndARestaurantIdToBeRemoved_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(EmployeeClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Employee employee = applicationDataProvider.getEmployee();
        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(employeeDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenThrow(RestClientException.class);
        Employee employeeResult = employeeClient.removeRestaurant("1L", "1L");

        //Then
        Assertions.assertEquals("[CLIENT, EMPLOYEES, EDITED] Employee was not removed from Employee!!",
                logsList.get(1).getMessage());

    }

    @Test
    public void requestingAnEmployeeIdToDelete_checkEmployeeResult() {

        //Given
        Employee employee = applicationDataProvider.getEmployeeWithIdOne();
        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTOWithRestaurantIdZero();
        List<RestaurantDTO> employeeGivenRestaurantListDTO =
                new ArrayList<>(employeeDTO.getRestaurantList());
        ResponseEntity responseEntity = new ResponseEntity<>(employeeDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenReturn(responseEntity);
        when(employeeMapper.mapEmployeeDTOToEmployee(employeeDTO)).thenReturn(employee);
        Employee employeeResult = employeeClient.deleteEmployee("1L");
        List<Restaurant> employeeResultRestaurantList =
                new ArrayList<>(employeeResult.getRestaurantList());

        //Then
        Assertions.assertEquals(employee.getUid(), employeeResult.getUid());
        Assertions.assertEquals(employee.getRole(), employeeResult.getRole());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getUid(), employeeResultRestaurantList.get(0).getUid());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getLocationCoordinate().getLatitude()
                , employeeResultRestaurantList.get(0).getLocation().getCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getLocationCoordinate().getLongitude()
                , employeeResultRestaurantList.get(0).getLocation().getCoordinate().getLongitude(),0);
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getName(), employeeResultRestaurantList.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getHereId(), employeeResultRestaurantList.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getSchedule().getDaysScheduleMap().size(),
                employeeResultRestaurantList.get(0).getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());

    }

    @Test
    public void requestingAnEmployeeIdToDelete_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(EmployeeClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Employee employee = applicationDataProvider.getEmployee();
        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(employeeDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenThrow(RestClientException.class);
        Employee employeeResult = employeeClient.deleteEmployee("1L");

        //Then
        Assertions.assertEquals("[CLIENT, EMPLOYEES, DELETED] Employee id not found",
                logsList.get(1).getMessage());

    }

    @Test
    public void checkEmployeeResultList() {

        //Given
        Employee employee = applicationDataProvider.getEmployeeWithIdOne();
        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTOWithRestaurantIdZero();
        List<RestaurantDTO> employeeGivenRestaurantListDTO =
                new ArrayList<>(employeeDTO.getRestaurantList());
        ResponseEntity responseEntity = new ResponseEntity<>(Collections.singletonList(employeeDTO), HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(responseEntity);
        when(employeeMapper.mapEmployeeDTOListToEmployeeList(
                Collections.singletonList(employeeDTO))).thenReturn(Collections.singletonList(employee));
        List<Employee> employeeResult = employeeClient.getAllEmployees();
        List<Restaurant> employeeResultRestaurantList =
                new ArrayList<>(employeeResult.get(0).getRestaurantList());

        //Then
        Assertions.assertEquals(employee.getUid(), employeeResult.get(0).getUid());
        Assertions.assertEquals(employee.getRole(), employeeResult.get(0).getRole());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getUid(), employeeResultRestaurantList.get(0).getUid());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getLocationCoordinate().getLatitude()
                , employeeResultRestaurantList.get(0).getLocation().getCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation().getLocationCoordinate().getLongitude()
                , employeeResultRestaurantList.get(0).getLocation().getCoordinate().getLongitude(),0);
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getName(), employeeResultRestaurantList.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getHereId(), employeeResultRestaurantList.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getSchedule().getDaysScheduleMap().size(),
                employeeResultRestaurantList.get(0).getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());

    }

    @Test
    public void checkLogThrownWhenGettingTheEmployeeResultList() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(EmployeeClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Employee employee = applicationDataProvider.getEmployee();
        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(Collections.singletonList(employeeDTO), HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);
        List<Employee> employeeResult = employeeClient.getAllEmployees();

        //Then
        Assertions.assertEquals("[CLIENT, EMPLOYEES, RETRIEVED] Employee list not found",
                logsList.get(1).getMessage());


    }




}
