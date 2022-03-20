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
        Assertions.assertEquals(employeeGivenList.get(0).getId(), employeeDTOResultList.get(0).getId());
        Assertions.assertEquals(employeeGivenList.get(0).getRole(), employeeDTOResultList.get(0).getRole());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getId(), employeeResultRestaurantList.get(0).getId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getCoordinate().getLatitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getCoordinate().getLongitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getName(), employeeResultRestaurantList.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getHereId(), employeeResultRestaurantList.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getSchedule().getScheduleRoutine().size(),
                employeeResultRestaurantList.get(0).getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());

    }


    @Test
    public void passingAnEmployeeListNull_checkThatEmployeeDTOListIsNull() {
        Assertions.assertNull(employeeMapper.mapEmployeeListToEmployeeDTOList(null));
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
        Assertions.assertEquals(employeeGiven.getId(), employeeDTOResult.getId());
        Assertions.assertEquals(employeeGiven.getRole(), employeeDTOResult.getRole());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getId(), employeeResultRestaurant.get(0).getId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurant.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurant.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurant.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurant.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getCoordinate().getLatitude()
                , employeeResultRestaurant.get(0).getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getCoordinate().getLongitude()
                , employeeResultRestaurant.get(0).getLocation().getLocationCoordinate().getLongitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getCapacity(),
                employeeResultRestaurant.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getName(), employeeResultRestaurant.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getHereId(), employeeResultRestaurant.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getSchedule().getScheduleRoutine().size(),
                employeeResultRestaurant.get(0).getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getMenu().getLanguage(),
                employeeResultRestaurant.get(0).getMenu().getLanguage());
    }

    @Test
    public void passingAnEmployeeNull_checkThatEmployeeDTOIsNull() {
        Assertions.assertNull(employeeMapper.mapEmployeeToEmployeeDTO(null));
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
        Assertions.assertEquals(employeeDTOGiven.getId(), employeeResult.getId());
        Assertions.assertEquals(employeeDTOGiven.getRole(), employeeResult.getRole());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getId(), employeeResultRestaurant.get(0).getId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurant.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurant.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurant.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurant.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude()
                , employeeResultRestaurant.get(0).getLocation().getCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude()
                , employeeResultRestaurant.get(0).getLocation().getCoordinate().getLongitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getCapacity(),
                employeeResultRestaurant.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getName(), employeeResultRestaurant.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getHereId(), employeeResultRestaurant.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getSchedule().getDaysScheduleMap().size(),
                employeeResultRestaurant.get(0).getSchedule().getScheduleRoutine().size());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getMenu().getLanguage(),
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
        Assertions.assertEquals(employeeGivenListDTO.get(0).getId(), employeeResultList.get(0).getId());
        Assertions.assertEquals(employeeGivenListDTO.get(0).getRole(), employeeResultList.get(0).getRole());
        Assertions.assertEquals(employeeGivenRestaurantListDTO.get(0).getId(), employeeResultRestaurantList.get(0).getId());
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
    public void passingAnEmployeeListDTONull_checkThatEmployeeListIsNull() {
        Assertions.assertNull(employeeMapper.mapEmployeeDTOListToEmployeeList(null));
    }
}
