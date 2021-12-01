package com.pep.restaurant.ms.bff.client;

import com.pep.restaurant.ms.bff.config.ApplicationProperties;
import com.pep.restaurant.ms.bff.domain.Employee;
import com.pep.restaurant.ms.bff.logging.Logger;
import com.pep.restaurant.ms.bff.logging.enumeration.LogTag;
import com.pep.restaurant.ms.bff.service.mapper.EmployeeMapper;
import com.pep.restaurant.ms.bff.web.api.model.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class EmployeeClient {

    private static final Logger LOGGER = new Logger(EmployeeClient.class);
    public static final String EMPLOYEE_EMPLOYEE_ID = "/api/employee/{employeeId}";
    public static final String EMPLOYEE = "/api/employee";
    public static final String EMPLOYEE_ADD_RESTAURANT_EMPLOYEE_ID_RESTAURANT_ID =
            "/api/employee/add/restaurant/{employeeId}/{restaurantId}";
    public static final String EMPLOYEE_REMOVE_RESTAURANT_EMPLOYEE_ID_RESTAURANT_ID =
            "/api/employee/remove/restaurant/{employeeId}/{restaurantId}";
    private final RestTemplate restTemplate;
    private final EmployeeMapper employeeMapper;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public EmployeeClient(final RestTemplate restTemplate,
                          final EmployeeMapper employeeMapper,
                          final ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.employeeMapper = employeeMapper;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Client to get Employee by id.
     * @param employeeId employee id.
     * @return employee.
     */
    public Employee getEmployee(final long employeeId){
        final String url =  applicationProperties.getMsManager().getUrl().concat(EMPLOYEE_EMPLOYEE_ID);
        EmployeeDTO employeeDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestGetEmployeeId = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("employeeId", String.valueOf(employeeId));

        try{
            final ResponseEntity<EmployeeDTO> responseEmployee = restTemplate.exchange(url, HttpMethod.GET,
                    requestGetEmployeeId,
                    new ParameterizedTypeReference<EmployeeDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseEmployee.getStatusCode())){
                employeeDTO = responseEmployee.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.EMPLOYEES, LogTag.RETRIEVED),
                    "Employee id not found");
        }

        return employeeMapper.mapEmployeeDTOToEmployee(employeeDTO);
    }

    /**
     * Client to create employee.
     * @param employee employee.
     * @return employee.
     */
    public Employee createEmployee(final Employee employee){
        final String url =  applicationProperties.getMsManager().getUrl().concat(EMPLOYEE);
        EmployeeDTO employeeDTO = null;

        final EmployeeDTO requestEmployeeDTO = employeeMapper.mapEmployeeToEmployeeDTO(employee);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<EmployeeDTO> requestCreateEmployeeClient = new HttpEntity(requestEmployeeDTO ,headers);

        try{
            final ResponseEntity<EmployeeDTO> responseEmployeeCreated = restTemplate.exchange(url, HttpMethod.POST,
                    requestCreateEmployeeClient, EmployeeDTO.class);
            if(HttpStatus.OK.equals(responseEmployeeCreated.getStatusCode())){
                employeeDTO = responseEmployeeCreated.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.EMPLOYEES, LogTag.PERSISTED)
                    , "Employee was not created!!");
        }

        return employeeMapper.mapEmployeeDTOToEmployee(employeeDTO);
    }

    /**
     * Client to edit employee.
     * @param employeeId employee id.
     * @param employee employee.
     * @return employee.
     */
    public Employee editEmployee(final long employeeId, final Employee employee){
        final String url =  applicationProperties.getMsManager().getUrl().concat(EMPLOYEE_EMPLOYEE_ID);
        EmployeeDTO employeeDTO = null;

        final EmployeeDTO requestEmployeeDTO = employeeMapper.mapEmployeeToEmployeeDTO(employee);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<EmployeeDTO> requestEditEmployeeClient = new HttpEntity(requestEmployeeDTO ,headers);
        final Map<String, String> params = new HashMap<>();
        params.put("employeeId", String.valueOf(employeeId));

        try{
            final ResponseEntity<EmployeeDTO> responseEmployeeEdited = restTemplate.exchange(url, HttpMethod.PUT,
                    requestEditEmployeeClient, EmployeeDTO.class, params);
            if(HttpStatus.OK.equals(responseEmployeeEdited.getStatusCode())){
                employeeDTO = responseEmployeeEdited.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.EMPLOYEES, LogTag.EDITED)
                    , "Employee was not edited!!");
        }

        return employeeMapper.mapEmployeeDTOToEmployee(employeeDTO);
    }

    /**
     * Client to add restaurant to employee.
     * @param employeeId employee id.
     * @param restaurantId restaurant id.
     * @return employee.
     */
    public Employee addRestaurant(final Long employeeId, final Long restaurantId) {
        final String url =  applicationProperties.getMsManager().getUrl()
                .concat(EMPLOYEE_ADD_RESTAURANT_EMPLOYEE_ID_RESTAURANT_ID);
        EmployeeDTO employeeDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestAddEmployeeToEmployeeClient = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("employeeId", String.valueOf(employeeId));
        params.put("restaurantId", String.valueOf(restaurantId));

        try{
            final ResponseEntity<EmployeeDTO> responseEmployeeWithEmployeeAdded = restTemplate.exchange(url,
                    HttpMethod.PUT,
                    requestAddEmployeeToEmployeeClient, new ParameterizedTypeReference<EmployeeDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseEmployeeWithEmployeeAdded.getStatusCode())){
                employeeDTO = responseEmployeeWithEmployeeAdded.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.EMPLOYEES, LogTag.EDITED)
                    , "Employee was not added to Employee!!");
        }

        return employeeMapper.mapEmployeeDTOToEmployee(employeeDTO);
    }

    /**
     * Client to remove restaurant from employee.
     * @param employeeId employee id.
     * @param restaurantId restaurant id.
     * @return employee.
     */
    public Employee removeRestaurant(final Long employeeId, final Long restaurantId) {
        final String url =  applicationProperties.getMsManager().getUrl()
                .concat(EMPLOYEE_REMOVE_RESTAURANT_EMPLOYEE_ID_RESTAURANT_ID);
        EmployeeDTO employeeDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestAddEmployeeToEmployeeClient = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("employeeId", String.valueOf(employeeId));
        params.put("restaurantId", String.valueOf(restaurantId));

        try{
            final ResponseEntity<EmployeeDTO> responseEmployeeWithEmployeeAdded = restTemplate.exchange(url,
                    HttpMethod.PUT,
                    requestAddEmployeeToEmployeeClient, new ParameterizedTypeReference<EmployeeDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseEmployeeWithEmployeeAdded.getStatusCode())){
                employeeDTO = responseEmployeeWithEmployeeAdded.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.EMPLOYEES, LogTag.EDITED)
                    , "Employee was not removed from Employee!!");
        }

        return employeeMapper.mapEmployeeDTOToEmployee(employeeDTO);
    }

    /**
     * Client to delete employee.
     * @param employeeId employee id.
     * @return employee deleted.
     */
    public Employee deleteEmployee(final Long employeeId) {
        final String url =  applicationProperties.getMsManager().getUrl().concat(EMPLOYEE_EMPLOYEE_ID);
        EmployeeDTO employeeDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestGetEmployeeId = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("employeeId", String.valueOf(employeeId));

        try{
            final ResponseEntity<EmployeeDTO> responseEmployee = restTemplate.exchange(url, HttpMethod.DELETE,
                    requestGetEmployeeId,
                    new ParameterizedTypeReference<EmployeeDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseEmployee.getStatusCode())){
                employeeDTO = responseEmployee.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.EMPLOYEES, LogTag.DELETED),
                    "Employee id not found");
        }

        return employeeMapper.mapEmployeeDTOToEmployee(employeeDTO);
    }

    /**
     * Client to get all employees.
     * @return employees list.
     */
    public List<Employee> getAllEmployees() {
        final String url =  applicationProperties.getMsManager().getUrl().concat(EMPLOYEE);
        List<EmployeeDTO> employeeDTOList = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestGetEmployeeId = new HttpEntity(headers);

        try{
            final ResponseEntity<List<EmployeeDTO>> responseEmployee = restTemplate.exchange(url, HttpMethod.GET,
                    requestGetEmployeeId,
                    new ParameterizedTypeReference<List<EmployeeDTO>>() {
                    });
            if(HttpStatus.OK.equals(responseEmployee.getStatusCode())){
                employeeDTOList = responseEmployee.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.EMPLOYEES, LogTag.RETRIEVED),
                    "Employee list not found");
        }

        return employeeMapper.mapEmployeeDTOListToEmployeeList(employeeDTOList);
    }
}
