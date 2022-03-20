package com.pep.restaurant.ms.bff.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pep.restaurant.ms.bff.RestaurantMsBffApplication;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.web.api.model.EmployeeDTO;
import com.pep.restaurant.ms.bff.web.api.model.RestaurantDTO;
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
import java.util.ArrayList;
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
        List<RestaurantDTO> employeeGivenRestaurantList = new ArrayList<>(employeeDTO.getRestaurantList());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/employee/1")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(employeeDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<EmployeeDTO> result = employeeController.getEmployee(1L);
        List<RestaurantDTO> employeeResultRestaurantList =
                new ArrayList<>(result.getBody().getRestaurantList());

        Assertions.assertEquals(employeeDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(employeeDTO.getRole(), result.getBody().getRole());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getId(), employeeResultRestaurantList.get(0).getId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getName(), employeeResultRestaurantList.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getHereId(), employeeResultRestaurantList.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getSchedule().getDaysScheduleMap().size(),
                employeeResultRestaurantList.get(0).getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());

    }

    @Test
    public void passingEmployeeToController_CreateEmployee() {

        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();
        List<RestaurantDTO> employeeGivenRestaurantList = new ArrayList<>(employeeDTO.getRestaurantList());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/employee")))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(employeeDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<EmployeeDTO> result = employeeController.createEmployee(employeeDTO);
        List<RestaurantDTO> employeeResultRestaurantList =
                new ArrayList<>(result.getBody().getRestaurantList());

        Assertions.assertEquals(employeeDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(employeeDTO.getRole(), result.getBody().getRole());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getId(), employeeResultRestaurantList.get(0).getId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getName(), employeeResultRestaurantList.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getHereId(), employeeResultRestaurantList.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getSchedule().getDaysScheduleMap().size(),
                employeeResultRestaurantList.get(0).getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());
    }

    @Test
    public void passingEmployeeIdAndAEmployeeToController_EditEmployee() {

        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();
        List<RestaurantDTO> employeeGivenRestaurantList = new ArrayList<>(employeeDTO.getRestaurantList());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/employee/1")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(employeeDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<EmployeeDTO> result = employeeController.editEmployee(1L, employeeDTO);
        List<RestaurantDTO> employeeResultRestaurantList =
                new ArrayList<>(result.getBody().getRestaurantList());

        Assertions.assertEquals(employeeDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(employeeDTO.getRole(), result.getBody().getRole());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getId(), employeeResultRestaurantList.get(0).getId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getName(), employeeResultRestaurantList.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getHereId(), employeeResultRestaurantList.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getSchedule().getDaysScheduleMap().size(),
                employeeResultRestaurantList.get(0).getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());
    }

    @Test
    public void passingEmployeeIdAndARestaurantIdToController_AddRestaurantToEmployee() {

        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();
        List<RestaurantDTO> employeeGivenRestaurantList = new ArrayList<>(employeeDTO.getRestaurantList());

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
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(employeeDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<EmployeeDTO> result = employeeController.addRestaurant(1L, 1L);
        List<RestaurantDTO> employeeResultRestaurantList =
                new ArrayList<>(result.getBody().getRestaurantList());

        Assertions.assertEquals(employeeDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(employeeDTO.getRole(), result.getBody().getRole());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getId(), employeeResultRestaurantList.get(0).getId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getName(), employeeResultRestaurantList.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getHereId(), employeeResultRestaurantList.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getSchedule().getDaysScheduleMap().size(),
                employeeResultRestaurantList.get(0).getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());
    }

    @Test
    public void passingEmployeeIdAndARestaurantToController_RemoveRestaurantFromEmployee() {

        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();
        List<RestaurantDTO> employeeGivenRestaurantList = new ArrayList<>(employeeDTO.getRestaurantList());

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
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(employeeDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<EmployeeDTO> result = employeeController.removeRestaurant(1L, 1L);
        List<RestaurantDTO> employeeResultRestaurantList =
                new ArrayList<>(result.getBody().getRestaurantList());

        Assertions.assertEquals(employeeDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(employeeDTO.getRole(), result.getBody().getRole());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getId(), employeeResultRestaurantList.get(0).getId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getName(), employeeResultRestaurantList.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getHereId(), employeeResultRestaurantList.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getSchedule().getDaysScheduleMap().size(),
                employeeResultRestaurantList.get(0).getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());
    }

    @Test
    public void passingEmployeeIdToController_DeleteEmployee() {

        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();
        List<RestaurantDTO> employeeGivenRestaurantList = new ArrayList<>(employeeDTO.getRestaurantList());

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
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(employeeDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<EmployeeDTO> result = employeeController.deleteEmployee(1L);
        List<RestaurantDTO> employeeResultRestaurantList =
                new ArrayList<>(result.getBody().getRestaurantList());

        Assertions.assertEquals(employeeDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(employeeDTO.getRole(), result.getBody().getRole());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getId(), employeeResultRestaurantList.get(0).getId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getName(), employeeResultRestaurantList.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getHereId(), employeeResultRestaurantList.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getSchedule().getDaysScheduleMap().size(),
                employeeResultRestaurantList.get(0).getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());
    }

    @Test
    public void checkEmployeeListRetrievedAfterControllerCall() {

        EmployeeDTO employeeDTO = applicationDataProvider.getEmployeeDTO();
        List<RestaurantDTO> employeeGivenRestaurantList = new ArrayList<>(employeeDTO.getRestaurantList());

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
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(expected))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<List<EmployeeDTO>> result = employeeController.getAllEmployees();
        List<RestaurantDTO> employeeResultRestaurantList =
                new ArrayList<>(result.getBody().get(0).getRestaurantList());

        Assertions.assertEquals(employeeDTO.getId(), result.getBody().get(0).getId());
        Assertions.assertEquals(employeeDTO.getRole(), result.getBody().get(0).getRole());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getId(), employeeResultRestaurantList.get(0).getId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getName()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCountry()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getCity()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getAddress().getPostalCode()
                , employeeResultRestaurantList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude()
                , employeeResultRestaurantList.get(0).getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getCapacity(),
                employeeResultRestaurantList.get(0).getCapacity());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getName(), employeeResultRestaurantList.get(0).getName());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getHereId(), employeeResultRestaurantList.get(0).getHereId());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getSchedule().getDaysScheduleMap().size(),
                employeeResultRestaurantList.get(0).getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(employeeGivenRestaurantList.get(0).getMenu().getLanguage(),
                employeeResultRestaurantList.get(0).getMenu().getLanguage());
    }
}
