package com.pep.restaurant.ms.bff.service;

import com.pep.restaurant.ms.bff.client.EmployeeClient;
import com.pep.restaurant.ms.bff.domain.Employee;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeClient employeeClient;

    private ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void requestingAnEmployeeId_checkIfGetEmployeeClientIsCalled() {
        //Given
        Employee employeeGiven = applicationDataProvider.getEmployee();

        //When
        when(employeeClient.getEmployee("0L")).thenReturn(employeeGiven);
        Employee employee = employeeService.getEmployee("0L");

        //Then
        verify(employeeClient, Mockito.times(1)).getEmployee("0L");
    }


    @Test
    public void requestingAnEmployee_checkIfCreateEmployeeClientIsCalled() {
        //Given
        Employee employeeGiven = applicationDataProvider.getEmployee();

        //When
        when(employeeClient.createEmployee(employeeGiven)).thenReturn(employeeGiven);
        Employee employee = employeeService.createEmployee(employeeGiven);

        //Then
        verify(employeeClient, Mockito.times(1)).createEmployee(employeeGiven);
    }

    @Test
    public void requestingAnEmployeeIdAndAnEmployee_checkIfEditEmployeeClientIsCalled() {
        //Given
        Employee employeeGiven = applicationDataProvider.getEmployee();

        //When
        when(employeeClient.editEmployee("0L", employeeGiven)).thenReturn(employeeGiven);
        Employee employee = employeeService.editEmployee("0L", employeeGiven);

        //Then
        verify(employeeClient, Mockito.times(1)).editEmployee("0L", employeeGiven);
    }


    @Test
    public void requestingAnEmployeeIdAndARestaurantId_checkIfAddRestaurantClientIsCalled() {
        //Given
        Employee employeeGiven = applicationDataProvider.getEmployee();

        //When
        when(employeeClient.addRestaurant("0L", "0L")).thenReturn(employeeGiven);
        Employee employee = employeeService.addRestaurant("0L", "0L");

        //Then
        verify(employeeClient, Mockito.times(1)).addRestaurant("0L", "0L");
    }


    @Test
    public void requestingAnEmployeeIdAndARestaurantId_checkIfRemoveRestaurantClientIsCalled() {
        //Given
        Employee employeeGiven = applicationDataProvider.getEmployee();

        //When
        when(employeeClient.removeRestaurant("0L", "0L")).thenReturn(employeeGiven);
        Employee employee = employeeService.removeRestaurant("0L", "0L");

        //Then
        verify(employeeClient, Mockito.times(1)).removeRestaurant("0L", "0L");
    }


    @Test
    public void requestingAnEmployeeId_checkIfDeleteEmployeeClientIsCalled() {
        //Given
        Employee employeeGiven = applicationDataProvider.getEmployee();

        //When
        when(employeeClient.deleteEmployee("0L")).thenReturn(employeeGiven);
        Employee employee = employeeService.deleteEmployee("0L");

        //Then
        verify(employeeClient, Mockito.times(1)).deleteEmployee("0L");
    }

    @Test
    public void checkIfGetAllEmployeesClientIsCalled() {
        //Given
        Employee employeeGiven = applicationDataProvider.getEmployee();

        //When
        when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(employeeGiven));
        List<Employee> employeeList = employeeService.getAllEmployees();

        //Then
        verify(employeeClient, Mockito.times(1)).getAllEmployees();
    }
}
