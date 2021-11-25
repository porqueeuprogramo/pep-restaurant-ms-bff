package com.pep.restaurant.ms.bff.service.mapper;

import com.pep.restaurant.ms.bff.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.domain.Employee;
import com.pep.restaurant.ms.bff.domain.Restaurant;
import com.pep.restaurant.ms.bff.web.api.model.EmployeeDTO;
import com.pep.restaurant.ms.bff.web.api.model.RestaurantDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeMapperTest {

    @InjectMocks
    EmployeeMapper employeeMapper;

    ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void passingAnEmployeeList_checkThatEmployeeDTOListIsEquals() {
        //Given
        List<Employee> employeeGivenList = Collections.singletonList(applicationDataProvider.getEmployee());
        List<Restaurant> employeeGivenRestaurantList = new ArrayList<>(employeeGivenList.get(0).getRestaurantList());

        //When
        List<EmployeeDTO> employeeDTOResultList= employeeMapper
                .mapEmployeeListToEmployeeDTOList(employeeGivenList);
        List<RestaurantDTO> employeeResultRestaurantList =
                new ArrayList<>(employeeDTOResultList.get(0).getRestaurantList());

        //Then
        Assert.assertEquals(employeeGivenList.get(0).getId(), employeeDTOResultList.get(0).getId().longValue());
        Assert.assertEquals(employeeGivenList.get(0).getRole(), employeeDTOResultList.get(0).getRole());
        Assert.assertEquals(employeeGivenList.get(0).getSchedule().getId(), employeeDTOResultList.get(0).getId().longValue());
        Assert.assertEquals(employeeGivenList.get(0).getSchedule().getType(),
                employeeDTOResultList.get(0).getSchedule().getType());
        Assert.assertEquals(employeeGivenRestaurantList.get(0).getId(), employeeResultRestaurantList.get(0).getId().longValue());
        Assert.assertEquals(employeeGivenRestaurantList.get(0).getLocation(),
                employeeResultRestaurantList.get(0).getLocation());
        Assert.assertEquals(employeeGivenRestaurantList.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity().intValue());
        Assert.assertEquals(employeeGivenRestaurantList.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());

    }


    @Test
    public void passingAnEmployeeListNull_checkThatEmployeeDTOListIsNull() {
        Assert.assertNull(employeeMapper.mapEmployeeListToEmployeeDTOList(null));
    }


    @Test
    public void passingAnEmployee_checkThatEmployeeDTOIsEquals() {
        //Given
        Employee employeeGiven = applicationDataProvider.getEmployee();
        List<Restaurant> employeeGivenRestaurantList = new ArrayList<>(employeeGiven.getRestaurantList());

        //When
        EmployeeDTO employeeDTOResult = employeeMapper.mapEmployeeToEmployeeDTO(employeeGiven);
        List<RestaurantDTO> employeeResultRestaurant = new ArrayList<>(employeeDTOResult.getRestaurantList());

        //Then
        Assert.assertEquals(employeeGiven.getId(), employeeDTOResult.getId().longValue());
        Assert.assertEquals(employeeGiven.getRole(), employeeDTOResult.getRole());
        Assert.assertEquals(employeeGiven.getSchedule().getId(), employeeDTOResult.getId().longValue());
        Assert.assertEquals(employeeGiven.getSchedule().getType(), employeeDTOResult.getSchedule().getType());
        Assert.assertEquals(employeeGivenRestaurantList.get(0).getId(), employeeResultRestaurant.get(0).getId().longValue());
        Assert.assertEquals(employeeGivenRestaurantList.get(0).getLocation(),
                employeeResultRestaurant.get(0).getLocation());
        Assert.assertEquals(employeeGivenRestaurantList.get(0).getCapacity(),
                employeeResultRestaurant.get(0).getCapacity().intValue());
        Assert.assertEquals(employeeGivenRestaurantList.get(0).getMenu().getLanguage(),
                employeeResultRestaurant.get(0).getMenu().getLanguage());
    }

    @Test
    public void passingAnEmployeeNull_checkThatEmployeeDTOIsNull() {
        Assert.assertNull(employeeMapper.mapEmployeeToEmployeeDTO(null));
    }

    @Test
    public void passingAnEmployeeDTO_checkThatEmployeeIsEquals() {
        //Given
        EmployeeDTO employeeDTOGiven = applicationDataProvider.getEmployeeDTO();
        List<RestaurantDTO> employeeGivenRestaurantList = new ArrayList<>(employeeDTOGiven.getRestaurantList());

        //When
        Employee employeeResult = employeeMapper.mapEmployeeDTOToEmployee(employeeDTOGiven);
        List<Restaurant> employeeResultRestaurant = new ArrayList<>(employeeResult.getRestaurantList());

        //Then
        Assert.assertEquals(employeeDTOGiven.getId().longValue(), employeeResult.getId());
        Assert.assertEquals(employeeDTOGiven.getRole(), employeeResult.getRole());
        Assert.assertEquals(employeeDTOGiven.getSchedule().getId().longValue(), employeeResult.getId());
        Assert.assertEquals(employeeDTOGiven.getSchedule().getType(), employeeResult.getSchedule().getType());
        Assert.assertEquals(employeeGivenRestaurantList.get(0).getLocation(),
                employeeResultRestaurant.get(0).getLocation());
        Assert.assertEquals(employeeGivenRestaurantList.get(0).getCapacity().intValue(),
                employeeResultRestaurant.get(0).getCapacity());
        Assert.assertEquals(employeeGivenRestaurantList.get(0).getMenu().getLanguage(),
                employeeResultRestaurant.get(0).getMenu().getLanguage());
    }

    @Test
    public void passingAnEmployeeListDTO_checkThatEmployeeListIsEquals() {
        //Given
        List<EmployeeDTO> employeeGivenListDTO = Collections.singletonList(applicationDataProvider.getEmployeeDTO());
        List<RestaurantDTO> employeeGivenRestaurantListDTO = new ArrayList<>(employeeGivenListDTO.get(0).getRestaurantList());

        //When
        List<Employee> employeeResultList = employeeMapper
                .mapEmployeeDTOListToEmployeeList(employeeGivenListDTO);
        List<Restaurant> employeeResultRestaurantList =
                new ArrayList<>(employeeResultList.get(0).getRestaurantList());

        //Then
        Assert.assertEquals(employeeGivenListDTO.get(0).getId().longValue(), employeeResultList.get(0).getId());
        Assert.assertEquals(employeeGivenListDTO.get(0).getRole(), employeeResultList.get(0).getRole());
        Assert.assertEquals(employeeGivenListDTO.get(0).getSchedule().getId().longValue(), employeeResultList.get(0).getId());
        Assert.assertEquals(employeeGivenListDTO.get(0).getSchedule().getType(),
                employeeResultList.get(0).getSchedule().getType());
        Assert.assertEquals(employeeGivenListDTO.get(0).getId().longValue(), employeeResultList.get(0).getId());
        Assert.assertEquals(employeeGivenRestaurantListDTO.get(0).getLocation(),
                employeeResultRestaurantList.get(0).getLocation());
        Assert.assertEquals(employeeGivenRestaurantListDTO.get(0).getCapacity().intValue(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assert.assertEquals(employeeGivenRestaurantListDTO.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());

    }


    @Test
    public void passingAnEmployeeListDTONull_checkThatEmployeeListIsNull() {
        Assert.assertNull(employeeMapper.mapEmployeeDTOListToEmployeeList(null));
    }
}
