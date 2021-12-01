package com.pep.restaurant.ms.bff.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pep.restaurant.ms.bff.RestaurantMsBffApplication;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.web.api.model.EmployeeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ActiveProfiles("test")
@SpringBootTest(classes = RestaurantMsBffApplication.class)
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void passingEmployeeIdToController_GetEmployee() {

        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/employee/1")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(employeeDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<EmployeeDTO> result = employeeController.getEmployee(1L);
        Assertions.assertEquals(employeeDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(employeeDTO.getRole(), result.getBody().getRole());
        Assertions.assertEquals(employeeDTO.getSchedule().getId(), result.getBody().getSchedule().getId());
        Assertions.assertEquals(1, result.getBody().getRestaurantList().size());
    }

    @Test
    public void passingEmployeeToController_CreateEmployee() {

        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/employee")))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(employeeDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<EmployeeDTO> result = employeeController.createEmployee(employeeDTO);
        Assertions.assertEquals(employeeDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(employeeDTO.getRole(), result.getBody().getRole());
        Assertions.assertEquals(employeeDTO.getSchedule().getId(), result.getBody().getSchedule().getId());
        Assertions.assertEquals(1, result.getBody().getRestaurantList().size());
    }

    @Test
    public void passingEmployeeIdAndAEmployeeToController_EditEmployee() {

        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/employee/1")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(employeeDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<EmployeeDTO> result = employeeController.editEmployee(1L, employeeDTO);
        Assertions.assertEquals(employeeDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(employeeDTO.getRole(), result.getBody().getRole());
        Assertions.assertEquals(employeeDTO.getSchedule().getId(), result.getBody().getSchedule().getId());
        Assertions.assertEquals(1, result.getBody().getRestaurantList().size());
    }

    @Test
    public void passingEmployeeIdAndAnEmployeeIdToController_AddEmployeeToEmployee() {

        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/employee/add/restaurant/1/1")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(employeeDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<EmployeeDTO> result = employeeController.addRestaurant(1L, 1L);
        Assertions.assertEquals(employeeDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(employeeDTO.getRole(), result.getBody().getRole());
        Assertions.assertEquals(employeeDTO.getSchedule().getId(), result.getBody().getSchedule().getId());
        Assertions.assertEquals(1, result.getBody().getRestaurantList().size());
    }

    @Test
    public void passingEmployeeIdAndAnEmployeeIdToController_RemoveEmployeeFromEmployee() {

        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/employee/remove/restaurant/1/1")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(employeeDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<EmployeeDTO> result = employeeController.removeRestaurant(1L, 1L);
        Assertions.assertEquals(employeeDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(employeeDTO.getRole(), result.getBody().getRole());
        Assertions.assertEquals(employeeDTO.getSchedule().getId(), result.getBody().getSchedule().getId());
        Assertions.assertEquals(1, result.getBody().getRestaurantList().size());
    }

    @Test
    public void passingEmployeeIdToController_DeleteEmployee() {

        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/employee/1")))
                    .andExpect(method(HttpMethod.DELETE))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(employeeDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<EmployeeDTO> result = employeeController.deleteEmployee(1L);
        Assertions.assertEquals(employeeDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(employeeDTO.getRole(), result.getBody().getRole());
        Assertions.assertEquals(employeeDTO.getSchedule().getId(), result.getBody().getSchedule().getId());
        Assertions.assertEquals(1, result.getBody().getRestaurantList().size());
    }

    @Test
    public void checkEmployeeListRetrievedAfterControllerCall() {

        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        List<EmployeeDTO> expected = Collections.singletonList(employeeDTO);

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/employee")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(expected))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<List<EmployeeDTO>> result = employeeController.getAllEmployees();
        Assertions.assertEquals(employeeDTO.getId(), result.getBody().get(0).getId());
        Assertions.assertEquals(employeeDTO.getRole(), result.getBody().get(0).getRole());
        Assertions.assertEquals(employeeDTO.getSchedule().getId(), result.getBody().get(0).getSchedule().getId());
        Assertions.assertEquals(1, result.getBody().get(0).getRestaurantList().size());
    }
}
