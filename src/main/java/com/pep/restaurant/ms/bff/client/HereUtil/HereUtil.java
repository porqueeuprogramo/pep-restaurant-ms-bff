package com.pep.restaurant.ms.bff.client.HereUtil;

import com.here.account.http.apache.ApacheHttpClientProvider;
import com.here.account.oauth2.AccessTokenResponse;
import com.here.account.oauth2.ClientCredentialsGrantRequest;
import com.here.account.oauth2.HereAccount;
import com.here.account.oauth2.TokenEndpoint;
import com.pep.restaurant.ms.bff.config.HereConfig;
import com.pep.restaurant.ms.bff.domain.HereRestaurantInfo;
import com.pep.restaurant.ms.bff.domain.ScheduleRoutine;
import com.pep.restaurant.ms.bff.domain.ScheduleTime;
import com.pep.restaurant.ms.bff.domain.Address;
import com.pep.restaurant.ms.bff.domain.Coordinate;
import com.pep.restaurant.ms.bff.domain.Location;
import com.pep.restaurant.ms.bff.logging.Logger;
import com.pep.restaurant.ms.bff.logging.enumeration.LogTag;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Collection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class HereUtil {

    private static final Logger LOGGER = new Logger(HereUtil.class);
    public static final int ADDRESS_ATTRIBUTES_SIZE = 4;
    public static final int ADDRESS_SPLITTER_INITIAL_VALUE = -1;
    public static final int ADDRESS_COUNTRY = 3;
    private final HereConfig hereConfig;

    public HereUtil(final HereConfig hereConfig) {
        this.hereConfig = hereConfig;
    }

    /**
     * Get Here Restaurant Info List from here api.
     * @param restaurantsNearCoordinate restaurants near to a coordinate response from here api.
     * @return Here Restaurant Info.
     */
    public List<HereRestaurantInfo> getHereRestaurantInfoList(final String restaurantsNearCoordinate) {

        final JSONObject restaurantsJson = new JSONObject(restaurantsNearCoordinate);

        final JSONArray restaurantsJsonResultList = restaurantsJson
                .getJSONObject("results")
                .getJSONArray("items");

        final JSONObject restaurantsSearchLocation = restaurantsJson
                .getJSONObject("search")
                .getJSONObject("context")
                .getJSONObject("location");

        final String restaurantsSearchAddress = restaurantsSearchLocation
                .getJSONObject("address")
                .get("text").toString();

        final String position = restaurantsSearchLocation.get("position").toString();
        final JSONArray positionArray = new JSONArray(position);
        final String searchLocationLatitude = positionArray.get(0).toString();
        final String searchLocationLongitude = positionArray.get(1).toString();

        return IntStream
                .range(0, restaurantsJsonResultList.length())
                .mapToObj(restaurantItem -> getHereRestaurantInfo(restaurantItem,
                        restaurantsJsonResultList,
                        restaurantsSearchAddress,
                        searchLocationLatitude,
                        searchLocationLongitude))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    /**
     * Map Here Restaurant Api attributes to Here Restaurant Info.
     * @param restaurantItem restaurant item of here api.
     * @param jsonResultList json restaurant result list.
     * @param restaurantsSearchAddress restaurant address searched.
     * @param searchLocationLatitude restaurant searched location latitude.
     * @param searchLocationLongitude restaurant searched location longitude.
     * @return Here Restaurant Info.
     */
    private HereRestaurantInfo getHereRestaurantInfo(final int restaurantItem,
                                                     final JSONArray jsonResultList,
                                                     final String restaurantsSearchAddress,
                                                     final String searchLocationLatitude,
                                                     final String searchLocationLongitude) {

        final HereRestaurantInfo hereRestaurantInfo = new HereRestaurantInfo();
        final String longitude;
        final String latitude;
        final String address;
        final String title;
        final String distance;
        final String hereId;

        final String categoryString = jsonResultList
                .getJSONObject(restaurantItem)
                .get("category").toString();

        final JSONObject categoryObject = new JSONObject(categoryString);
        final String category = categoryObject.get("id").toString();

        if (category.equals("restaurant")) {
            final JSONObject item = jsonResultList.getJSONObject(restaurantItem);
            distance = item.get("distance").toString();
            title = item.get("title").toString();
            address = item.get("vicinity").toString();
            hereId = item.get("id").toString();

            final ScheduleRoutine scheduleRoutine = getScheduleRoutineFromOpeningHours(item);

            final Address restaurantsAddressTransformed = buildAddress(address);
            final Address restaurantsSearchAddressTransformed = buildAddress(restaurantsSearchAddress);

            final String position = item.get("position").toString();
            final JSONArray positionArray = new JSONArray(position);
            latitude = positionArray.get(0).toString();
            longitude = positionArray.get(1).toString();

            return buildHereRestaurantInfo(
                    hereId,
                    restaurantsSearchAddressTransformed,
                    searchLocationLatitude,
                    searchLocationLongitude,
                    hereRestaurantInfo,
                    longitude,
                    latitude,
                    scheduleRoutine,
                    restaurantsAddressTransformed,
                    title,
                    distance);
        }
        return null;
    }

    private ScheduleRoutine getScheduleRoutineFromOpeningHours(final JSONObject item) {
        final Object openingHoursObj;
        try{
            openingHoursObj = item.get("openingHours");
        }catch(JSONException e){
            //log
            LOGGER.info(Arrays.asList(LogTag.JSON, LogTag.PROPERTY),
                    "Opening Hours Json Property not found!!!");
            return new ScheduleRoutine()
                    .daysScheduleMap(new HashMap<>());
        }
        final JSONObject jsonObjectOpeningHours = new JSONObject(openingHoursObj.toString());
        final String schedule = jsonObjectOpeningHours.get("text").toString();
        return buildScheduleRoutine(schedule);
    }

    private Address buildAddress(final String addressTest) {

        final AtomicInteger atom = new AtomicInteger(ADDRESS_SPLITTER_INITIAL_VALUE);
        final List<String> addressSplitted = Arrays
                .stream(addressTest.split("<br/>"))
                .map(position -> splitPostalCodeFromCity(position, atom.incrementAndGet()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return addressSplitted.size() != ADDRESS_ATTRIBUTES_SIZE ? getAddressBuilder(addressSplitted)
                : getAddressBuilder(addressSplitted)
                .country(addressSplitted.get(ADDRESS_COUNTRY));

    }

    private Address getAddressBuilder(final List<String> addressSplitted) {
        return new Address()
                .name(addressSplitted.get(0))
                .city(addressSplitted.get(2))
                .postalCode(addressSplitted.get(1));
    }

    /**
     * Method to get a Schedule Routine Object.
     * @param schedule schedule string of api response
     * @return Schedule Routine for a restaurant.
     */
    private ScheduleRoutine buildScheduleRoutine(final String schedule) {
        //Split schedule for each day interval.
        final List<List<String>> scheduleSplitted = Arrays.stream(schedule.split("<br/>"))
                .map(this::splitByWeekDay)
                .collect(Collectors.toList());

        //Split schedule week days for the same schedule
        final List<List<String>> scheduleSplittedWithScheduleOfSameWeekDays = scheduleSplitted
                .stream()
                .map(this::splitIfSameScheduleForSameWeekDays)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        //Split schedule with intervals of week days.
        final List<List<DayOfWeek>> dayOfWeekScheduleList = scheduleSplittedWithScheduleOfSameWeekDays
                .stream()
                .map(this::splitScheduleWeek)
                .collect(Collectors.toList());

        //Schedule for all week days of restaurant splitted by schedule time interval.
        final List<List<ScheduleTime>> scheduleTimeListFilled = new ArrayList<>();
        scheduleSplittedWithScheduleOfSameWeekDays
                .forEach(scheduleTime -> splitScheduleTime(scheduleTime, scheduleTimeListFilled));

        final Map<DayOfWeek, List<ScheduleTime>> daysScheduleMap = new HashMap<>();

        //Prepare Schedule Routine object with all week days relative to schedule intervals.
        IntStream
                .range(0, dayOfWeekScheduleList.size())
                .forEach(schedulePosition ->
                        addWeekDayAndScheduleToDayScheduleList(
                                dayOfWeekScheduleList.get(schedulePosition),
                                scheduleTimeListFilled,
                                schedulePosition,
                                daysScheduleMap));

        return new ScheduleRoutine()
                .daysScheduleMap(daysScheduleMap);
    }

    /**
     * Split schedule with same shecule time for one week day.
     * @param scheduleForSameWeekDays schedule for same week day.
     * @return List of schedule week day and time.
     */
    private List<List<String>> splitIfSameScheduleForSameWeekDays(final List<String> scheduleForSameWeekDays) {
        final List<String> scheduleWeekDays = Arrays.asList(scheduleForSameWeekDays.get(0).split(", "));
        return scheduleWeekDays
                .stream()
                .map(scheduleWeekDay -> addScheduleToWeekDay(scheduleWeekDay, scheduleForSameWeekDays.get(1)))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Add Schedule to week day.
     * @param scheduleWeekDay schedule week day.
     * @param scheduleWeekHour schedule week hour.
     * @return list of schedule.
     */
    private List<List<String>> addScheduleToWeekDay(final String scheduleWeekDay, final String scheduleWeekHour) {
        final List<List<String>> scheduleWithWeekDayAdded = new ArrayList<>();
        scheduleWithWeekDayAdded.add(Arrays.asList(scheduleWeekDay, scheduleWeekHour));
        return scheduleWithWeekDayAdded;
    }

    private List<String> splitPostalCodeFromCity(final String position, final int atom) {
        return atom == 1
                ? Arrays.asList(position.split(" ", 2))
                : List.of(position);
    }

    /**
     * Add Week day and schedule to a day schedule list.
     * @param dayOfWeeks day of weeks.
     * @param scheduleTimes schedule times.
     * @param schedulePosition schedule position.
     * @param daysScheduleMap day schedule map.
     * @return Map with a relation of day of week and list of schedule time.
     */
    private Map<DayOfWeek, List<ScheduleTime>> addWeekDayAndScheduleToDayScheduleList(
            final List<DayOfWeek> dayOfWeeks,
            final List<List<ScheduleTime>> scheduleTimes,
            final int schedulePosition,
            final Map<DayOfWeek, List<ScheduleTime>> daysScheduleMap) {
        dayOfWeeks
                .forEach(dayOfWeek -> mapDayOfWeekAndScheduleTimesToDayScheduleList(
                        dayOfWeek,
                        scheduleTimes.get(schedulePosition),
                        daysScheduleMap));

        return daysScheduleMap;
    }

    /**
     * Save with a relation of day of week and list of schedule time.
     * @param dayOfWeek day of week.
     * @param scheduleTime schedule time.
     * @param daysScheduleMap day schedule map.
     * @return Map with a relation of day of week and list of schedule time.
     */
    private Map<DayOfWeek, List<ScheduleTime>> mapDayOfWeekAndScheduleTimesToDayScheduleList(
            final DayOfWeek dayOfWeek,
            final List<ScheduleTime> scheduleTime,
            final Map<DayOfWeek, List<ScheduleTime>> daysScheduleMap) {
        daysScheduleMap.put(dayOfWeek, scheduleTime);
        return daysScheduleMap;
    }

    /**
     * Split schedule time.
     * @param scheduleTime schedule time.
     * @param scheduleTimeListFilled schedule time list filled.
     * @return List of schedule time.
     */
    private List<List<ScheduleTime>> splitScheduleTime(final List<String> scheduleTime,
                                                       final List<List<ScheduleTime>> scheduleTimeListFilled) {
        final List<String> scheduleHoursList = Arrays.asList(scheduleTime.get(1).split(", "));

        final List<ScheduleTime> scheduleTimeList = scheduleHoursList
                .stream()
                .map(this::buildScheduleTime)
                .collect(Collectors.toList());

        scheduleTimeListFilled.add(scheduleTimeList);

        return scheduleTimeListFilled;
    }

    /**
     * Build Schedule Time by schedule time hour string.
     * @param scheduleTime schedule time (hour string).
     * @return Schedule Time.
     */
    private ScheduleTime buildScheduleTime(final String scheduleTime) {

        final List<String> scheduleHoursList = Arrays.asList(scheduleTime.split(" - "));

        final List<List<String>> scheduleTimeList = scheduleHoursList
                .stream()
                .map(time -> Arrays.asList(time.split(":")))
                .collect(Collectors.toList());

        return new ScheduleTime()
                .startTime(LocalTime.of(Integer.parseInt(scheduleTimeList
                                .get(0)
                                .get(0)),
                        Integer.parseInt(scheduleTimeList
                                .get(0)
                                .get(1))))
                .endTime(LocalTime.of(
                        Integer.parseInt(scheduleTimeList
                                .get(1)
                                .get(0)),
                        Integer.parseInt(scheduleTimeList
                                .get(1)
                                .get(1))));
    }

    /**
     * Split Schedule Week day.
     * @param scheduleWeek schedule week.
     * @return day of week list.
     */
    private List<DayOfWeek> splitScheduleWeek(final List<String> scheduleWeek) {
        final DayOfWeek firstWeekDayEnum;
        final List<String> weekDaysList = Arrays.asList(scheduleWeek.get(0).split("-"));
        firstWeekDayEnum = mapHereWeekDayToWeekDay(weekDaysList.get(0));

        if(weekDaysList.size() == 1) {
            return Collections.singletonList(firstWeekDayEnum);
        }
        final DayOfWeek lastWeekDayEnum = mapHereWeekDayToWeekDay(weekDaysList.get(1));
        final int firstWeekDayValue = firstWeekDayEnum.getValue();
        final int lastWeekDayValue = lastWeekDayEnum.getValue();

        return IntStream
                .range(firstWeekDayValue, lastWeekDayValue + 1)
                .mapToObj(DayOfWeek::of)
                .collect(Collectors.toList());
    }

    /**
     * Trim schedule element.
     * @param scheduleElement schedule element with day of week and time.
     * @return List of schedule week and time.
     */
    private List<String> splitByWeekDay(final String scheduleElement) {
        return Arrays.asList(scheduleElement.split(": "));
    }

    /**
     * Build Here Restaurant Info.
     * @param hereId restaurant here id.
     * @param restaurantsSearchAddressTransformed restaurant searched Address.
     * @param searchLocationLatitude restaurant searched Location latitude.
     * @param searchLocationLongitude restaurant searched location longitude
     * @param hereRestaurantInfo here Restaurant Info.
     * @param longitude restaurant result longitude.
     * @param latitude restaurant result latitude.
     * @param scheduleRoutine restaurant result schedule.
     * @param restaurantsAddressTransformed restaurant result address.
     * @param title restaurant result title.
     * @param distance restaurant result distance.
     * @return Here Restaurant Info.
     */
    private HereRestaurantInfo buildHereRestaurantInfo( final String hereId,
                                                        final Address restaurantsSearchAddressTransformed,
                                                        final String searchLocationLatitude,
                                                        final String searchLocationLongitude,
                                                        final HereRestaurantInfo hereRestaurantInfo,
                                                        final String longitude,
                                                        final String latitude,
                                                        final ScheduleRoutine scheduleRoutine,
                                                        final Address restaurantsAddressTransformed,
                                                        final String title,
                                                        final String distance) {
        return hereRestaurantInfo
                .hereId(hereId)
                .searchLocation(new Location()
                        .address(restaurantsSearchAddressTransformed)
                        .coordinate(new Coordinate()
                                .latitude(Double.parseDouble(searchLocationLatitude))
                                .longitude(Double.parseDouble(searchLocationLongitude))))
                .distance(Long.parseLong(distance))
                .name(title)
                .schedule(scheduleRoutine)
                .location(new Location()
                        .address(restaurantsAddressTransformed)
                        .coordinate(new Coordinate()
                                .latitude(Double.parseDouble(latitude))
                                .longitude(Double.parseDouble(longitude))));
    }

    /**
     * Get Here Access Token.
     * @return here access token.
     */
    public String getHereAccessToken() {
        final TokenEndpoint tokenEndpoint = HereAccount.getTokenEndpoint(
                ApacheHttpClientProvider.builder().build(),
                hereConfig.getHereClientAuthorizationCredentials());
        final AccessTokenResponse fresh = tokenEndpoint.requestToken(new ClientCredentialsGrantRequest());
        return fresh.getAccessToken();
    }

    private DayOfWeek mapHereWeekDayToWeekDay(final String weekDay){
        DayOfWeek dayOfWeek = null;
        switch(weekDay){
            case "Mon":
                dayOfWeek = DayOfWeek.MONDAY;
                break;
            case "Tue":
                dayOfWeek = DayOfWeek.TUESDAY;
                break;
            case "Wed":
                dayOfWeek = DayOfWeek.WEDNESDAY;
                break;
            case "Thu":
                dayOfWeek = DayOfWeek.THURSDAY;
                break;
            case "Fri":
                dayOfWeek = DayOfWeek.FRIDAY;
                break;
            case "Sat":
                dayOfWeek = DayOfWeek.SATURDAY;
                break;
            case "Sun":
                dayOfWeek = DayOfWeek.SUNDAY;
                break;
            default:
                LOGGER.error(Arrays.asList(LogTag.CLIENT, LogTag.HERE, LogTag.DAYOFWEEK),
                        "Day of week not found!!!");
        }
        return dayOfWeek;
    }
}
