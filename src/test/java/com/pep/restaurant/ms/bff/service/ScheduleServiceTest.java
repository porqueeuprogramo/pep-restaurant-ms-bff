package com.pep.restaurant.ms.bff.service;

import com.pep.restaurant.ms.bff.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.client.ScheduleClient;
import com.pep.restaurant.ms.bff.client.ScheduleClient;
import com.pep.restaurant.ms.bff.domain.Schedule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceTest {

    @InjectMocks
    private ScheduleService scheduleService;

    @Mock
    private ScheduleClient scheduleClient;

    private ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void requestingAScheduleId_checkIfGetScheduleClientIsCalled() {
        //Given
        Schedule scheduleGiven = applicationDataProvider.getSchedule();

        //When
        when(scheduleClient.getSchedule(0L)).thenReturn(scheduleGiven);
        Schedule schedule = scheduleService.getSchedule(0L);

        //Then
        verify(scheduleClient, Mockito.times(1)).getSchedule(0L);
    }


    @Test
    public void requestingASchedule_checkIfCreateScheduleClientIsCalled() {
        //Given
        Schedule scheduleGiven = applicationDataProvider.getSchedule();

        //When
        when(scheduleClient.createSchedule(scheduleGiven)).thenReturn(scheduleGiven);
        Schedule schedule = scheduleService.createSchedule(scheduleGiven);

        //Then
        verify(scheduleClient, Mockito.times(1)).createSchedule(scheduleGiven);
    }

    @Test
    public void requestingAScheduleIdAndAnSchedule_checkIfEditScheduleClientIsCalled() {
        //Given
        Schedule scheduleGiven = applicationDataProvider.getSchedule();

        //When
        when(scheduleClient.editSchedule(0L, scheduleGiven)).thenReturn(scheduleGiven);
        Schedule schedule = scheduleService.editSchedule(0L, scheduleGiven);

        //Then
        verify(scheduleClient, Mockito.times(1)).editSchedule(0L, scheduleGiven);
    }


    @Test
    public void requestingAScheduleIdAndAEmployeeId_checkIfAddEmployeeClientIsCalled() {
        //Given
        Schedule scheduleGiven = applicationDataProvider.getSchedule();

        //When
        when(scheduleClient.addEmployee(0L, 0L)).thenReturn(scheduleGiven);
        Schedule schedule = scheduleService.addEmployee(0L, 0L);

        //Then
        verify(scheduleClient, Mockito.times(1)).addEmployee(0L, 0L);
    }


    @Test
    public void requestingAScheduleIdAndAEmployeeId_checkIfRemoveEmployeeClientIsCalled() {
        //Given
        Schedule scheduleGiven = applicationDataProvider.getSchedule();

        //When
        when(scheduleClient.removeEmployee(0L, 0L)).thenReturn(scheduleGiven);
        Schedule schedule = scheduleService.removeEmployee(0L, 0L);

        //Then
        verify(scheduleClient, Mockito.times(1)).removeEmployee(0L, 0L);
    }


    @Test
    public void requestingAScheduleId_checkIfDeleteScheduleClientIsCalled() {
        //Given
        Schedule scheduleGiven = applicationDataProvider.getSchedule();

        //When
        when(scheduleClient.deleteSchedule(0L)).thenReturn(scheduleGiven);
        Schedule schedule = scheduleService.deleteSchedule(0L);

        //Then
        verify(scheduleClient, Mockito.times(1)).deleteSchedule(0L);
    }

    @Test
    public void checkIfGetAllSchedulesClientIsCalled() {
        //Given
        Schedule scheduleGiven = applicationDataProvider.getSchedule();

        //When
        when(scheduleService.getAllSchedules()).thenReturn(Collections.singletonList(scheduleGiven));
        List<Schedule> scheduleList = scheduleService.getAllSchedules();

        //Then
        verify(scheduleClient, Mockito.times(1)).getAllSchedules();
    }
}
