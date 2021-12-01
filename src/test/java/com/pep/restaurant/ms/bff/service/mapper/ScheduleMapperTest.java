package com.pep.restaurant.ms.bff.service.mapper;

import com.pep.restaurant.ms.bff.domain.Restaurant;
import com.pep.restaurant.ms.bff.domain.Schedule;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.web.api.model.RestaurantDTO;
import com.pep.restaurant.ms.bff.web.api.model.ScheduleDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class ScheduleMapperTest {

    @InjectMocks
    ScheduleMapper scheduleMapper;

    ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void passingAScheduleList_checkThatScheduleDTOListIsEquals() {
        //Given
        List<Schedule> scheduleGivenList = Collections.singletonList(applicationDataProvider
                .getScheduleWithEmployeeList());
        List<Restaurant> scheduleGivenEmployeeRestaurantList = new ArrayList<>(scheduleGivenList.get(0)
                .getEmployeeList().get(0).getRestaurantList());

        //When
        List<ScheduleDTO> scheduleDTOResultList = scheduleMapper
                .mapScheduleListToScheduleDTOList(scheduleGivenList);
        List<RestaurantDTO> scheduleResultEmployeeRestaurantList = new ArrayList<>(scheduleDTOResultList
                .get(0).getEmployeeList().get(0).getRestaurantList());

        //Then
        Assertions.assertEquals(scheduleGivenList.get(0).getId(), scheduleDTOResultList.get(0).getId().longValue());
        Assertions.assertEquals(scheduleGivenList.get(0).getType(), scheduleDTOResultList.get(0).getType());
        Assertions.assertEquals(scheduleGivenList.get(0).getEmployeeList().get(0).getId(),
                scheduleDTOResultList.get(0).getEmployeeList().get(0).getId().longValue());
        Assertions.assertEquals(scheduleGivenList.get(0).getEmployeeList().get(0).getRole(),
                scheduleDTOResultList.get(0).getEmployeeList().get(0).getRole());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurantList.get(0).getId(),
                scheduleResultEmployeeRestaurantList.get(0).getId().longValue());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurantList.get(0).getLocation(),
                scheduleResultEmployeeRestaurantList.get(0).getLocation());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurantList.get(0).getCapacity(),
                scheduleResultEmployeeRestaurantList.get(0).getCapacity().intValue());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurantList.get(0).getMenu().getLanguage(),
                scheduleResultEmployeeRestaurantList.get(0).getMenu().getLanguage());
    }

    @Test
    public void passingAnScheduleListNull_checkThatScheduleDTOListIsNull() {
        Assertions.assertNull(scheduleMapper.mapScheduleListToScheduleDTOList(null));
    }


    @Test
    public void passingASchedule_checkThatScheduleDTOIsEquals() {
        //Given
        Schedule scheduleGiven = applicationDataProvider.getScheduleWithEmployeeList();
        List<Restaurant> scheduleGivenEmployeeRestaurant =
                new ArrayList<>(scheduleGiven.getEmployeeList().get(0).getRestaurantList());

        //When
        ScheduleDTO scheduleDTOResult = scheduleMapper.mapScheduleToScheduleDTO(scheduleGiven);
        List<RestaurantDTO> scheduleResultEmployeeRestaurant =
                new ArrayList<>(scheduleDTOResult.getEmployeeList().get(0).getRestaurantList());

        //Then
        Assertions.assertEquals(scheduleGiven.getId(), scheduleDTOResult.getId().longValue());
        Assertions.assertEquals(scheduleGiven.getType(), scheduleDTOResult.getType());
        Assertions.assertEquals(scheduleGiven.getEmployeeList().get(0).getId(),
                scheduleDTOResult.getEmployeeList().get(0).getId().longValue());
        Assertions.assertEquals(scheduleGiven.getEmployeeList().get(0).getRole(),
                scheduleDTOResult.getEmployeeList().get(0).getRole());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurant.get(0).getId(),
                scheduleResultEmployeeRestaurant.get(0).getId().longValue());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurant.get(0).getLocation(),
                scheduleResultEmployeeRestaurant.get(0).getLocation());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurant.get(0).getCapacity(),
                scheduleResultEmployeeRestaurant.get(0).getCapacity().intValue());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurant.get(0).getMenu().getLanguage(),
                scheduleResultEmployeeRestaurant.get(0).getMenu().getLanguage());

    }

    @Test
    public void passingAScheduleNull_checkThatScheduleDTOIsNull() {
        Assertions.assertNull(scheduleMapper.mapScheduleToScheduleDTO(null));
    }

    @Test
    public void passingAScheduleDTO_checkThatScheduleIsEquals() {
        //Given
        ScheduleDTO scheduleDTOGiven = applicationDataProvider.getScheduleDTOWithEmployeeDTOList();
        List<RestaurantDTO> scheduleGivenEmployeeRestaurant =
                new ArrayList<>(scheduleDTOGiven.getEmployeeList().get(0).getRestaurantList());

        //When
        Schedule scheduleResult = scheduleMapper.mapScheduleDTOToSchedule(scheduleDTOGiven);
        List<Restaurant> scheduleResultEmployeeRestaurant =
                new ArrayList<>(scheduleResult.getEmployeeList().get(0).getRestaurantList());

        //Then
        Assertions.assertEquals(scheduleDTOGiven.getId().longValue(), scheduleResult.getId());
        Assertions.assertEquals(scheduleDTOGiven.getType(), scheduleResult.getType());
        Assertions.assertEquals(scheduleDTOGiven.getEmployeeList().get(0).getId().longValue(),
                scheduleResult.getEmployeeList().get(0).getId());
        Assertions.assertEquals(scheduleDTOGiven.getEmployeeList().get(0).getRole(),
                scheduleResult.getEmployeeList().get(0).getRole());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurant.get(0).getId().longValue(),
                scheduleResultEmployeeRestaurant.get(0).getId());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurant.get(0).getLocation(),
                scheduleResultEmployeeRestaurant.get(0).getLocation());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurant.get(0).getCapacity().longValue(),
                scheduleResultEmployeeRestaurant.get(0).getCapacity());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurant.get(0).getMenu().getLanguage(),
                scheduleResultEmployeeRestaurant.get(0).getMenu().getLanguage());

    }

    @Test
    public void passingAScheduleDTONull_checkThatScheduleIsNull() {
        Assertions.assertNull(scheduleMapper.mapScheduleDTOToSchedule(null));
    }

    @Test
    public void passingAScheduleListDTO_checkThatScheduleListIsEquals() {
        //Given
        List<ScheduleDTO> scheduleGivenListDTO = Collections.singletonList(applicationDataProvider
                .getScheduleDTOWithEmployeeDTOList());
        List<RestaurantDTO> scheduleGivenEmployeeRestaurantListDTO = new ArrayList<>(scheduleGivenListDTO.get(0)
                .getEmployeeList().get(0).getRestaurantList());

        //When
        List<Schedule> scheduleResultList = scheduleMapper
                .mapScheduleDTOListToScheduleList(scheduleGivenListDTO);
        List<Restaurant> scheduleResultEmployeeRestaurantList = new ArrayList<>(scheduleResultList
                .get(0).getEmployeeList().get(0).getRestaurantList());

        //Then
        Assertions.assertEquals(scheduleGivenListDTO.get(0).getId().longValue(), scheduleResultList.get(0).getId());
        Assertions.assertEquals(scheduleGivenListDTO.get(0).getType(), scheduleResultList.get(0).getType());
        Assertions.assertEquals(scheduleGivenListDTO.get(0).getEmployeeList().get(0).getId().longValue(),
                scheduleResultList.get(0).getEmployeeList().get(0).getId());
        Assertions.assertEquals(scheduleGivenListDTO.get(0).getEmployeeList().get(0).getRole(),
                scheduleResultList.get(0).getEmployeeList().get(0).getRole());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurantListDTO.get(0).getId().longValue(),
                scheduleResultEmployeeRestaurantList.get(0).getId());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurantListDTO.get(0).getLocation(),
                scheduleResultEmployeeRestaurantList.get(0).getLocation());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurantListDTO.get(0).getCapacity().intValue(),
                scheduleResultEmployeeRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(scheduleGivenEmployeeRestaurantListDTO.get(0).getMenu().getLanguage(),
                scheduleResultEmployeeRestaurantList.get(0).getMenu().getLanguage());
    }

    @Test
    public void passingAnScheduleListDTONull_checkThatScheduleListIsNull() {
        Assertions.assertNull(scheduleMapper.mapScheduleDTOListToScheduleList(null));
    }

}
