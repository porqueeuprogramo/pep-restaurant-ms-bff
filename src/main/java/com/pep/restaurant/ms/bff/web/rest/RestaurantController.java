package com.pep.restaurant.ms.bff.web.rest;

import com.pep.restaurant.ms.bff.service.HereService;
import com.pep.restaurant.ms.bff.service.RestaurantService;
import com.pep.restaurant.ms.bff.service.mapper.RestaurantMapper;
import com.pep.restaurant.ms.bff.web.api.RestaurantApi;
import com.pep.restaurant.ms.bff.web.api.model.RestaurantDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
public class RestaurantController implements RestaurantApi, ApiController {

    public static final String RESTAURANT_RESTAURANT_ID = "/restaurant/{restaurantId}";
    public static final String RESTAURANT = "/restaurant";
    public static final String RESTAURANT_PERSIST_LATITUDE_LONGITUDE = "/restaurant/{lat}/{lng}";
    public static final String RESTAURANT_ADD_EMPLOYEE_RESTAURANT_ID_EMPLOYEE_ID =
            "/restaurant/add/employee/{restaurantId}/{employeeId}";
    public static final String RESTAURANT_REMOVE_EMPLOYEE_RESTAURANT_ID_EMPLOYEE_ID =
            "/restaurant/remove/employee/{restaurantId}/{employeeId}";
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final HereService hereService;


    /**
     * Constructor for Restaurant Controller.
     *  @param restaurantService Restaurant Service.
     * @param restaurantMapper  Restaurant Mapper.
     * @param hereService Here Service.
     */
    public RestaurantController(final RestaurantService restaurantService, final RestaurantMapper restaurantMapper,
                                final HereService hereService) {
        this.restaurantService = restaurantService;
        this.restaurantMapper = restaurantMapper;
        this.hereService = hereService;
    }

    /**
     * Controller to get a restaurant by id.
     *
     * @param restaurantId id of restaurant to get.
     * @return RestaurantDTO with the provided id.
     */

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = RESTAURANT_RESTAURANT_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<RestaurantDTO> getRestaurant(final Long restaurantId) {
        return ResponseEntity.ok(restaurantMapper.mapRestaurantToRestaurantDTO(
                restaurantService.getRestaurant(restaurantId)));
    }

    /**
     * Controller to create a restaurant.
     *
     * @param restaurantDTO restaurantDTO to create.
     * @return RestaurantDTO created.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(value = RESTAURANT,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody final RestaurantDTO restaurantDTO) {
        return ResponseEntity.ok(restaurantMapper.mapRestaurantToRestaurantDTO(
                restaurantService.createRestaurant(restaurantMapper.mapRestaurantDTOToRestaurant(restaurantDTO))));
    }

    /**
     * Controller to edit a restaurant.
     *
     * @param restaurantId restaurant to be edited.
     * @param restaurantDTO restaurantDTO new data.
     * @return RestaurantDTO created.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(value = RESTAURANT_RESTAURANT_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<RestaurantDTO> editRestaurant(final Long restaurantId,
                                                        @RequestBody final RestaurantDTO restaurantDTO) {
        return ResponseEntity.ok(restaurantMapper.mapRestaurantToRestaurantDTO(
                restaurantService.editRestaurant(restaurantId,
                        restaurantMapper.mapRestaurantDTOToRestaurant(restaurantDTO))));

    }

    /**
     * Controller to add employee to restaurant.
     *
     * @param restaurantId restaurant id.
     * @param employeeId employee id.
     * @return RestaurantDTO with employee added.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(value = RESTAURANT_ADD_EMPLOYEE_RESTAURANT_ID_EMPLOYEE_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<RestaurantDTO> addEmployee(final Long restaurantId, final Long employeeId) {
        return ResponseEntity.ok(restaurantMapper.mapRestaurantToRestaurantDTO(
                restaurantService.addEmployee(restaurantId, employeeId)));
    }

    /**
     * Controller to remove employee from restaurant.
     *
     * @param restaurantId restaurant id.
     * @param employeeId employee id.
     * @return RestaurantDTO with employee removed.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(value = RESTAURANT_REMOVE_EMPLOYEE_RESTAURANT_ID_EMPLOYEE_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<RestaurantDTO> removeEmployee(final Long restaurantId, final Long employeeId) {
        return ResponseEntity.ok(restaurantMapper.mapRestaurantToRestaurantDTO(
                restaurantService.removeEmployee(restaurantId, employeeId)));
    }

    /**
     * Controller to delete restaurant by id.
     * @param restaurantId restaurant Id.
     * @return Restaurant deleted.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping(value = RESTAURANT_RESTAURANT_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<RestaurantDTO> deleteRestaurant(final Long restaurantId) {
        return ResponseEntity.ok(restaurantMapper.mapRestaurantToRestaurantDTO(
                restaurantService.deleteRestaurant(restaurantId)));
    }

    /**
     * Controller to Get all restaurants.
     * @return Restaurants List.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = RESTAURANT,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantMapper.mapRestaurantListToRestaurantDTOList(
                restaurantService.getAllRestaurants()));
    }

    /**
     * Controller to Persist Restaurants Near the Coordinate.
     * @param lat latitude.
     * @param lng longitude.
     * @return Restaurant DTO List.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = RESTAURANT_PERSIST_LATITUDE_LONGITUDE,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<List<RestaurantDTO>> persistRestaurantsNearTheCoordinate(final String lat, final String lng) {
        return ResponseEntity.ok(restaurantMapper.mapRestaurantListToRestaurantDTOList(
                hereService.persistRestaurantsNearTheCoordinate(lat, lng)));
    }
}
