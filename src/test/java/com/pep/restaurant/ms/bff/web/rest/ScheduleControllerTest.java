package com.pep.restaurant.ms.bff.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pep.restaurant.ms.bff.RestaurantMsBffApplication;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.web.api.model.ScheduleDTO;
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
public class ScheduleControllerTest {

    @Autowired
    private ScheduleController scheduleController;

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
    public void passingScheduleIdToController_GetSchedule() {

        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTOWithEmployeeDTOList();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/schedule/1")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(scheduleDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<ScheduleDTO> result = scheduleController.getSchedule(1L);
        Assertions.assertEquals(scheduleDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(scheduleDTO.getType().name(), result.getBody().getType().name());
        Assertions.assertEquals(scheduleDTO.getEmployeeList().size(), result.getBody().getEmployeeList().size());
    }

    @Test
    public void passingScheduleToController_CreateSchedule() {

        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTOWithEmployeeDTOList();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/schedule")))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(scheduleDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<ScheduleDTO> result = scheduleController.createSchedule(scheduleDTO);
        Assertions.assertEquals(scheduleDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(scheduleDTO.getType().name(), result.getBody().getType().name());
        Assertions.assertEquals(scheduleDTO.getEmployeeList().size(), result.getBody().getEmployeeList().size());
    }

    @Test
    public void passingScheduleIdAndAScheduleToController_EditSchedule() {

        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTOWithEmployeeDTOList();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/schedule/1")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(scheduleDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<ScheduleDTO> result = scheduleController.editSchedule(1L, scheduleDTO);
        Assertions.assertEquals(scheduleDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(scheduleDTO.getType().name(), result.getBody().getType().name());
        Assertions.assertEquals(scheduleDTO.getEmployeeList().size(), result.getBody().getEmployeeList().size());
    }

    @Test
    public void passingScheduleIdAndAnEmployeeIdToController_AddEmployeeToSchedule() {

        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTOWithEmployeeDTOList();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/schedule/add/employee/1/1")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(scheduleDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<ScheduleDTO> result = scheduleController.addEmployeeToSchedule(1L, 1L);
        Assertions.assertEquals(scheduleDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(scheduleDTO.getType().name(), result.getBody().getType().name());
        Assertions.assertEquals(scheduleDTO.getEmployeeList().size(), result.getBody().getEmployeeList().size());
    }

    @Test
    public void passingScheduleIdAndAnEmployeeIdToController_RemoveEmployeeFromSchedule() {

        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTOWithEmployeeDTOList();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/schedule/remove/employee/1/1")))
                    .andExpect(method(HttpMethod.PUT))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(scheduleDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<ScheduleDTO> result = scheduleController.removeEmployeeFromSchedule(1L, 1L);
        Assertions.assertEquals(scheduleDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(scheduleDTO.getType().name(), result.getBody().getType().name());
        Assertions.assertEquals(scheduleDTO.getEmployeeList().size(), result.getBody().getEmployeeList().size());
    }

    @Test
    public void passingScheduleIdToController_DeleteSchedule() {

        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTOWithEmployeeDTOList();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI(
                            "http://localhost:8082/pep/restaurant/manager/api/schedule/1")))
                    .andExpect(method(HttpMethod.DELETE))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(scheduleDTO))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<ScheduleDTO> result = scheduleController.deleteSchedule(1L);
        Assertions.assertEquals(scheduleDTO.getId(), result.getBody().getId());
        Assertions.assertEquals(scheduleDTO.getType().name(), result.getBody().getType().name());
        Assertions.assertEquals(scheduleDTO.getEmployeeList().size(), result.getBody().getEmployeeList().size());
    }

    @Test
    public void checkScheduleListRetrievedAfterControllerCall() {

        ScheduleDTO scheduleDTO = applicationDataProvider.getScheduleDTOWithEmployeeDTOList();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        List<ScheduleDTO> expected = Collections.singletonList(scheduleDTO);

        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:8082/pep/restaurant/manager/api/schedule")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(headers)
                            .body(mapper.writeValueAsString(expected))
                    );
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<List<ScheduleDTO>> result = scheduleController.getAllSchedules();
        Assertions.assertEquals(scheduleDTO.getId(), result.getBody().get(0).getId());
        Assertions.assertEquals(scheduleDTO.getType().name(), result.getBody().get(0).getType().name());
        Assertions.assertEquals(scheduleDTO.getEmployeeList().size(), result.getBody().get(0).getEmployeeList().size());
    }
}
