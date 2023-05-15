package com.pep.restaurant.ms.bff.client;

import com.pep.restaurant.ms.bff.config.ApplicationProperties;
import com.pep.restaurant.ms.bff.domain.Restaurant;
import com.pep.restaurant.ms.bff.logging.Logger;
import com.pep.restaurant.ms.bff.logging.enumeration.LogTag;
import com.pep.restaurant.ms.bff.service.mapper.RestaurantMapper;
import com.pep.restaurant.ms.bff.web.api.model.RestaurantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Component
public class RestaurantClient {

    private static final Logger LOGGER = new Logger(RestaurantClient.class);
    public static final String RESTAURANT_RESTAURANT_ID = "/api/restaurant/{restaurantId}";
    public static final String RESTAURANT = "/api/restaurant";
    public static final String RESTAURANT_ADD_EMPLOYEE_RESTAURANT_ID_EMPLOYEE_ID =
            "/api/restaurant/add/employee/{restaurantId}/{employeeId}";
    public static final String RESTAURANT_REMOVE_EMPLOYEE_RESTAURANT_ID_EMPLOYEE_ID =
            "/api/restaurant/remove/employee/{restaurantId}/{employeeId}";
    private final RestTemplate restTemplate;
    private final RestaurantMapper restaurantMapper;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public RestaurantClient(final RestTemplate restTemplate,
                            final RestaurantMapper restaurantMapper,
                            final ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.restaurantMapper = restaurantMapper;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Client to get Restaurant by id.
     * @param restaurantId restaurant id.
     * @return restaurant.
     */
    public Restaurant getRestaurant(final String restaurantId){
        final String url =  applicationProperties.getMsManager().getUrl().concat(RESTAURANT_RESTAURANT_ID);
        final String correlationId = UUID.randomUUID().toString();
        RestaurantDTO restaurantDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity requestGetRestaurantId = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("restaurantId", restaurantId);

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.RESTAURANTS, LogTag.RETRIEVED),
                "Get Restaurant by id: " + restaurantId);

        try{
            final ResponseEntity<RestaurantDTO> responseRestaurant = restTemplate.exchange(url, HttpMethod.GET,
                    requestGetRestaurantId,
                    new ParameterizedTypeReference<RestaurantDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseRestaurant.getStatusCode())){
                restaurantDTO = responseRestaurant.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.RESTAURANTS, LogTag.RETRIEVED),
                    "Restaurant id not found");
        }

        return restaurantMapper.mapRestaurantDTOToRestaurant(restaurantDTO);
    }

    /**
     * Client to create restaurant.
     * @param restaurant restaurant.
     * @return restaurant.
     */
    public Restaurant createRestaurant(final Restaurant restaurant){
        final String url =  applicationProperties.getMsManager().getUrl().concat(RESTAURANT);
        final String correlationId = UUID.randomUUID().toString();
        RestaurantDTO restaurantDTO = null;

        final RestaurantDTO requestRestaurantDTO = restaurantMapper.mapRestaurantToRestaurantDTO(restaurant);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity<RestaurantDTO> requestCreateRestaurantClient = new HttpEntity(requestRestaurantDTO ,headers);

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.RESTAURANTS, LogTag.PERSISTED),
                "Create Restaurant: " + restaurant.toString());

        try{
            final ResponseEntity<RestaurantDTO> responseRestaurantCreated = restTemplate.exchange(url, HttpMethod.POST,
                    requestCreateRestaurantClient, RestaurantDTO.class);
            if(HttpStatus.OK.equals(responseRestaurantCreated.getStatusCode())){
                restaurantDTO = responseRestaurantCreated.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.RESTAURANTS, LogTag.PERSISTED)
                    , "Restaurant was not created!!");
        }

        return restaurantMapper.mapRestaurantDTOToRestaurant(restaurantDTO);
    }

    /**
     * Client to edit restaurant.
     * @param restaurantId restaurant id.
     * @param restaurant restaurant.
     * @return restaurant.
     */
    public Restaurant editRestaurant(final String restaurantId, final Restaurant restaurant){
        final String url =  applicationProperties.getMsManager().getUrl().concat(RESTAURANT_RESTAURANT_ID);
        final String correlationId = UUID.randomUUID().toString();
        RestaurantDTO restaurantDTO = null;

        final RestaurantDTO requestRestaurantDTO = restaurantMapper.mapRestaurantToRestaurantDTO(restaurant);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity<RestaurantDTO> requestEditRestaurantClient = new HttpEntity(requestRestaurantDTO ,headers);
        final Map<String, String> params = new HashMap<>();
        params.put("restaurantId", restaurantId);

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.RESTAURANTS, LogTag.EDITED),
                "Edit Restaurant by id " + restaurantId);

        try{
            final ResponseEntity<RestaurantDTO> responseRestaurantEdited = restTemplate.exchange(url, HttpMethod.PUT,
                    requestEditRestaurantClient, RestaurantDTO.class, params);
            if(HttpStatus.OK.equals(responseRestaurantEdited.getStatusCode())){
                restaurantDTO = responseRestaurantEdited.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.RESTAURANTS, LogTag.EDITED)
                    , "Restaurant was not edited!!");
        }

        return restaurantMapper.mapRestaurantDTOToRestaurant(restaurantDTO);
    }

    /**
     * Client to add employee to restaurant.
     * @param restaurantId restaurant id.
     * @param employeeId employee id.
     * @return restaurant.
     */
    public Restaurant addEmployee(final String restaurantId, final String employeeId) {
        final String url =  applicationProperties.getMsManager().getUrl()
                .concat(RESTAURANT_ADD_EMPLOYEE_RESTAURANT_ID_EMPLOYEE_ID);
        final String correlationId = UUID.randomUUID().toString();
        RestaurantDTO restaurantDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity requestAddEmployeeToRestaurantClient = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("restaurantId", restaurantId);
        params.put("employeeId", employeeId);

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.RESTAURANTS, LogTag.EDITED),
                "Add Employee with id: " + employeeId + " to Restaurant with id: " + restaurantId);

        try{
            final ResponseEntity<RestaurantDTO> responseRestaurantWithEmployeeAdded = restTemplate.exchange(url,
                    HttpMethod.PUT,
                    requestAddEmployeeToRestaurantClient, new ParameterizedTypeReference<RestaurantDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseRestaurantWithEmployeeAdded.getStatusCode())){
                restaurantDTO = responseRestaurantWithEmployeeAdded.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.RESTAURANTS, LogTag.EDITED)
                    , "Employee was not added to Restaurant!!");
        }

        return restaurantMapper.mapRestaurantDTOToRestaurant(restaurantDTO);
    }

    /**
     * Client to remove employee from restaurant.
     * @param restaurantId restaurant id.
     * @param employeeId employee id.
     * @return restaurant.
     */
    public Restaurant removeEmployee(final String restaurantId, final String employeeId) {
        final String url =  applicationProperties.getMsManager().getUrl()
                .concat(RESTAURANT_REMOVE_EMPLOYEE_RESTAURANT_ID_EMPLOYEE_ID);
        final String correlationId = UUID.randomUUID().toString();
        RestaurantDTO restaurantDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity requestAddEmployeeToRestaurantClient = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("restaurantId", restaurantId);
        params.put("employeeId", employeeId);

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.RESTAURANTS, LogTag.EDITED),
                "Remove Employee with id: " + employeeId + " from Restaurant with id: " + restaurantId);

        try{
            final ResponseEntity<RestaurantDTO> responseRestaurantWithEmployeeAdded = restTemplate.exchange(url,
                    HttpMethod.PUT,
                    requestAddEmployeeToRestaurantClient, new ParameterizedTypeReference<RestaurantDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseRestaurantWithEmployeeAdded.getStatusCode())){
                restaurantDTO = responseRestaurantWithEmployeeAdded.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.RESTAURANTS, LogTag.EDITED)
                    , "Employee was not removed from Restaurant!!");
        }

        return restaurantMapper.mapRestaurantDTOToRestaurant(restaurantDTO);
    }

    /**
     * Client to delete restaurant.
     * @param restaurantId restaurant id.
     * @return restaurant deleted.
     */
    public Restaurant deleteRestaurant(final String restaurantId) {
        final String url =  applicationProperties.getMsManager().getUrl().concat(RESTAURANT_RESTAURANT_ID);
        final String correlationId = UUID.randomUUID().toString();
        RestaurantDTO restaurantDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity requestGetRestaurantId = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("restaurantId", restaurantId);

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.RESTAURANTS, LogTag.DELETED),
                "Delete Restaurant by id: " + restaurantId);

        try{
            final ResponseEntity<RestaurantDTO> responseRestaurant = restTemplate.exchange(url, HttpMethod.DELETE,
                    requestGetRestaurantId,
                    new ParameterizedTypeReference<RestaurantDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseRestaurant.getStatusCode())){
                restaurantDTO = responseRestaurant.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.RESTAURANTS, LogTag.DELETED),
                    "Restaurant id not found");
        }

        return restaurantMapper.mapRestaurantDTOToRestaurant(restaurantDTO);
    }

    /**
     * Client to get all restaurants.
     * @return restaurants list.
     */
    public List<Restaurant> getAllRestaurants() {
        final String url =  applicationProperties.getMsManager().getUrl().concat(RESTAURANT);
        final String correlationId = UUID.randomUUID().toString();
        List<RestaurantDTO> restaurantDTOList = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity requestGetRestaurantId = new HttpEntity(headers);

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.RESTAURANTS, LogTag.RETRIEVED),
                "Get All Restaurants list");

        try{
            final ResponseEntity<List<RestaurantDTO>> responseRestaurant = restTemplate.exchange(url, HttpMethod.GET,
                    requestGetRestaurantId,
                    new ParameterizedTypeReference<List<RestaurantDTO>>() {
                    });
            if(HttpStatus.OK.equals(responseRestaurant.getStatusCode())){
                restaurantDTOList = responseRestaurant.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.RESTAURANTS, LogTag.RETRIEVED),
                    "Restaurant list not found");
        }

        return restaurantMapper.mapRestaurantDTOListToRestaurantList(restaurantDTOList);
    }
}
