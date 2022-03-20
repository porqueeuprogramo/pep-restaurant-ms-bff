package com.pep.restaurant.ms.bff.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pep.restaurant.ms.bff.RestaurantMsBffApplication;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.web.api.model.LocationDTO;
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
import java.util.Objects;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ActiveProfiles("test")
@SpringBootTest(classes = RestaurantMsBffApplication.class)
public class LocationControllerTest {

    @Autowired
    private LocationController locationController;

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
    public void passingLocationIdToController_GetLocation() {

        LocationDTO locationDTO = applicationDataProvider.getLocationDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/location/1")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(locationDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<LocationDTO> result = locationController.getLocation(1L);

        Assertions.assertEquals(locationDTO.getId(),
                Objects.requireNonNull(result.getBody()).getId());
        Assertions.assertEquals(locationDTO.getAddress().getName()
                , result.getBody().getAddress().getName());
        Assertions.assertEquals(locationDTO.getAddress().getCountry()
                , result.getBody().getAddress().getCountry());
        Assertions.assertEquals(locationDTO.getAddress().getCity()
                , result.getBody().getAddress().getCity());
        Assertions.assertEquals(locationDTO.getAddress().getPostalCode()
                , result.getBody().getAddress().getPostalCode());
        Assertions.assertEquals(locationDTO.getLocationCoordinate().getLatitude()
                , result.getBody().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(locationDTO.getLocationCoordinate().getLongitude()
                , result.getBody().getLocationCoordinate().getLongitude(),0);
    }

    @Test
    public void passingLocationToController_CreateLocation() {

        LocationDTO locationDTO = applicationDataProvider.getLocationDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/location")))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(locationDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<LocationDTO> result = locationController.createLocation(locationDTO);

        Assertions.assertEquals(locationDTO.getId(),
                Objects.requireNonNull(result.getBody()).getId());
        Assertions.assertEquals(locationDTO.getAddress().getName()
                , result.getBody().getAddress().getName());
        Assertions.assertEquals(locationDTO.getAddress().getCountry()
                , result.getBody().getAddress().getCountry());
        Assertions.assertEquals(locationDTO.getAddress().getCity()
                , result.getBody().getAddress().getCity());
        Assertions.assertEquals(locationDTO.getAddress().getPostalCode()
                , result.getBody().getAddress().getPostalCode());
        Assertions.assertEquals(locationDTO.getLocationCoordinate().getLatitude()
                , result.getBody().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(locationDTO.getLocationCoordinate().getLongitude()
                , result.getBody().getLocationCoordinate().getLongitude(),0);
    }

    @Test
    public void passingLocationIdAndALocationToController_EditLocation() {

        LocationDTO locationDTO = applicationDataProvider.getLocationDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/location/1")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(locationDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<LocationDTO> result = locationController.editLocation(1L, locationDTO);

        Assertions.assertEquals(locationDTO.getId(),
                Objects.requireNonNull(result.getBody()).getId());
        Assertions.assertEquals(locationDTO.getAddress().getName()
                , result.getBody().getAddress().getName());
        Assertions.assertEquals(locationDTO.getAddress().getCountry()
                , result.getBody().getAddress().getCountry());
        Assertions.assertEquals(locationDTO.getAddress().getCity()
                , result.getBody().getAddress().getCity());
        Assertions.assertEquals(locationDTO.getAddress().getPostalCode()
                , result.getBody().getAddress().getPostalCode());
        Assertions.assertEquals(locationDTO.getLocationCoordinate().getLatitude()
                , result.getBody().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(locationDTO.getLocationCoordinate().getLongitude()
                , result.getBody().getLocationCoordinate().getLongitude(),0);
    }

    @Test
    public void passingLocationIdToController_DeleteLocation() {

        LocationDTO locationDTO = applicationDataProvider.getLocationDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/location/1")))
                    .andExpect(method(HttpMethod.DELETE))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(locationDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<LocationDTO> result = locationController.deleteLocation(1L);

        Assertions.assertEquals(locationDTO.getId(),
                Objects.requireNonNull(result.getBody()).getId());
        Assertions.assertEquals(locationDTO.getAddress().getName()
                , result.getBody().getAddress().getName());
        Assertions.assertEquals(locationDTO.getAddress().getCountry()
                , result.getBody().getAddress().getCountry());
        Assertions.assertEquals(locationDTO.getAddress().getCity()
                , result.getBody().getAddress().getCity());
        Assertions.assertEquals(locationDTO.getAddress().getPostalCode()
                , result.getBody().getAddress().getPostalCode());
        Assertions.assertEquals(locationDTO.getLocationCoordinate().getLatitude()
                , result.getBody().getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(locationDTO.getLocationCoordinate().getLongitude()
                , result.getBody().getLocationCoordinate().getLongitude(),0);
    }

    @Test
    public void checkLocationListRetrievedAfterControllerCall() {

        LocationDTO locationDTO = applicationDataProvider.getLocationDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        List<LocationDTO> expected = Collections.singletonList(locationDTO);

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/location")))
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

        ResponseEntity<List<LocationDTO>> result = locationController.getAllLocations();

        Assertions.assertEquals(locationDTO.getId(),
                Objects.requireNonNull(result.getBody()).get(0).getId());
        Assertions.assertEquals(locationDTO.getAddress().getName()
                , result.getBody().get(0).getAddress().getName());
        Assertions.assertEquals(locationDTO.getAddress().getCountry()
                , result.getBody().get(0).getAddress().getCountry());
        Assertions.assertEquals(locationDTO.getAddress().getCity()
                , result.getBody().get(0).getAddress().getCity());
        Assertions.assertEquals(locationDTO.getAddress().getPostalCode()
                , result.getBody().get(0).getAddress().getPostalCode());
        Assertions.assertEquals(locationDTO.getLocationCoordinate().getLatitude()
                , result.getBody().get(0).getLocationCoordinate().getLatitude(), 0);
        Assertions.assertEquals(locationDTO.getLocationCoordinate().getLongitude()
                , result.getBody().get(0).getLocationCoordinate().getLongitude(),0);

    }
}
