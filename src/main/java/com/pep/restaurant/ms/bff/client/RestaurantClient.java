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
    public Restaurant getRestaurant(final long restaurantId){
        final String url =  applicationProperties.getMsManager().getUrl().concat(RESTAURANT_RESTAURANT_ID);
        RestaurantDTO restaurantDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestGetRestaurantId = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("restaurantId", String.valueOf(restaurantId));

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
        RestaurantDTO restaurantDTO = null;

        final RestaurantDTO requestRestaurantDTO = restaurantMapper.mapRestaurantToRestaurantDTO(restaurant);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<RestaurantDTO> requestCreateRestaurantClient = new HttpEntity(requestRestaurantDTO ,headers);

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
    public Restaurant editRestaurant(final long restaurantId, final Restaurant restaurant){
        final String url =  applicationProperties.getMsManager().getUrl().concat(RESTAURANT_RESTAURANT_ID);
        RestaurantDTO restaurantDTO = null;

        final RestaurantDTO requestRestaurantDTO = restaurantMapper.mapRestaurantToRestaurantDTO(restaurant);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<RestaurantDTO> requestEditRestaurantClient = new HttpEntity(requestRestaurantDTO ,headers);
        final Map<String, String> params = new HashMap<>();
        params.put("restaurantId", String.valueOf(restaurantId));

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
    public Restaurant addEmployee(final Long restaurantId, final Long employeeId) {
        final String url =  applicationProperties.getMsManager().getUrl()
                .concat(RESTAURANT_ADD_EMPLOYEE_RESTAURANT_ID_EMPLOYEE_ID);
        RestaurantDTO restaurantDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestAddEmployeeToRestaurantClient = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("restaurantId", String.valueOf(restaurantId));
        params.put("employeeId", String.valueOf(employeeId));

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
    public Restaurant removeEmployee(final Long restaurantId, final Long employeeId) {
        final String url =  applicationProperties.getMsManager().getUrl()
                .concat(RESTAURANT_REMOVE_EMPLOYEE_RESTAURANT_ID_EMPLOYEE_ID);
        RestaurantDTO restaurantDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestAddEmployeeToRestaurantClient = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("restaurantId", String.valueOf(restaurantId));
        params.put("employeeId", String.valueOf(employeeId));

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
    public Restaurant deleteRestaurant(final Long restaurantId) {
        final String url =  applicationProperties.getMsManager().getUrl().concat(RESTAURANT_RESTAURANT_ID);
        RestaurantDTO restaurantDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestGetRestaurantId = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("restaurantId", String.valueOf(restaurantId));

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
        List<RestaurantDTO> restaurantDTOList = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestGetRestaurantId = new HttpEntity(headers);

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
