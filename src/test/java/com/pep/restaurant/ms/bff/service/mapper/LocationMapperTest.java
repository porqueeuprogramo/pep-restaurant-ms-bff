package com.pep.restaurant.ms.bff.service.mapper;

import com.pep.restaurant.ms.bff.domain.Location;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.web.api.model.LocationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class LocationMapperTest {

    @InjectMocks
    LocationMapper locationMapper;

    ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void passingALocationList_checkThatLocationDTOListIsEquals() {
        //Given
        List<Location> locationGivenList = Collections.singletonList(applicationDataProvider.getLocation());

        //When
        List<LocationDTO> locationDTOResultList = locationMapper.mapLocationListToLocationDTOList(locationGivenList);

        //Then
        Assertions.assertEquals(locationGivenList.get(0).getCoordinate().getLatitude(),
                locationDTOResultList.get(0).getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(locationGivenList.get(0).getCoordinate().getLongitude(),
                locationDTOResultList.get(0).getLocationCoordinate().getLongitude(), 0);
        Assertions.assertEquals(locationGivenList.get(0).getAddress().getName(),
                locationDTOResultList.get(0).getAddress().getName());
        Assertions.assertEquals(locationGivenList.get(0).getAddress().getCountry(),
                locationDTOResultList.get(0).getAddress().getCountry());
        Assertions.assertEquals(locationGivenList.get(0).getAddress().getPostalCode(),
                locationDTOResultList.get(0).getAddress().getPostalCode());
        Assertions.assertEquals(locationGivenList.get(0).getAddress().getCity(),
                locationDTOResultList.get(0).getAddress().getCity());

    }

    @Test
    public void passingAnLocationListNull_checkThatLocationDTOListIsNull() {
        Assertions.assertNull(locationMapper.mapLocationListToLocationDTOList(null));
    }

    @Test
    public void passingALocation_checkThatLocationDTOIsEquals() {
        //Given
        Location locationGiven = applicationDataProvider.getLocation();

        //When
        LocationDTO locationDTOResult = locationMapper.mapLocationToLocationDTO(locationGiven);

        //Then
        Assertions.assertEquals(locationGiven.getCoordinate().getLatitude(),
                locationDTOResult.getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(locationGiven.getCoordinate().getLongitude(),
                locationDTOResult.getLocationCoordinate().getLongitude(), 0);
        Assertions.assertEquals(locationGiven.getAddress().getName(),
                locationDTOResult.getAddress().getName());
        Assertions.assertEquals(locationGiven.getAddress().getCountry(),
                locationDTOResult.getAddress().getCountry());
        Assertions.assertEquals(locationGiven.getAddress().getPostalCode(),
                locationDTOResult.getAddress().getPostalCode());
        Assertions.assertEquals(locationGiven.getAddress().getCity(),
                locationDTOResult.getAddress().getCity());
    }

    @Test
    public void passingAnLocationNull_checkThatLocationDTOIsNull() {
        Assertions.assertNull(locationMapper.mapLocationToLocationDTO(null));
    }

    @Test
    public void passingALocationDTO_checkThatLocationIsEquals() {
        //Given
        LocationDTO locationDTOGiven = applicationDataProvider.getLocationDTO();

        //When
        Location locationResult = locationMapper.mapLocationDTOToLocation(locationDTOGiven);

        //Then
        Assertions.assertEquals(locationDTOGiven.getLocationCoordinate().getLatitude(),
                locationResult.getCoordinate().getLatitude(), 0);
        Assertions.assertEquals(locationDTOGiven.getLocationCoordinate().getLongitude(),
                locationResult.getCoordinate().getLongitude(), 0);
        Assertions.assertEquals(locationDTOGiven.getAddress().getName(),
                locationResult.getAddress().getName());
        Assertions.assertEquals(locationDTOGiven.getAddress().getCountry(),
                locationResult.getAddress().getCountry());
        Assertions.assertEquals(locationDTOGiven.getAddress().getPostalCode(),
                locationResult.getAddress().getPostalCode());
        Assertions.assertEquals(locationDTOGiven.getAddress().getCity(),
                locationResult.getAddress().getCity());
    }

    @Test
    public void passingAnLocationDTONull_checkThatLocationIsNull() {
        Assertions.assertNull(locationMapper.mapLocationDTOToLocation(null));
    }

    @Test
    public void passingALocationDTOList_checkThatLocationListIsEquals() {
        //Given
        List<LocationDTO> locationGivenListDTO = Collections.singletonList(applicationDataProvider.getLocationDTO());

        //When
        List<Location> locationResultList = locationMapper.mapLocationDTOListToLocationList(locationGivenListDTO);

        //Then
        Assertions.assertEquals(locationGivenListDTO.get(0).getLocationCoordinate().getLatitude(),
                locationResultList.get(0).getCoordinate().getLatitude(), 0);
        Assertions.assertEquals(locationGivenListDTO.get(0).getLocationCoordinate().getLongitude(),
                locationResultList.get(0).getCoordinate().getLongitude(), 0);
        Assertions.assertEquals(locationGivenListDTO.get(0).getAddress().getName(),
                locationResultList.get(0).getAddress().getName());
        Assertions.assertEquals(locationGivenListDTO.get(0).getAddress().getCountry(),
                locationResultList.get(0).getAddress().getCountry());
        Assertions.assertEquals(locationGivenListDTO.get(0).getAddress().getPostalCode(),
                locationResultList.get(0).getAddress().getPostalCode());
        Assertions.assertEquals(locationGivenListDTO.get(0).getAddress().getCity(),
                locationResultList.get(0).getAddress().getCity());

    }

    @Test
    public void passingAnLocationDTOListNull_checkThatLocationListIsNull() {
        Assertions.assertNull(locationMapper.mapLocationDTOListToLocationList(null));
    }

}
