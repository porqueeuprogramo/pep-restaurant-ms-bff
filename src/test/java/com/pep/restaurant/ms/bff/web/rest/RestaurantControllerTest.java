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
import java.util.Objects;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ActiveProfiles("test")
@SpringBootTest(classes = RestaurantMsBffApplication.class)
public class RestaurantControllerTest {

    @Autowired
    private RestaurantController restaurantController;

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
    public void passingRestaurantIdToController_GetRestaurant() {

        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployeeDTO = new ArrayList<>(restaurantDTO.getEmployeeList());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/restaurant/1L")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(restaurantDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<RestaurantDTO> result = restaurantController.getRestaurant("1L");
        List<EmployeeDTO> restaurantResultEmployeeDTO =
                new ArrayList<>(Objects.requireNonNull(result.getBody()).getEmployeeList());

        Assertions.assertEquals(restaurantDTO.getUid(),
                result.getBody().getUid());
        Assertions.assertEquals(restaurantDTO.getMenu().getLanguage(),
                result.getBody().getMenu().getLanguage());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getName()
                , result.getBody().getLocation().getAddress().getName());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getCountry()
                , result.getBody().getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getCity()
                , result.getBody().getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getPostalCode()
                , result.getBody().getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurantDTO.getLocation().getLocationCoordinate().getLatitude()
                , result.getBody().getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(restaurantDTO.getLocation().getLocationCoordinate().getLongitude()
                , result.getBody().getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(restaurantDTO.getName(), result.getBody().getName());
        Assertions.assertEquals(restaurantDTO.getHereId(), result.getBody().getHereId());
        Assertions.assertEquals(restaurantDTO.getSchedule().getDaysScheduleMap().size(),
                result.getBody().getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(restaurantDTO.getCapacity(), result.getBody().getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getUid(), restaurantResultEmployeeDTO.get(0).getUid());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getRole(),
                restaurantResultEmployeeDTO.get(0).getRole());
        
        
    }

    @Test
    public void passingRestaurantToController_CreateRestaurant() {

        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployeeDTO = new ArrayList<>(restaurantDTO.getEmployeeList());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/restaurant")))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(restaurantDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<RestaurantDTO> result = restaurantController.createRestaurant(restaurantDTO);
        List<EmployeeDTO> restaurantResultEmployeeDTO =
                new ArrayList<>(Objects.requireNonNull(result.getBody()).getEmployeeList());

        Assertions.assertEquals(restaurantDTO.getUid(),
                result.getBody().getUid());
        Assertions.assertEquals(restaurantDTO.getMenu().getLanguage(),
                result.getBody().getMenu().getLanguage());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getName()
                , result.getBody().getLocation().getAddress().getName());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getCountry()
                , result.getBody().getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getCity()
                , result.getBody().getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getPostalCode()
                , result.getBody().getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurantDTO.getLocation().getLocationCoordinate().getLatitude()
                , result.getBody().getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(restaurantDTO.getLocation().getLocationCoordinate().getLongitude()
                , result.getBody().getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(restaurantDTO.getName(), result.getBody().getName());
        Assertions.assertEquals(restaurantDTO.getHereId(), result.getBody().getHereId());
        Assertions.assertEquals(restaurantDTO.getSchedule().getDaysScheduleMap().size(),
                result.getBody().getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(restaurantDTO.getCapacity(), result.getBody().getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getUid(), restaurantResultEmployeeDTO.get(0).getUid());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getRole(),
                restaurantResultEmployeeDTO.get(0).getRole());
    }

    @Test
    public void passingRestaurantIdAndARestaurantToController_EditRestaurant() {

        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployeeDTO = new ArrayList<>(restaurantDTO.getEmployeeList());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/restaurant/1L")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(restaurantDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<RestaurantDTO> result = restaurantController.editRestaurant("1L", restaurantDTO);
        List<EmployeeDTO> restaurantResultEmployeeDTO =
                new ArrayList<>(Objects.requireNonNull(result.getBody()).getEmployeeList());

        Assertions.assertEquals(restaurantDTO.getUid(),
                result.getBody().getUid());
        Assertions.assertEquals(restaurantDTO.getMenu().getLanguage(),
                result.getBody().getMenu().getLanguage());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getName()
                , result.getBody().getLocation().getAddress().getName());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getCountry()
                , result.getBody().getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getCity()
                , result.getBody().getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getPostalCode()
                , result.getBody().getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurantDTO.getLocation().getLocationCoordinate().getLatitude()
                , result.getBody().getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(restaurantDTO.getLocation().getLocationCoordinate().getLongitude()
                , result.getBody().getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(restaurantDTO.getName(), result.getBody().getName());
        Assertions.assertEquals(restaurantDTO.getHereId(), result.getBody().getHereId());
        Assertions.assertEquals(restaurantDTO.getSchedule().getDaysScheduleMap().size(),
                result.getBody().getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(restaurantDTO.getCapacity(), result.getBody().getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getUid(), restaurantResultEmployeeDTO.get(0).getUid());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getRole(),
                restaurantResultEmployeeDTO.get(0).getRole());
    }

    @Test
    public void passingRestaurantIdAndAnEmployeeIdToController_AddEmployeeToRestaurant() {

        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployeeDTO = new ArrayList<>(restaurantDTO.getEmployeeList());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/restaurant/add/employee/1L/1L")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(restaurantDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<RestaurantDTO> result = restaurantController.addEmployee("1L", "1L");
        List<EmployeeDTO> restaurantResultEmployeeDTO =
                new ArrayList<>(Objects.requireNonNull(result.getBody()).getEmployeeList());

        Assertions.assertEquals(restaurantDTO.getUid(),
                result.getBody().getUid());
        Assertions.assertEquals(restaurantDTO.getMenu().getLanguage(),
                result.getBody().getMenu().getLanguage());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getName()
                , result.getBody().getLocation().getAddress().getName());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getCountry()
                , result.getBody().getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getCity()
                , result.getBody().getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getPostalCode()
                , result.getBody().getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurantDTO.getLocation().getLocationCoordinate().getLatitude()
                , result.getBody().getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(restaurantDTO.getLocation().getLocationCoordinate().getLongitude()
                , result.getBody().getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(restaurantDTO.getName(), result.getBody().getName());
        Assertions.assertEquals(restaurantDTO.getHereId(), result.getBody().getHereId());
        Assertions.assertEquals(restaurantDTO.getSchedule().getDaysScheduleMap().size(),
                result.getBody().getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(restaurantDTO.getCapacity(), result.getBody().getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getUid(), restaurantResultEmployeeDTO.get(0).getUid());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getRole(),
                restaurantResultEmployeeDTO.get(0).getRole());
    }

    @Test
    public void passingRestaurantIdAndAnEmployeeIdToController_RemoveEmployeeFromRestaurant() {

        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployeeDTO = new ArrayList<>(restaurantDTO.getEmployeeList());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/restaurant/remove/employee/1L/1L")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(restaurantDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<RestaurantDTO> result = restaurantController.removeEmployee("1L", "1L");
        List<EmployeeDTO> restaurantResultEmployeeDTO =
                new ArrayList<>(Objects.requireNonNull(result.getBody()).getEmployeeList());

        Assertions.assertEquals(restaurantDTO.getUid(),
                result.getBody().getUid());
        Assertions.assertEquals(restaurantDTO.getMenu().getLanguage(),
                result.getBody().getMenu().getLanguage());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getName()
                , result.getBody().getLocation().getAddress().getName());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getCountry()
                , result.getBody().getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getCity()
                , result.getBody().getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getPostalCode()
                , result.getBody().getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurantDTO.getLocation().getLocationCoordinate().getLatitude()
                , result.getBody().getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(restaurantDTO.getLocation().getLocationCoordinate().getLongitude()
                , result.getBody().getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(restaurantDTO.getName(), result.getBody().getName());
        Assertions.assertEquals(restaurantDTO.getHereId(), result.getBody().getHereId());
        Assertions.assertEquals(restaurantDTO.getSchedule().getDaysScheduleMap().size(),
                result.getBody().getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(restaurantDTO.getCapacity(), result.getBody().getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getUid(), restaurantResultEmployeeDTO.get(0).getUid());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getRole(),
                restaurantResultEmployeeDTO.get(0).getRole());
    }

    @Test
    public void passingRestaurantIdToController_DeleteRestaurant() {

        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployeeDTO = new ArrayList<>(restaurantDTO.getEmployeeList());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/restaurant/1L")))
                    .andExpect(method(HttpMethod.DELETE))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(restaurantDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<RestaurantDTO> result = restaurantController.deleteRestaurant("1L");
        List<EmployeeDTO> restaurantResultEmployeeDTO =
                new ArrayList<>(Objects.requireNonNull(result.getBody()).getEmployeeList());

        Assertions.assertEquals(restaurantDTO.getUid(),
                result.getBody().getUid());
        Assertions.assertEquals(restaurantDTO.getMenu().getLanguage(),
                result.getBody().getMenu().getLanguage());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getName()
                , result.getBody().getLocation().getAddress().getName());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getCountry()
                , result.getBody().getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getCity()
                , result.getBody().getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getPostalCode()
                , result.getBody().getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurantDTO.getLocation().getLocationCoordinate().getLatitude()
                , result.getBody().getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(restaurantDTO.getLocation().getLocationCoordinate().getLongitude()
                , result.getBody().getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(restaurantDTO.getName(), result.getBody().getName());
        Assertions.assertEquals(restaurantDTO.getHereId(), result.getBody().getHereId());
        Assertions.assertEquals(restaurantDTO.getSchedule().getDaysScheduleMap().size(),
                result.getBody().getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(restaurantDTO.getCapacity(), result.getBody().getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getUid(), restaurantResultEmployeeDTO.get(0).getUid());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getRole(),
                restaurantResultEmployeeDTO.get(0).getRole());
    }

    @Test
    public void checkRestaurantListRetrievedAfterControllerCall() {

        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();
        List<EmployeeDTO> restaurantGivenEmployeeDTO = new ArrayList<>(restaurantDTO.getEmployeeList());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        List<RestaurantDTO> expected = Collections.singletonList(restaurantDTO);

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/restaurant")))
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

        ResponseEntity<List<RestaurantDTO>> result = restaurantController.getAllRestaurants();
        List<EmployeeDTO> restaurantResultEmployeeDTO =
                new ArrayList<>(Objects.requireNonNull(result.getBody()).get(0).getEmployeeList());

        Assertions.assertEquals(restaurantDTO.getUid(),
                result.getBody().get(0).getUid());
        Assertions.assertEquals(restaurantDTO.getMenu().getLanguage(),
                result.getBody().get(0).getMenu().getLanguage());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getName()
                , result.getBody().get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getCountry()
                , result.getBody().get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getCity()
                , result.getBody().get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(restaurantDTO.getLocation().getAddress().getPostalCode()
                , result.getBody().get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(restaurantDTO.getLocation().getLocationCoordinate().getLatitude()
                , result.getBody().get(0).getLocation().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(restaurantDTO.getLocation().getLocationCoordinate().getLongitude()
                , result.getBody().get(0).getLocation().getLocationCoordinate().getLongitude(),0);
        Assertions.assertEquals(restaurantDTO.getName(), result.getBody().get(0).getName());
        Assertions.assertEquals(restaurantDTO.getHereId(), result.getBody().get(0).getHereId());
        Assertions.assertEquals(restaurantDTO.getSchedule().getDaysScheduleMap().size(),
                result.getBody().get(0).getSchedule().getDaysScheduleMap().size());
        Assertions.assertEquals(restaurantDTO.getCapacity(), result.getBody().get(0).getCapacity());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getUid(), restaurantResultEmployeeDTO.get(0).getUid());
        Assertions.assertEquals(restaurantGivenEmployeeDTO.get(0).getRole(),
                restaurantResultEmployeeDTO.get(0).getRole());
    }
}
