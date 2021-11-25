package com.pep.restaurant.ms.bff.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.pep.restaurant.ms.bff.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.config.ApplicationProperties;
import com.pep.restaurant.ms.bff.domain.Schedule;
import com.pep.restaurant.ms.bff.service.mapper.ScheduleMapper;
import com.pep.restaurant.ms.bff.web.api.model.ScheduleDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyMap;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleClientTest {

    @InjectMocks
    private ScheduleClient scheduleClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private ScheduleMapper scheduleMapper;

    private ApplicationProperties.MsManager msManagerProperty = new ApplicationProperties.MsManager();

    ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void requestingAScheduleIdToGet_checkScheduleResult() {

        //Given
        Schedule schedule = applicationDataProvider.getSchedule();
        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(scheduleDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                anyMap())).thenReturn(responseEntity);
        when(scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO)).thenReturn(schedule);
        Schedule scheduleResult = scheduleClient.getSchedule(1L);

        //Then
        Assert.assertEquals(schedule.getId(), scheduleResult.getId());
        Assert.assertEquals(schedule.getType().name(), scheduleResult.getType().name());
        Assert.assertEquals(schedule.getEmployeeList().size(), scheduleResult.getEmployeeList().size());

    }

    @Test
    public void requestingAScheduleIdToGet_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(ScheduleClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Schedule schedule = applicationDataProvider.getSchedule();
        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(scheduleDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                anyMap())).thenThrow(RestClientException.class);
        Schedule scheduleResult = scheduleClient.getSchedule(1L);

        //Then
        Assert.assertEquals("[CLIENT, SCHEDULES, RETRIEVED] Schedule id not found",
                logsList.get(0).getMessage());

    }

    @Test
    public void requestingAScheduleToCreate_checkScheduleResult() {

        //Given
        Schedule schedule = applicationDataProvider.getSchedule();
        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(scheduleDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<ScheduleDTO>) any())).thenReturn(responseEntity);
        when(scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO)).thenReturn(schedule);
        Schedule scheduleResult = scheduleClient.createSchedule(schedule);

        //Then
        Assert.assertEquals(schedule.getId(), scheduleResult.getId());
        Assert.assertEquals(schedule.getType().name(), scheduleResult.getType().name());
        Assert.assertEquals(schedule.getEmployeeList().size(), scheduleResult.getEmployeeList().size());

    }

    @Test
    public void requestingAScheduleToCreate_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(ScheduleClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Schedule schedule = applicationDataProvider.getSchedule();
        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(scheduleDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<ScheduleDTO>) any())).thenThrow(RestClientException.class);
        Schedule scheduleResult = scheduleClient.createSchedule(schedule);

        //Then
        Assert.assertEquals("[CLIENT, SCHEDULES, PERSISTED] Schedule was not created!!",
                logsList.get(0).getMessage());

    }


    @Test
    public void requestingAScheduleIdToEditAndAScheduleEdited_checkScheduleResult() {

        //Given
        Schedule schedule = applicationDataProvider.getSchedule();
        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(scheduleDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<ScheduleDTO>) any(), anyMap())).thenReturn(responseEntity);
        when(scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO)).thenReturn(schedule);
        Schedule scheduleResult = scheduleClient.editSchedule(1L, schedule);

        //Then
        Assert.assertEquals(schedule.getId(), scheduleResult.getId());
        Assert.assertEquals(schedule.getType().name(), scheduleResult.getType().name());
        Assert.assertEquals(schedule.getEmployeeList().size(), scheduleResult.getEmployeeList().size());

    }

    @Test
    public void requestingAScheduleIdToEditAndAScheduleEdited_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(ScheduleClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Schedule schedule = applicationDataProvider.getSchedule();
        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(scheduleDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<ScheduleDTO>) any(), anyMap())).thenThrow(RestClientException.class);
        Schedule scheduleResult = scheduleClient.editSchedule(1L, schedule);

        //Then
        Assert.assertEquals("[CLIENT, SCHEDULES, EDITED] Schedule was not edited!!",
                logsList.get(0).getMessage());

    }

    @Test
    public void requestingAScheduleIdAndAnEmployeeIdToBeAdded_checkScheduleResult() {

        //Given
        Schedule schedule = applicationDataProvider.getSchedule();
        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(scheduleDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenReturn(responseEntity);
        when(scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO)).thenReturn(schedule);
        Schedule scheduleResult = scheduleClient.addEmployee(1L, 1L);

        //Then
        Assert.assertEquals(schedule.getId(), scheduleResult.getId());
        Assert.assertEquals(schedule.getType().name(), scheduleResult.getType().name());
        Assert.assertEquals(schedule.getEmployeeList().size(), scheduleResult.getEmployeeList().size());

    }

    @Test
    public void requestingAScheduleIdAndAnEmployeeIdToBeAdded_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(ScheduleClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Schedule schedule = applicationDataProvider.getSchedule();
        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(scheduleDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenThrow(RestClientException.class);
        Schedule scheduleResult = scheduleClient.addEmployee(1L, 1L);

        //Then
        Assert.assertEquals("[CLIENT, SCHEDULES, EDITED] Employee was not added to Schedule!!",
                logsList.get(0).getMessage());

    }

    @Test
    public void requestingAScheduleIdAndAnEmployeeIdToBeRemoved_checkScheduleResult() {

        //Given
        Schedule schedule = applicationDataProvider.getSchedule();
        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(scheduleDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenReturn(responseEntity);
        when(scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO)).thenReturn(schedule);
        Schedule scheduleResult = scheduleClient.removeEmployee(1L, 1L);

        //Then
        Assert.assertEquals(schedule.getId(), scheduleResult.getId());
        Assert.assertEquals(schedule.getType().name(), scheduleResult.getType().name());
        Assert.assertEquals(schedule.getEmployeeList().size(), scheduleResult.getEmployeeList().size());

    }

    @Test
    public void requestingAScheduleIdAndAnEmployeeIdToBeRemoved_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(ScheduleClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Schedule schedule = applicationDataProvider.getSchedule();
        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(scheduleDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenThrow(RestClientException.class);
        Schedule scheduleResult = scheduleClient.removeEmployee(1L, 1L);

        //Then
        Assert.assertEquals("[CLIENT, SCHEDULES, EDITED] Employee was not removed from Schedule!!",
                logsList.get(0).getMessage());

    }

    @Test
    public void requestingAScheduleIdToDelete_checkScheduleResult() {

        //Given
        Schedule schedule = applicationDataProvider.getSchedule();
        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(scheduleDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenReturn(responseEntity);
        when(scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO)).thenReturn(schedule);
        Schedule scheduleResult = scheduleClient.deleteSchedule(1L);

        //Then
        Assert.assertEquals(schedule.getId(), scheduleResult.getId());
        Assert.assertEquals(schedule.getType().name(), scheduleResult.getType().name());
        Assert.assertEquals(schedule.getEmployeeList().size(), scheduleResult.getEmployeeList().size());

    }

    @Test
    public void requestingAScheduleIdToDelete_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(ScheduleClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Schedule schedule = applicationDataProvider.getSchedule();
        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(scheduleDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenThrow(RestClientException.class);
        Schedule scheduleResult = scheduleClient.deleteSchedule(1L);

        //Then
        Assert.assertEquals("[CLIENT, SCHEDULES, DELETED] Schedule id not found",
                logsList.get(0).getMessage());

    }

    @Test
    public void checkScheduleResultList() {

        //Given
        Schedule schedule = applicationDataProvider.getSchedule();
        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(Collections.singletonList(scheduleDTO), HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(responseEntity);
        when(scheduleMapper.mapScheduleDTOListToScheduleList(
                Collections.singletonList(scheduleDTO))).thenReturn(Collections.singletonList(schedule));
        List<Schedule> scheduleResult = scheduleClient.getAllSchedules();

        //Then
        Assert.assertEquals(schedule.getId(), scheduleResult.get(0).getId());
        Assert.assertEquals(schedule.getType().name(), scheduleResult.get(0).getType().name());
        Assert.assertEquals(schedule.getEmployeeList().size(), scheduleResult.get(0).getEmployeeList().size());

    }

    @Test
    public void checkLogThrownWhenGettingTheScheduleResultList() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(ScheduleClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Schedule schedule = applicationDataProvider.getSchedule();
        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(Collections.singletonList(scheduleDTO), HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);
        List<Schedule> scheduleResult = scheduleClient.getAllSchedules();

        //Then
        Assert.assertEquals("[CLIENT, SCHEDULES, RETRIEVED] Schedule list not found",
                logsList.get(0).getMessage());

    }

}
