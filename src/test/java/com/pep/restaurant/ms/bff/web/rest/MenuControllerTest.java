package com.pep.restaurant.ms.bff.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pep.restaurant.ms.bff.RestaurantMsBffApplication;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.web.api.model.MenuDTO;
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
public class MenuControllerTest {

    @Autowired
    private MenuController menuController;

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
    public void passingMenuIdToController_GetMenu() {

        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/menu/1")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(menuDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<MenuDTO> result = menuController.getMenu(1L);
        Assertions.assertEquals(menuDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(menuDTO.getLanguage(), result.getBody().getLanguage());
    }

    @Test
    public void passingMenuToController_CreateMenu() {

        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/menu")))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(menuDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<MenuDTO> result = menuController.createMenu(menuDTO);
        Assertions.assertEquals(menuDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(menuDTO.getLanguage(), result.getBody().getLanguage());
    }

    @Test
    public void passingMenuIdAndAMenuToController_EditMenu() {

        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/menu/1")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(menuDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<MenuDTO> result = menuController.editMenu(1L, menuDTO);
        Assertions.assertEquals(menuDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(menuDTO.getLanguage(), result.getBody().getLanguage());
    }

    @Test
    public void passingMenuIdToController_DeleteMenu() {

        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/menu/1")))
                    .andExpect(method(HttpMethod.DELETE))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(menuDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<MenuDTO> result = menuController.deleteMenu(1L);
        Assertions.assertEquals(menuDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(menuDTO.getLanguage(), result.getBody().getLanguage());
    }

    @Test
    public void checkMenuListRetrievedAfterControllerCall() {

        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        List<MenuDTO> expected = Collections.singletonList(menuDTO);

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/menu")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(expected))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<List<MenuDTO>> result = menuController.getAllMenus();
        Assertions.assertEquals(menuDTO.getId(), result.getBody().get(0).getId());
        Assertions.assertEquals(menuDTO.getLanguage(), result.getBody().get(0).getLanguage());
    }
}
