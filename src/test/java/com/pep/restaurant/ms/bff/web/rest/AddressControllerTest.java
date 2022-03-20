package com.pep.restaurant.ms.bff.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pep.restaurant.ms.bff.RestaurantMsBffApplication;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.web.api.model.AddressDTO;
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
public class AddressControllerTest {

    @Autowired
    private AddressController addressController;

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
    public void passingAddressIdToController_GetAddress() {

        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/address/1")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(addressDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<AddressDTO> result = addressController.getAddress(1L);

        Assertions.assertEquals(addressDTO.getId(),
                Objects.requireNonNull(result.getBody()).getId());
        Assertions.assertEquals(addressDTO.getName()
                , result.getBody().getName());
        Assertions.assertEquals(addressDTO.getCountry()
                , result.getBody().getCountry());
        Assertions.assertEquals(addressDTO.getCity()
                , result.getBody().getCity());
        Assertions.assertEquals(addressDTO.getPostalCode()
                , result.getBody().getPostalCode());

    }

    @Test
    public void passingAddressToController_CreateAddress() {

        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/address")))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(addressDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<AddressDTO> result = addressController.createAddress(addressDTO);

        Assertions.assertEquals(addressDTO.getId(),
                Objects.requireNonNull(result.getBody()).getId());
        Assertions.assertEquals(addressDTO.getName()
                , result.getBody().getName());
        Assertions.assertEquals(addressDTO.getCountry()
                , result.getBody().getCountry());
        Assertions.assertEquals(addressDTO.getCity()
                , result.getBody().getCity());
        Assertions.assertEquals(addressDTO.getPostalCode()
                , result.getBody().getPostalCode());
    }

    @Test
    public void passingAddressIdAndAAddressToController_EditAddress() {

        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/address/1")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(addressDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<AddressDTO> result = addressController.editAddress(1L, addressDTO);

        Assertions.assertEquals(addressDTO.getId(),
                Objects.requireNonNull(result.getBody()).getId());
        Assertions.assertEquals(addressDTO.getName()
                , result.getBody().getName());
        Assertions.assertEquals(addressDTO.getCountry()
                , result.getBody().getCountry());
        Assertions.assertEquals(addressDTO.getCity()
                , result.getBody().getCity());
        Assertions.assertEquals(addressDTO.getPostalCode()
                , result.getBody().getPostalCode());
    }

    @Test
    public void passingAddressIdToController_DeleteAddress() {

        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/address/1")))
                    .andExpect(method(HttpMethod.DELETE))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(addressDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<AddressDTO> result = addressController.deleteAddress(1L);

        Assertions.assertEquals(addressDTO.getId(),
                Objects.requireNonNull(result.getBody()).getId());
        Assertions.assertEquals(addressDTO.getName()
                , result.getBody().getName());
        Assertions.assertEquals(addressDTO.getCountry()
                , result.getBody().getCountry());
        Assertions.assertEquals(addressDTO.getCity()
                , result.getBody().getCity());
        Assertions.assertEquals(addressDTO.getPostalCode()
                , result.getBody().getPostalCode());
    }

    @Test
    public void checkAddressListRetrievedAfterControllerCall() {

        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        List<AddressDTO> expected = Collections.singletonList(addressDTO);

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/address")))
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

        ResponseEntity<List<AddressDTO>> result = addressController.getAllAddresses();

        Assertions.assertEquals(addressDTO.getId(),
                Objects.requireNonNull(result.getBody()).get(0).getId());
        Assertions.assertEquals(addressDTO.getName()
                , result.getBody().get(0).getName());
        Assertions.assertEquals(addressDTO.getCountry()
                , result.getBody().get(0).getCountry());
        Assertions.assertEquals(addressDTO.getCity()
                , result.getBody().get(0).getCity());
        Assertions.assertEquals(addressDTO.getPostalCode()
                , result.getBody().get(0).getPostalCode());

    }
}
