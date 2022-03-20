package com.pep.restaurant.ms.bff.web.rest;

import com.pep.restaurant.ms.bff.service.EmployeeService;
import com.pep.restaurant.ms.bff.service.mapper.EmployeeMapper;
import com.pep.restaurant.ms.bff.web.api.EmployeeApi;
import com.pep.restaurant.ms.bff.web.api.model.EmployeeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
public class EmployeeController implements EmployeeApi, ApiController {

    public static final String EMPLOYEE_EMPLOYEE_ID = "/employee/{employeeId}";
    public static final String EMPLOYEE = "/employee";
    public static final String EMPLOYEE_ADD_RESTAURANT_EMPLOYEE_ID_RESTAURANT_ID =
            "/employee/add/restaurant/{employeeId}/{restaurantId}";
    public static final String EMPLOYEE_REMOVE_RESTAURANT_EMPLOYEE_ID_RESTAURANT_ID =
            "/employee/remove/restaurant/{employeeId}/{restaurantId}";
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;


    /**
     * Constructor for Employee Controller.
     *
     * @param employeeService Employee Service.
     * @param employeeMapper  Employee Mapper.
     */
    public EmployeeController(final EmployeeService employeeService, final EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Controller to get a employee by id.
     *
     * @param employeeId id of employee to get.
     * @return EmployeeDTO with the provided id.
     */

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = EMPLOYEE_EMPLOYEE_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<EmployeeDTO> getEmployee(final Long employeeId) {
        return ResponseEntity.ok(employeeMapper.mapEmployeeToEmployeeDTO(
                employeeService.getEmployee(employeeId)));
    }

    /**
     * Controller to create a employee.
     *
     * @param employeeDTO employeeDTO to create.
     * @return EmployeeDTO created.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(value = EMPLOYEE,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody final EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeMapper.mapEmployeeToEmployeeDTO(
                employeeService.createEmployee(employeeMapper.mapEmployeeDTOToEmployee(employeeDTO))));
    }

    /**
     * Controller to edit a employee.
     *
     * @param employeeId employee to be edited.
     * @param employeeDTO employeeDTO new data.
     * @return EmployeeDTO created.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(value = EMPLOYEE_EMPLOYEE_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<EmployeeDTO> editEmployee(final Long employeeId,
                                                        @RequestBody final EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeMapper.mapEmployeeToEmployeeDTO(
                employeeService.editEmployee(employeeId,
                        employeeMapper.mapEmployeeDTOToEmployee(employeeDTO))));

    }

    /**
     * Controller to add employee to employee.
     *
     * @param employeeId employee id.
     * @param restaurantId restaurant id.
     * @return EmployeeDTO with restaurant added.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(value = EMPLOYEE_ADD_RESTAURANT_EMPLOYEE_ID_RESTAURANT_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<EmployeeDTO> addRestaurant(final Long employeeId, final Long restaurantId) {
        return ResponseEntity.ok(employeeMapper.mapEmployeeToEmployeeDTO(
                employeeService.addRestaurant(employeeId, restaurantId)));
    }

    /**
     * Controller to remove employee from employee.
     *
     * @param employeeId employee id.
     * @param restaurantId restaurant id.
     * @return EmployeeDTO with restaurant removed.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(value = EMPLOYEE_REMOVE_RESTAURANT_EMPLOYEE_ID_RESTAURANT_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<EmployeeDTO> removeRestaurant(final Long employeeId, final Long restaurantId) {
        return ResponseEntity.ok(employeeMapper.mapEmployeeToEmployeeDTO(
                employeeService.removeRestaurant(employeeId, restaurantId)));
    }

    /**
     * Controller to delete employee by id.
     * @param employeeId employee Id.
     * @return Employee deleted.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping(value = EMPLOYEE_EMPLOYEE_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<EmployeeDTO> deleteEmployee(final Long employeeId) {
        return ResponseEntity.ok(employeeMapper.mapEmployeeToEmployeeDTO(
                employeeService.deleteEmployee(employeeId)));
    }

    /**
     * Controller to Get all employees.
     * @return Employees List.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = EMPLOYEE,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeMapper.mapEmployeeListToEmployeeDTOList(
                employeeService.getAllEmployees()));
    }
}
