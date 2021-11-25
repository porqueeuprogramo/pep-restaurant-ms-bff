package com.pep.restaurant.ms.bff.service;

import com.pep.restaurant.ms.bff.client.EmployeeClient;
import com.pep.restaurant.ms.bff.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeClient employeeClient;

    @Autowired
    public EmployeeService(final EmployeeClient employeeClient) {
        this.employeeClient = employeeClient;
    }

    /**
     * Call get employee client.
     * @param employeeId employee id.
     * @return employee.
     */
    public Employee getEmployee(final long employeeId) {
        return employeeClient.getEmployee(employeeId);
    }

    /**
     * Call create employee client.
     * @param employee employee to create.
     * @return employee.
     */
    public Employee createEmployee(final Employee employee) {
        return employeeClient.createEmployee(employee);
    }

    /**
     * Call edit employee client.
     * @param employeeId employee id to edit.
     * @param employee employee to be edited
     * @return employee edited.
     */
    public Employee editEmployee(final Long employeeId, final Employee employee) {
        return employeeClient.editEmployee(employeeId, employee);
    }

    /**
     * Call add restaurant to employee.
     * @param employeeId employee id.
     * @param restaurantId restaurant id.
     * @return employee with restaurant added.
     */
    public Employee addRestaurant(final Long employeeId, final Long restaurantId) {
        return employeeClient.addRestaurant(employeeId, restaurantId);
    }

    /**
     * Call remove restaurant from employee.
     * @param employeeId employee id.
     * @param restaurantId restaurant id.
     * @return employee with restaurant removed.
     */
    public Employee removeRestaurant(final Long employeeId, final Long restaurantId) {
        return employeeClient.removeRestaurant(employeeId, restaurantId);
    }

    /**
     * Call remove employee.
     * @param employeeId employee id.
     * @return Employee Deleted.
     */
    public Employee deleteEmployee(final Long employeeId) {
        return employeeClient.deleteEmployee(employeeId);
    }

    /**
     * Call Get all employees.
     * @return Employee List.
     */
    public List<Employee> getAllEmployees() {
        return employeeClient.getAllEmployees();
    }
}
