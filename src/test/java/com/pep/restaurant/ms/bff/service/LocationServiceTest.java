package com.pep.restaurant.ms.bff.service;

import com.pep.restaurant.ms.bff.client.LocationClient;
import com.pep.restaurant.ms.bff.domain.Location;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class LocationServiceTest {

    @InjectMocks
    private LocationService locationService;

    @Mock
    private LocationClient locationClient;

    private ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void passingLocationId_checkIfGetLocationLocationClientIsCalled() {

        //Given
        Location locationGiven = applicationDataProvider.getLocation();

        //When
        when(locationClient.getLocation(anyLong())).thenReturn(locationGiven);
        Location location = locationService.getLocation(anyLong());

        //Then
        verify(locationClient, Mockito.times(1)).getLocation(anyLong());
    }

    @Test
    public void passingALocation_checkIfCreateLocationLocationClientIsCalled() {
        //Given
        Location locationGiven = applicationDataProvider.getLocation();

        //When
        when(locationClient.createLocation(locationGiven)).thenReturn(locationGiven);
        Location location = locationService.createLocation(locationGiven);

        //Then
        verify(locationClient, Mockito.times(1)).createLocation(locationGiven);
    }

    @Test
    public void passingALocationIdAndALocation_checkIfEditLocationLocationClientIsCalled() {
        //Given
        Location locationGiven = applicationDataProvider.getLocation();

        //When
        when(locationClient.editLocation(1L, locationGiven)).thenReturn(locationGiven);
        Location location = locationService.editLocation(1L, locationGiven);

        //Then
        verify(locationClient, Mockito.times(1))
                .editLocation(1L, locationGiven);
    }

    @Test
    public void passingLocationId_checkIfDeleteLocationLocationClientIsCalled() {
        //Given
        Location locationGiven = applicationDataProvider.getLocation();

        //When
        when(locationClient.deleteLocation(1L)).thenReturn(locationGiven);
        Location location = locationService.deleteLocation(1L);

        //Then
        verify(locationClient, Mockito.times(1))
                .deleteLocation(1L);
    }

    @Test
    public void checkIfGetAllLocationsLocationClientIsCalled() {

        //Given
        Location locationGiven = applicationDataProvider.getLocation();

        //When
        when(locationClient.getAllLocations()).thenReturn(Collections.singletonList(locationGiven));
        List<Location> location = locationService.getAllLocations();

        //Then
        verify(locationClient, Mockito.times(1))
                .getAllLocations();
    }
}
