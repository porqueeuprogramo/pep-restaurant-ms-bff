package com.pep.restaurant.ms.bff.provider;

import com.pep.restaurant.ms.bff.domain.Employee;
import com.pep.restaurant.ms.bff.domain.Menu;
import com.pep.restaurant.ms.bff.domain.Restaurant;
import com.pep.restaurant.ms.bff.domain.Schedule;
import com.pep.restaurant.ms.bff.web.api.model.*;

import java.util.Collections;
import java.util.Set;

public class ApplicationDataProvider {

    public Restaurant getRestaurantWithEmployees(){
        return new Restaurant()
                .id(1L)
                .name("Francesinhas")
                .location("Porto")
                .capacity(100)
                .menu(getMenu())
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



    public Restaurant getRestaurant(){
        return new Restaurant()
                .id(1L)
                .name("Francesinhas")
                .location("Porto")
                .capacity(100)
                .menu(getMenu());
    }

    public Menu getMenu(){
        return new Menu()
                .language("PORTUGUESE");
    }

    public Employee getEmployee(){
        Employee employee = new Employee()
                .id(0L)
                .role("CHEF")
                .schedule(getSchedule());
        employee.setRestaurantList(Set.of(getRestaurant()));
        return employee;
    }

    public Schedule getSchedule(){
        return new Schedule()
                .id(0L)
                .type(ScheduleType.FULLTIME);
    }

    public RestaurantDTO getRestaurantDTO(){
        return new RestaurantDTO()
                .id(1L)
                .name("Francesinhas")
                .location("Porto")
                .capacity(100)
                .menu(getMenuDTO());
    }

    public MenuDTO getMenuDTO(){
        return new MenuDTO()
                .id(1L)
                .language("PORTUGUESE");
    }

    public EmployeeDTO getEmployeeDTO(){
        EmployeeDTO employee = new EmployeeDTO()
                .id(1L)
                .role("CHEF")
                .schedule(getScheduleDTO());
        employee.setRestaurantList(Set.of(getRestaurantDTO()));
        return employee;
    }

    public ScheduleDTO getScheduleDTO(){
        return new ScheduleDTO()
                .id(1L)
                .type(ScheduleType.FULLTIME);
    }

    public Restaurant getRestaurantWithEmployeeList(){
        return new Restaurant()
                .name("Francesinhas")
                .location("Porto")
                .capacity(100)
                .menu(getMenu())
                .employeeList(Set.of(getEmployeeWithoutRestaurantList()));
    }

    public Restaurant getRestaurantWithEmployeeListOnlyWithRole(){
        return new Restaurant()
                .name("Francesinhas")
                .location("Porto")
                .capacity(100)
                .menu(getMenu())
                .employeeList(Set.of(getEmployeeWithoutRestaurantListWithId()));
    }

    public Employee getEmployeeWithoutRestaurantListAndWithoutSchedule(){
        return new Employee()
                .role("CHEF");
    }

    public Employee getEmployeeWithoutRestaurantList(){
        return new Employee()
                .role("CHEF")
                .schedule(getSchedule());
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
                .location("Porto")
                .capacity(100)
                .menu(getMenuDTO())
                .employeeList(Set.of(getEmployeeDTOWithoutRestaurantListDTO()));
    }

    public EmployeeDTO getEmployeeDTOWithoutRestaurantListDTO(){
        return new EmployeeDTO()
                .id(1L)
                .role("CHEF")
                .schedule(getScheduleDTO());
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

    public Schedule getScheduleWithEmployeeList(){
        return new Schedule()
                .type(ScheduleType.FULLTIME)
                .employeeList(Collections.singletonList(getEmployeeWithoutSchedule()));
    }

    public ScheduleDTO getScheduleDTOWithEmployeeDTOList(){
        return new ScheduleDTO()
                .id(1L)
                .type(ScheduleType.FULLTIME)
                .employeeList(Collections.singletonList(getEmployeeDTOWithoutScheduleDTO()));
    }

}
