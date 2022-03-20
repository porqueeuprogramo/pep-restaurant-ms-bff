package com.pep.restaurant.ms.bff.client;

import com.pep.restaurant.ms.bff.client.HereUtil.HereUtil;
import com.pep.restaurant.ms.bff.config.ApplicationProperties;
import com.pep.restaurant.ms.bff.domain.HereRestaurantInfo;
import com.pep.restaurant.ms.bff.logging.Logger;
import com.pep.restaurant.ms.bff.logging.enumeration.LogTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class HereClient {

    private static final Logger LOGGER = new Logger(HereClient.class);
    private final HereUtil hereUtil;
    private final RestTemplate restTemplate;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public HereClient(final HereUtil hereUtil,
                      final RestTemplate restTemplate, final ApplicationProperties applicationProperties) {
        this.hereUtil = hereUtil;
        this.restTemplate = restTemplate;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Get Restaurant Info List Near to a coordinate from Here provider.
     * @param lat latitude.
     * @param lng longitude.
     * @return Here Restaurant Info List.
     */
    public List<HereRestaurantInfo> getRestaurantsNearTheCoordinate(final String lat, final String lng){
        List<HereRestaurantInfo> hereRestaurantInfoList = new ArrayList<>();

        final String url = applicationProperties.getHere().getHerePlacesApiUrl() + "?at=" + lat + "," + lng;

        //get here token by here credentials
        final String token = "Bearer " + hereUtil.getHereAccessToken();

        //request restaurants close to a coordinate from api here
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        final HttpEntity requestHereProvider = new HttpEntity(headers);

        LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.HERE, LogTag.RESTAURANTS, LogTag.RETRIEVED),
                "Get restaurants near to a coordinate from Here Provider");

        try{
            final ResponseEntity<String> responseRestaurantsNearCoordinate = restTemplate.exchange(url, HttpMethod.GET,
                    requestHereProvider,
                    String.class);
            if(HttpStatus.OK.equals(responseRestaurantsNearCoordinate.getStatusCode())){
                //get here restaurant info list extracted from json
                hereRestaurantInfoList =
                        hereUtil.getHereRestaurantInfoList(Objects.requireNonNull(
                                        responseRestaurantsNearCoordinate.getBody()));
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.HERE, LogTag.RESTAURANTS, LogTag.RETRIEVED),
                    "Here not retrieve restaurants near to a coordinate");
        }
        return hereRestaurantInfoList;
    }

}
