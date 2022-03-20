package com.pep.restaurant.ms.bff.provider;

import com.pep.restaurant.ms.bff.domain.*;
import com.pep.restaurant.ms.bff.web.api.model.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class ApplicationDataProvider {

    public Restaurant getRestaurantWithEmployees(){
        return new Restaurant()
                .id(1L)
                .name("Francesinhas")
                .capacity(100)
                .menu(getMenu())
                .hereId("hereId")
                .location(getLocation())
                .schedule(getScheduleRoutine())
                .employeeList(Set.of(getEmployeeWithoutRestaurantListWithId()));
    }

    public Employee getEmployeeWithId(){
        return new Employee()
                .id(1L)
                .role("CHEF");
    }

    public Employee getEmployeeWithRestaurant(){
        Employee employee = new Employee()
                .id(1L)
                .role("CHEF");
        employee.setRestaurantList(Set.of(getRestaurantWithEmployees()));
        return employee;
    }

    public Restaurant getRestaurant() {
        return new Restaurant()
                .name("Francesinhas")
                .capacity(100)
                .menu(getMenu())
                .hereId("hereId")
                .location(getLocation())
                .schedule(getScheduleRoutine());
    }

    public Location getLocation(){
        return new Location()
                .coordinate(getCoordinate())
                .address(getAddress());
    }

    public Coordinate getCoordinate(){
        return new Coordinate()
                .latitude(12.0000)
                .longitude(13.0000);
    }

    public Address getAddress(){
        return new Address()
                .id(1L)
                .name("Rua Teste")
                .city("Porto")
                .country("Portugal")
                .postalCode("9999-999");
    }

    public ScheduleRoutine getScheduleRoutine(){

        ScheduleTime scheduleTime = new ScheduleTime()
                .startTime(LocalTime.of(12, 30))
                .endTime(LocalTime.of(20, 30));

        Map<DayOfWeek, List<ScheduleTime>> dayMap = new HashMap<>();
        dayMap.put(DayOfWeek.MONDAY, List.of(scheduleTime));

        return new ScheduleRoutine()
                .daysScheduleMap(dayMap);
    }

    public ScheduleRoutineDTO getScheduleRoutineDTO(){

        ScheduleTimeDTO scheduleTimeDTO = new ScheduleTimeDTO()
                .startTime(LocalTime.of(12, 30))
                .endTime(LocalTime.of(20, 30));

        Map<String, List<ScheduleTimeDTO>> dayMap = new HashMap<>();
        dayMap.put("MONDAY", List.of(scheduleTimeDTO));

        return new ScheduleRoutineDTO()
                .daysScheduleMap(dayMap);
    }

    public Menu getMenu(){
        return new Menu()
                .language("PORTUGUESE");
    }

    public Employee getEmployee(){
        Employee employee = new Employee()
                .id(0L)
                .role("CHEF");
        employee.setRestaurantList(Set.of(getRestaurant()));
        return employee;
    }

    public Employee getEmployeeWithIdOne(){
        Employee employee = new Employee()
                .id(1L)
                .role("CHEF");
        employee.setRestaurantList(Set.of(getRestaurant()));
        return employee;
    }

    public RestaurantDTO getRestaurantDTO(){
        return new RestaurantDTO()
                .id(1L)
                .name("Francesinhas")
                .capacity(100)
                .menu(getMenuDTO())
                .hereId("hereId")
                .location(getLocationDTO())
                .schedule(getScheduleRoutineDTO());
    }

    public LocationDTO getLocationDTO(){
        return new LocationDTO()
                .id(1L)
                .locationCoordinate(getCoordinateDTO())
                .address(getAddressDTO());
    }

    public AddressDTO getAddressDTO(){
        return new AddressDTO()
                .id(1L)
                .name("Rua Teste")
                .city("Porto")
                .country("Portugal")
                .postalCode("9999-999");
    }

    public CoordinateDTO getCoordinateDTO(){
        return new CoordinateDTO()
                .latitude(12.0000)
                .longitude(13.0000);
    }

    public MenuDTO getMenuDTO(){
        return new MenuDTO()
                .id(1L)
                .language("PORTUGUESE");
    }

    public EmployeeDTO getEmployeeDTO(){
        EmployeeDTO employee = new EmployeeDTO()
                .id(1L)
                .role("CHEF");
        employee.setRestaurantList(Set.of(getRestaurantDTO()));
        return employee;
    }

    public EmployeeDTO getEmployeeDTOWithRestaurantIdZero(){
        EmployeeDTO employee = new EmployeeDTO()
                .id(1L)
                .role("CHEF");
        employee.setRestaurantList(Set.of(getRestaurantDTOWithRestaurantIdZero()));
        return employee;
    }

    public RestaurantDTO getRestaurantDTOWithRestaurantIdZero(){
        return new RestaurantDTO()
                .id(0L)
                .name("Francesinhas")
                .capacity(100)
                .menu(getMenuDTO())
                .hereId("hereId")
                .location(getLocationDTO())
                .schedule(getScheduleRoutineDTO());
    }

    public Restaurant getRestaurantWithEmployeeList(){
        return new Restaurant()
                .name("Francesinhas")
                .capacity(100)
                .menu(getMenu())
                .hereId("hereId")
                .location(getLocation())
                .schedule(getScheduleRoutine())
                .employeeList(Set.of(getEmployeeWithoutRestaurantList()));
    }

    public Restaurant getRestaurantWithEmployeeListOnlyWithRole(){
        return new Restaurant()
                .name("Francesinhas")
                .capacity(100)
                .menu(getMenu())
                .hereId("hereId")
                .location(getLocation())
                .schedule(getScheduleRoutine())
                .employeeList(Set.of(getEmployeeWithoutRestaurantListWithId()));
    }

    public Employee getEmployeeWithoutRestaurantList(){
        return new Employee()
                .id(0L)
                .role("CHEF");
    }

    public Employee getEmployeeWithoutRestaurantListWithId(){
        return new Employee()
                .id(1L)
                .role("CHEF");
    }

    public RestaurantDTO getRestaurantDTOWithEmployeeListDTO(){
        return new RestaurantDTO()
                .id(1L)
                .name("Francesinhas")
                .capacity(100)
                .menu(getMenuDTO())
                .hereId("hereId")
                .location(getLocationDTO())
                .schedule(getScheduleRoutineDTO())
                .employeeList(Set.of(getEmployeeDTOWithoutRestaurantListDTO()));
    }

    public EmployeeDTO getEmployeeDTOWithoutRestaurantListDTO(){
        return new EmployeeDTO()
                .id(1L)
                .schedule(new ScheduleRoutineDTO())
                .role("CHEF");
    }

    public Employee getEmployeeWithoutSchedule(){
        Employee employee = new Employee()
                .role("CHEF");
        employee.setRestaurantList(Set.of(getRestaurant()));
        return employee;
    }

    public EmployeeDTO getEmployeeDTOWithoutScheduleDTO(){
        EmployeeDTO employeeDTO = new EmployeeDTO()
                .id(1L)
                .role("CHEF");
        employeeDTO.setRestaurantList(Set.of(getRestaurantDTO()));
        return employeeDTO;
    }

    public HereRestaurantInfo getHereRestaurantInfo(){

        ScheduleTime weekScheduleTime = new ScheduleTime()
                .startTime(LocalTime.of(8, 0)).endTime(LocalTime.of(23, 0));

        Map<DayOfWeek, List<ScheduleTime>> daysMap = new HashMap<>();
        daysMap.put(DayOfWeek.TUESDAY, Collections.singletonList(weekScheduleTime));
        daysMap.put(DayOfWeek.WEDNESDAY, Collections.singletonList(weekScheduleTime));
        daysMap.put(DayOfWeek.THURSDAY, Collections.singletonList(weekScheduleTime));
        daysMap.put(DayOfWeek.FRIDAY, Collections.singletonList(weekScheduleTime));
        daysMap.put(DayOfWeek.SATURDAY, Collections.singletonList(weekScheduleTime));
        daysMap.put(DayOfWeek.SUNDAY, Collections.singletonList(
                new ScheduleTime()
                .startTime(LocalTime.of(17, 0))
                .endTime(LocalTime.of(23, 0))));

        return new HereRestaurantInfo()
                .hereId("620aabd1-28cfe099c787060e95c8c46d483f795c")
                .searchLocation(new Location()
                        .id(0L)
                        .address(new Address()
                                .id(0L)
                                .name("Rua Joaquim Lopes Pintor 178")
                                .postalCode("4405-868")
                                .city("Vila Nova de Gaia")
                                .country("Portugal"))
                        .coordinate(new Coordinate()
                                .latitude(41.109909)
                                .longitude(-8.621536)))
                .distance(13L)
                .name("Cafe Hamburgo")
                .schedule(new ScheduleRoutine().daysScheduleMap(daysMap))
                .location(new Location()
                        .id(0L)
                        .address(new Address()
                                .id(0L)
                                .name("Rua Joaquim Lopes Pintor 176")
                                .postalCode("4405-868")
                                .city("Vila Nova de Gaia"))
                        .coordinate(new Coordinate()
                                .latitude(41.109795)
                                .longitude(-8.621567)));

    }

}
