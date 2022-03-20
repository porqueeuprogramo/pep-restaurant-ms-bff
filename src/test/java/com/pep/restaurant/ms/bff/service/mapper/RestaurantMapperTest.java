package com.pep.restaurant.ms.bff.service.mapper;

import com.pep.restaurant.ms.bff.domain.Employee;
import com.pep.restaurant.ms.bff.domain.Restaurant;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.web.api.model.EmployeeDTO;
import com.pep.restaurant.ms.bff.web.api.model.RestaurantDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class RestaurantMapperTest {

    @InjectMocks
    RestaurantMapper restaurantMapper;

    ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();


    @Test
    public void passingARestaurantList_checkThatRestaurantDTOListIsEquals() {
        //Given
        List<Restaurant> restaurantGivenList = Collections.singletonList(applicationDataProvider
                .getRestaurantWithEmployeeList());
        List<Employee> restaurantGivenEmployeeList =
                new ArrayList<>(restaurantGivenList.get(0).getEmployeeList());

        //When
        List<RestaurantDTO> restaurantDTOResultList = restaurantMapper
                .mapRestaurantListToRestaurantDTOList(restaurantGivenList);
        List<EmployeeDTO> restaurantResultEmployeeList =
                new ArrayList<>(restaurantDTOResultList.get(0).getEmployeeList());

        //Then
        Assertions.assertEquals(restaurantGivenList.get(0).getId(),
                restaurantDTOResultList.get(0).getId());
        Assertions.assertEquals(restaurantGivenList.get(0).getMenu().getId(),
                restaurantDTOResultList.get(0).getMenu().getId());
        Assertions.assertEquals(restaurantGivenList.get(0).getMenu().getLanguage(),
                restaurantDTOResultList.get(0).getMenu().getLanguage());
        Assertions.assertEquals(restaurantGivenList.get(0).getLocation().getAddress().getName()
                , restaurantDTOResultList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(restaurantGivenList.get(0).getLocation().getAddress().getCountry()
                , restaurantDTOResultList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurantGivenList.get(0).getLocation().getAddress().getCity()
                , restaurantDTOResultList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurantGivenList.get(0).getLocation().getAddress().getPostalCode()
                , restaurantDTOResultList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurantGivenList.get(0).getLocation().getCoordinate().getLatitude()
                , restaurantDTOResultList.get(0).getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(restaurantGivenList.get(0).getLocation().getCoordinate().getLongitude()
                , restaurantDTOResultList.get(0).getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(restaurantGivenList.get(0).getName(), restaurantDTOResultList.get(0).getName());
        Assertions.assertEquals(restaurantGivenList.get(0).getHereId(), restaurantDTOResultList.get(0).getHereId());
        Assertions.assertEquals(restaurantGivenList.get(0).getSchedule().getScheduleRoutine().size(),
                restaurantDTOResultList.get(0).getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(restaurantGivenList.get(0).getCapacity(), restaurantDTOResultList.get(0).getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeList.get(0).getId(), restaurantResultEmployeeList.get(0).getId());
        Assertions.assertEquals(restaurantGivenEmployeeList.get(0).getRole(),
                restaurantResultEmployeeList.get(0).getRole());

    }

    @Test
    public void passingAnRestaurantListNull_checkThatRestaurantDTOListIsNull() {
        Assertions.assertNull(restaurantMapper.mapRestaurantListToRestaurantDTOList(null));
    }

    @Test
    public void passingARestaurant_checkThatRestaurantDTOIsEquals() {
        //Given
        Restaurant restaurantGiven = applicationDataProvider.getRestaurantWithEmployeeList();
        List<Employee> restaurantGivenEmployee = new ArrayList<>(restaurantGiven.getEmployeeList());

        //When
        RestaurantDTO restaurantDTOResult = restaurantMapper.mapRestaurantToRestaurantDTO(restaurantGiven);
        List<EmployeeDTO> restaurantResultEmployee = new ArrayList<>(restaurantDTOResult.getEmployeeList());

        //Then
        Assertions.assertEquals(restaurantGiven.getId(),
                restaurantDTOResult.getId());
        Assertions.assertEquals(restaurantGiven.getMenu().getId(),
                restaurantDTOResult.getMenu().getId());
        Assertions.assertEquals(restaurantGiven.getMenu().getLanguage(),
                restaurantDTOResult.getMenu().getLanguage());
        Assertions.assertEquals(restaurantGiven.getLocation().getAddress().getName()
                , restaurantDTOResult.getLocation().getAddress().getName());
        Assertions.assertEquals(restaurantGiven.getLocation().getAddress().getCountry()
                , restaurantDTOResult.getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurantGiven.getLocation().getAddress().getCity()
                , restaurantDTOResult.getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurantGiven.getLocation().getAddress().getPostalCode()
                , restaurantDTOResult.getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurantGiven.getLocation().getCoordinate().getLatitude()
                , restaurantDTOResult.getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(restaurantGiven.getLocation().getCoordinate().getLongitude()
                , restaurantDTOResult.getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(restaurantGiven.getName(), restaurantDTOResult.getName());
        Assertions.assertEquals(restaurantGiven.getHereId(), restaurantDTOResult.getHereId());
        Assertions.assertEquals(restaurantGiven.getSchedule().getScheduleRoutine().size(),
                restaurantDTOResult.getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(restaurantGiven.getCapacity(), restaurantDTOResult.getCapacity());
        Assertions.assertEquals(restaurantGivenEmployee.get(0).getId(), restaurantResultEmployee.get(0).getId());
        Assertions.assertEquals(restaurantGivenEmployee.get(0).getRole(),
                restaurantGivenEmployee.get(0).getRole());
    }

    @Test
    public void passingAnRestaurantNull_checkThatRestaurantDTOIsNull() {
        Assertions.assertNull(restaurantMapper.mapRestaurantToRestaurantDTO(null));
    }

    @Test
    public void passingARestaurantDTO_checkThatRestaurantIsEquals() {
        //Given
        RestaurantDTO restaurantGivenDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployeeDTO = new ArrayList<>(restaurantGivenDTO.getEmployeeList());

        //When
        Restaurant restaurantResult = restaurantMapper.mapRestaurantDTOToRestaurant(restaurantGivenDTO);
        List<Employee> restaurantResultEmployee = new ArrayList<>(restaurantResult.getEmployeeList());

        //Then
        Assertions.assertEquals(restaurantGivenDTO.getId(),
                restaurantResult.getId());
        Assertions.assertEquals(restaurantGivenDTO.getMenu().getId(),
                restaurantResult.getMenu().getId());
        Assertions.assertEquals(restaurantGivenDTO.getMenu().getLanguage(),
                restaurantResult.getMenu().getLanguage());
        Assertions.assertEquals(restaurantGivenDTO.getLocation().getAddress().getName()
                , restaurantResult.getLocation().getAddress().getName());
        Assertions.assertEquals(restaurantGivenDTO.getLocation().getAddress().getCountry()
                , restaurantResult.getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurantGivenDTO.getLocation().getAddress().getCity()
                , restaurantResult.getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurantGivenDTO.getLocation().getAddress().getPostalCode()
                , restaurantResult.getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurantGivenDTO.getLocation().getLocationCoordinate().getLatitude()
                , restaurantResult.getLocation().getCoordinate().getLatitude(), 0);
        Assertions.assertEquals(restaurantGivenDTO.getLocation().getLocationCoordinate().getLongitude()
                , restaurantResult.getLocation().getCoordinate().getLongitude(),0);
        Assertions.assertEquals(restaurantGivenDTO.getName(), restaurantResult.getName());
        Assertions.assertEquals(restaurantGivenDTO.getHereId(), restaurantResult.getHereId());
        Assertions.assertEquals(restaurantGivenDTO.getSchedule().getDaysScheduleMap().size(),
                restaurantResult.getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(restaurantGivenDTO.getCapacity(), restaurantResult.getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getId(), restaurantResultEmployee.get(0).getId());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getRole(),
                restaurantResultEmployee.get(0).getRole());
    }

    @Test
    public void passingAnRestaurantDTONull_checkThatRestaurantIsNull() {
        Assertions.assertNull(restaurantMapper.mapRestaurantDTOToRestaurant(null));
    }

    @Test
    public void passingARestaurantDTOList_checkThatRestaurantListIsEquals() {
        //Given
        List<RestaurantDTO> restaurantGivenListDTO = Collections.singletonList(applicationDataProvider
                .getRestaurantDTOWithEmployeeListDTO());
        List<EmployeeDTO> restaurantGivenEmployeeListDTO =
                new ArrayList<>(restaurantGivenListDTO.get(0).getEmployeeList());

        //When
        List<Restaurant> restaurantResultList = restaurantMapper
                .mapRestaurantDTOListToRestaurantList(restaurantGivenListDTO);
        List<Employee> restaurantResultEmployeeList =
                new ArrayList<>(restaurantResultList.get(0).getEmployeeList());

        //Then
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getId(),
                restaurantResultList.get(0).getId());
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getMenu().getId(),
                restaurantResultList.get(0).getMenu().getId());
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getMenu().getLanguage(),
                restaurantResultList.get(0).getMenu().getLanguage());
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getLocation().getAddress().getName()
                , restaurantResultList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getLocation().getAddress().getCountry()
                , restaurantResultList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getLocation().getAddress().getCity()
                , restaurantResultList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getLocation().getAddress().getPostalCode()
                , restaurantResultList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getLocation().getLocationCoordinate().getLatitude()
                , restaurantResultList.get(0).getLocation().getCoordinate().getLatitude(), 0);
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getLocation().getLocationCoordinate().getLongitude()
                , restaurantResultList.get(0).getLocation().getCoordinate().getLongitude(),0);
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getName(), restaurantResultList.get(0).getName());
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getHereId(), restaurantResultList.get(0).getHereId());
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getSchedule().getDaysScheduleMap().size(),
                restaurantResultList.get(0).getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getCapacity(), restaurantResultList.get(0).getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getId(), restaurantResultEmployeeList.get(0).getId());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getRole(),
                restaurantResultEmployeeList.get(0).getRole());
    }

    @Test
    public void passingAnRestaurantDTOListNull_checkThatRestaurantListIsNull() {
        Assertions.assertNull(restaurantMapper.mapRestaurantDTOListToRestaurantList(null));
    }
}
