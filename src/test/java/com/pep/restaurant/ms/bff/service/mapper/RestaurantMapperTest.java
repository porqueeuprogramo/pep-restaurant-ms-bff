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
                restaurantDTOResultList.get(0).getId().longValue());
        Assertions.assertEquals(restaurantGivenList.get(0).getMenu().getId(),
                restaurantDTOResultList.get(0).getMenu().getId().longValue());
        Assertions.assertEquals(restaurantGivenList.get(0).getMenu().getLanguage(),
                restaurantDTOResultList.get(0).getMenu().getLanguage());
        Assertions.assertEquals(restaurantGivenList.get(0).getLocation(), restaurantDTOResultList.get(0).getLocation());
        Assertions.assertEquals(restaurantGivenList.get(0).getCapacity(), restaurantDTOResultList.get(0).getCapacity().intValue());
        Assertions.assertEquals(restaurantGivenEmployeeList.get(0).getId(), restaurantResultEmployeeList.get(0).getId().longValue());
        Assertions.assertEquals(restaurantGivenEmployeeList.get(0).getRole(),
                restaurantResultEmployeeList.get(0).getRole());
        Assertions.assertEquals(restaurantGivenEmployeeList.get(0).getSchedule().getId(),
                restaurantResultEmployeeList.get(0).getSchedule().getId().longValue());
        Assertions.assertEquals(restaurantGivenEmployeeList.get(0).getSchedule().getType(),
                restaurantResultEmployeeList.get(0).getSchedule().getType());
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
        Assertions.assertEquals(restaurantGiven.getId(), restaurantDTOResult.getId().longValue());
        Assertions.assertEquals(restaurantGiven.getMenu().getId(), restaurantDTOResult.getMenu().getId().longValue());
        Assertions.assertEquals(restaurantGiven.getMenu().getLanguage(), restaurantDTOResult.getMenu().getLanguage());
        Assertions.assertEquals(restaurantGiven.getLocation(), restaurantDTOResult.getLocation());
        Assertions.assertEquals(restaurantGiven.getCapacity(), restaurantDTOResult.getCapacity().intValue());
        Assertions.assertEquals(restaurantGivenEmployee.get(0).getId(), restaurantResultEmployee.get(0).getId().longValue());
        Assertions.assertEquals(restaurantGivenEmployee.get(0).getRole(), restaurantResultEmployee.get(0).getRole());
        Assertions.assertEquals(restaurantGivenEmployee.get(0).getSchedule().getId(),
                restaurantResultEmployee.get(0).getSchedule().getId().longValue());
        Assertions.assertEquals(restaurantGivenEmployee.get(0).getSchedule().getType(),
                restaurantResultEmployee.get(0).getSchedule().getType());
    }

    @Test
    public void passingAnRestaurantNull_checkThatRestaurantDTOIsNull() {
        Assertions.assertNull(restaurantMapper.mapRestaurantToRestaurantDTO(null));
    }

    @Test
    public void passingARestaurantDTO_checkThatRestaurantIsEquals() {
        //Given
        RestaurantDTO restaurantGiven = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployee = new ArrayList<>(restaurantGiven.getEmployeeList());

        //When
        Restaurant restaurantDTOResult = restaurantMapper.mapRestaurantDTOToRestaurant(restaurantGiven);
        List<Employee> restaurantResultEmployee = new ArrayList<>(restaurantDTOResult.getEmployeeList());

        //Then
        Assertions.assertEquals(restaurantGiven.getId().longValue(), restaurantDTOResult.getId());
        Assertions.assertEquals(restaurantGiven.getMenu().getId().longValue(), restaurantDTOResult.getMenu().getId());
        Assertions.assertEquals(restaurantGiven.getMenu().getLanguage(), restaurantDTOResult.getMenu().getLanguage());
        Assertions.assertEquals(restaurantGiven.getLocation(), restaurantDTOResult.getLocation());
        Assertions.assertEquals(restaurantGiven.getCapacity().intValue(), restaurantDTOResult.getCapacity());
        Assertions.assertEquals(restaurantGivenEmployee.get(0).getId().longValue(), restaurantResultEmployee.get(0).getId());
        Assertions.assertEquals(restaurantGivenEmployee.get(0).getRole(), restaurantResultEmployee.get(0).getRole());
        Assertions.assertEquals(restaurantGivenEmployee.get(0).getSchedule().getId().longValue(),
                restaurantResultEmployee.get(0).getSchedule().getId());
        Assertions.assertEquals(restaurantGivenEmployee.get(0).getSchedule().getType(),
                restaurantResultEmployee.get(0).getSchedule().getType());
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
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getId().longValue(),
                restaurantResultList.get(0).getId());
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getMenu().getId().longValue(),
                restaurantResultList.get(0).getMenu().getId());
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getMenu().getLanguage(),
                restaurantResultList.get(0).getMenu().getLanguage());
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getLocation(),
                restaurantResultList.get(0).getLocation());
        Assertions.assertEquals(restaurantGivenListDTO.get(0).getCapacity().intValue(),
                restaurantResultList.get(0).getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getId().longValue(),
                restaurantResultEmployeeList.get(0).getId());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getRole(),
                restaurantResultEmployeeList.get(0).getRole());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getSchedule().getId().longValue(),
                restaurantResultEmployeeList.get(0).getSchedule().getId());
        Assertions.assertEquals(restaurantGivenEmployeeListDTO.get(0).getSchedule().getType(),
                restaurantResultEmployeeList.get(0).getSchedule().getType());
    }

    @Test
    public void passingAnRestaurantDTOListNull_checkThatRestaurantListIsNull() {
        Assertions.assertNull(restaurantMapper.mapRestaurantDTOListToRestaurantList(null));
    }
}
