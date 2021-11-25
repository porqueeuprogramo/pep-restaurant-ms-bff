package com.pep.restaurant.ms.bff.client;

import com.pep.restaurant.ms.bff.config.ApplicationProperties;
import com.pep.restaurant.ms.bff.domain.Schedule;
import com.pep.restaurant.ms.bff.logging.Logger;
import com.pep.restaurant.ms.bff.logging.enumeration.LogTag;
import com.pep.restaurant.ms.bff.service.mapper.ScheduleMapper;
import com.pep.restaurant.ms.bff.web.api.model.ScheduleDTO;
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
public class ScheduleClient {

    private static final Logger LOGGER = new Logger(ScheduleClient.class);
    public static final String SCHEDULE_ADD_EMPLOYEE_SCHEDULE_ID_EMPLOYEE_ID =
            "/api/schedule/add/employee/{scheduleId}/{employeeId}";
    public static final String SCHEDULE_REMOVE_EMPLOYEE_SCHEDULE_ID_EMPLOYEE_ID =
            "/api/schedule/remove/employee/{scheduleId}/{employeeId}";
    public static final String SCHEDULE_SCHEDULE_ID = "/api/schedule/{scheduleId}";
    public static final String SCHEDULE = "/api/schedule";
    private final RestTemplate restTemplate;
    private final ScheduleMapper scheduleMapper;
    private final ApplicationProperties applicationProperties;

    public ScheduleClient(final RestTemplate restTemplate,
                          final ScheduleMapper scheduleMapper,
                          final ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.scheduleMapper = scheduleMapper;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Client to get Schedule by id.
     * @param scheduleId schedule id.
     * @return schedule.
     */
    public Schedule getSchedule(final long scheduleId){
        final String url =  applicationProperties.getMsManager().getUrl().concat(SCHEDULE_SCHEDULE_ID);
        ScheduleDTO scheduleDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestGetScheduleId = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("scheduleId", String.valueOf(scheduleId));

        try{
            final ResponseEntity<ScheduleDTO> responseSchedule = restTemplate.exchange(url, HttpMethod.GET,
                    requestGetScheduleId,
                    new ParameterizedTypeReference<ScheduleDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseSchedule.getStatusCode())){
                scheduleDTO = responseSchedule.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.SCHEDULES, LogTag.RETRIEVED),
                    "Schedule id not found");
        }

        return scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO);
    }

    /**
     * Client to create schedule.
     * @param schedule schedule.
     * @return schedule.
     */
    public Schedule createSchedule(final Schedule schedule){
        final String url =  applicationProperties.getMsManager().getUrl().concat(SCHEDULE);
        ScheduleDTO scheduleDTO = null;

        final ScheduleDTO requestScheduleDTO = scheduleMapper.mapScheduleToScheduleDTO(schedule);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<ScheduleDTO> requestCreateScheduleClient = new HttpEntity(requestScheduleDTO ,headers);

        try{
            final ResponseEntity<ScheduleDTO> responseScheduleCreated = restTemplate.exchange(url, HttpMethod.POST,
                    requestCreateScheduleClient, ScheduleDTO.class);
            if(HttpStatus.OK.equals(responseScheduleCreated.getStatusCode())){
                scheduleDTO = responseScheduleCreated.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.SCHEDULES, LogTag.PERSISTED)
                    , "Schedule was not created!!");
        }

        return scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO);
    }

    /**
     * Client to edit schedule.
     * @param scheduleId schedule id.
     * @param schedule schedule.
     * @return schedule.
     */
    public Schedule editSchedule(final long scheduleId, final Schedule schedule){
        final String url =  applicationProperties.getMsManager().getUrl().concat(SCHEDULE_SCHEDULE_ID);
        ScheduleDTO scheduleDTO = null;

        final ScheduleDTO requestScheduleDTO = scheduleMapper.mapScheduleToScheduleDTO(schedule);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<ScheduleDTO> requestEditScheduleClient = new HttpEntity(requestScheduleDTO ,headers);
        final Map<String, String> params = new HashMap<>();
        params.put("scheduleId", String.valueOf(scheduleId));

        try{
            final ResponseEntity<ScheduleDTO> responseScheduleEdited = restTemplate.exchange(url, HttpMethod.PUT,
                    requestEditScheduleClient, ScheduleDTO.class, params);
            if(HttpStatus.OK.equals(responseScheduleEdited.getStatusCode())){
                scheduleDTO = responseScheduleEdited.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.SCHEDULES, LogTag.EDITED)
                    , "Schedule was not edited!!");
        }

        return scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO);
    }

    /**
     * Client to add employee to schedule.
     * @param scheduleId schedule id.
     * @param employeeId employee id.
     * @return schedule.
     */
    public Schedule addEmployee(final Long scheduleId, final Long employeeId) {
        final String url =  applicationProperties.getMsManager().getUrl()
                .concat(SCHEDULE_ADD_EMPLOYEE_SCHEDULE_ID_EMPLOYEE_ID);
        ScheduleDTO scheduleDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestAddEmployeeToScheduleClient = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("scheduleId", String.valueOf(scheduleId));
        params.put("employeeId", String.valueOf(employeeId));

        try{
            final ResponseEntity<ScheduleDTO> responseScheduleWithEmployeeAdded = restTemplate.exchange(url,
                    HttpMethod.PUT,
                    requestAddEmployeeToScheduleClient, new ParameterizedTypeReference<ScheduleDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseScheduleWithEmployeeAdded.getStatusCode())){
                scheduleDTO = responseScheduleWithEmployeeAdded.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.SCHEDULES, LogTag.EDITED)
                    , "Employee was not added to Schedule!!");
        }

        return scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO);
    }

    /**
     * Client to remove employee from schedule.
     * @param scheduleId schedule id.
     * @param employeeId employee id.
     * @return schedule.
     */
    public Schedule removeEmployee(final Long scheduleId, final Long employeeId) {
        final String url =  applicationProperties.getMsManager().getUrl()
                .concat(SCHEDULE_REMOVE_EMPLOYEE_SCHEDULE_ID_EMPLOYEE_ID);
        ScheduleDTO scheduleDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestAddEmployeeToScheduleClient = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("scheduleId", String.valueOf(scheduleId));
        params.put("employeeId", String.valueOf(employeeId));

        try{
            final ResponseEntity<ScheduleDTO> responseScheduleWithEmployeeAdded = restTemplate.exchange(url,
                    HttpMethod.PUT,
                    requestAddEmployeeToScheduleClient, new ParameterizedTypeReference<ScheduleDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseScheduleWithEmployeeAdded.getStatusCode())){
                scheduleDTO = responseScheduleWithEmployeeAdded.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.SCHEDULES, LogTag.EDITED)
                    , "Employee was not removed from Schedule!!");
        }

        return scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO);
    }

    /**
     * Client to delete schedule.
     * @param scheduleId schedule id.
     * @return schedule deleted.
     */
    public Schedule deleteSchedule(final Long scheduleId) {
        final String url =  applicationProperties.getMsManager().getUrl().concat(SCHEDULE_SCHEDULE_ID);
        ScheduleDTO scheduleDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestGetScheduleId = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("scheduleId", String.valueOf(scheduleId));

        try{
            final ResponseEntity<ScheduleDTO> responseSchedule = restTemplate.exchange(url, HttpMethod.DELETE,
                    requestGetScheduleId,
                    new ParameterizedTypeReference<ScheduleDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseSchedule.getStatusCode())){
                scheduleDTO = responseSchedule.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.SCHEDULES, LogTag.DELETED),
                    "Schedule id not found");
        }

        return scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO);
    }

    /**
     * Client to get all schedules.
     * @return schedules list.
     */
    public List<Schedule> getAllSchedules() {
        final String url =  applicationProperties.getMsManager().getUrl().concat(SCHEDULE);
        List<ScheduleDTO> scheduleDTOList = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestGetScheduleId = new HttpEntity(headers);

        try{
            final ResponseEntity<List<ScheduleDTO>> responseSchedule = restTemplate.exchange(url, HttpMethod.GET,
                    requestGetScheduleId,
                    new ParameterizedTypeReference<List<ScheduleDTO>>() {
                    });
            if(HttpStatus.OK.equals(responseSchedule.getStatusCode())){
                scheduleDTOList = responseSchedule.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.SCHEDULES, LogTag.RETRIEVED),
                    "Schedule list not found");
        }

        return scheduleMapper.mapScheduleDTOListToScheduleList(scheduleDTOList);
    }
}
