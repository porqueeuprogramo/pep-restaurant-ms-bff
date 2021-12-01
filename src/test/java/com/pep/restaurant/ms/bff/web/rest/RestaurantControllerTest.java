package com.pep.restaurant.ms.bff.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pep.restaurant.ms.bff.RestaurantMsBffApplication;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
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
import java.util.Collections;
import java.util.List;

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

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/restaurant/1")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(restaurantDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<RestaurantDTO> result = restaurantController.getRestaurant(1L);
        Assertions.assertEquals(restaurantDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(restaurantDTO.getName(), result.getBody().getName());
        Assertions.assertEquals(restaurantDTO.getLocation(), result.getBody().getLocation());
        Assertions.assertEquals(restaurantDTO.getCapacity(), result.getBody().getCapacity());
        Assertions.assertEquals(restaurantDTO.getMenu().getLanguage(), result.getBody().getMenu().getLanguage());
        Assertions.assertEquals(1, result.getBody().getEmployeeList().size());
    }

    @Test
    public void passingRestaurantToController_CreateRestaurant() {

        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/restaurant")))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(restaurantDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<RestaurantDTO> result = restaurantController.createRestaurant(restaurantDTO);
        Assertions.assertEquals(restaurantDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(restaurantDTO.getName(), result.getBody().getName());
        Assertions.assertEquals(restaurantDTO.getLocation(), result.getBody().getLocation());
        Assertions.assertEquals(restaurantDTO.getCapacity(), result.getBody().getCapacity());
        Assertions.assertEquals(restaurantDTO.getMenu().getLanguage(), result.getBody().getMenu().getLanguage());
        Assertions.assertEquals(1, result.getBody().getEmployeeList().size());
    }

    @Test
    public void passingRestaurantIdAndARestaurantToController_EditRestaurant() {

        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/restaurant/1")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(restaurantDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<RestaurantDTO> result = restaurantController.editRestaurant(1L, restaurantDTO);
        Assertions.assertEquals(restaurantDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(restaurantDTO.getName(), result.getBody().getName());
        Assertions.assertEquals(restaurantDTO.getLocation(), result.getBody().getLocation());
        Assertions.assertEquals(restaurantDTO.getCapacity(), result.getBody().getCapacity());
        Assertions.assertEquals(restaurantDTO.getMenu().getLanguage(), result.getBody().getMenu().getLanguage());
        Assertions.assertEquals(1, result.getBody().getEmployeeList().size());
    }

    @Test
    public void passingRestaurantIdAndAnEmployeeIdToController_AddEmployeeToRestaurant() {

        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/restaurant/add/employee/1/1")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(restaurantDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<RestaurantDTO> result = restaurantController.addEmployee(1L, 1L);
        Assertions.assertEquals(restaurantDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(restaurantDTO.getName(), result.getBody().getName());
        Assertions.assertEquals(restaurantDTO.getLocation(), result.getBody().getLocation());
        Assertions.assertEquals(restaurantDTO.getCapacity(), result.getBody().getCapacity());
        Assertions.assertEquals(restaurantDTO.getMenu().getLanguage(), result.getBody().getMenu().getLanguage());
        Assertions.assertEquals(1, result.getBody().getEmployeeList().size());
    }

    @Test
    public void passingRestaurantIdAndAnEmployeeIdToController_RemoveEmployeeFromRestaurant() {

        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/restaurant/remove/employee/1/1")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(restaurantDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<RestaurantDTO> result = restaurantController.removeEmployee(1L, 1L);
        Assertions.assertEquals(restaurantDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(restaurantDTO.getName(), result.getBody().getName());
        Assertions.assertEquals(restaurantDTO.getLocation(), result.getBody().getLocation());
        Assertions.assertEquals(restaurantDTO.getCapacity(), result.getBody().getCapacity());
        Assertions.assertEquals(restaurantDTO.getMenu().getLanguage(), result.getBody().getMenu().getLanguage());
        Assertions.assertEquals(1, result.getBody().getEmployeeList().size());
    }

    @Test
    public void passingRestaurantIdToController_DeleteRestaurant() {

        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/restaurant/1")))
                    .andExpect(method(HttpMethod.DELETE))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(restaurantDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<RestaurantDTO> result = restaurantController.deleteRestaurant(1L);
        Assertions.assertEquals(restaurantDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(restaurantDTO.getName(), result.getBody().getName());
        Assertions.assertEquals(restaurantDTO.getLocation(), result.getBody().getLocation());
        Assertions.assertEquals(restaurantDTO.getCapacity(), result.getBody().getCapacity());
        Assertions.assertEquals(restaurantDTO.getMenu().getLanguage(), result.getBody().getMenu().getLanguage());
        Assertions.assertEquals(1, result.getBody().getEmployeeList().size());
    }

    @Test
    public void checkRestaurantListRetrievedAfterControllerCall() {

        RestaurantDTO restaurantDTO = applicationDataProvider.getRestaurantDTOWithEmployeeListDTO();

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
                            .body(mapper.writeValueAsString(expected))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<List<RestaurantDTO>> result = restaurantController.getAllRestaurants();
        Assertions.assertEquals(restaurantDTO.getId(), result.getBody().get(0).getId());
        Assertions.assertEquals(restaurantDTO.getName(), result.getBody().get(0).getName());
        Assertions.assertEquals(restaurantDTO.getLocation(), result.getBody().get(0).getLocation());
        Assertions.assertEquals(restaurantDTO.getCapacity(), result.getBody().get(0).getCapacity());
        Assertions.assertEquals(restaurantDTO.getMenu().getLanguage(), result.getBody().get(0).getMenu().getLanguage());
        Assertions.assertEquals(1, result.getBody().get(0).getEmployeeList().size());
    }
}
