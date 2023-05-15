package com.pep.restaurant.ms.bff.client.HereUtil;

import com.pep.restaurant.ms.bff.domain.HereRestaurantInfo;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class HereUtilTest {

    @InjectMocks
    HereUtil hereUtil;

    ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void passingAJsonStringWithRestaurantsNearACoordinate_checkHereRestaurantInfoObjectIsTheExpected() {

        //Given
        String restaurantsNearCoordinate = "{\"results\":{\"items\":[{\"position\":[41.109795,-8.621567],\"distance\":13,\"title\":\"Cafe Hamburgo\",\"averageRating\":0.0,\"category\":{\"id\":\"restaurant\",\"title\":\"Restaurant\",\"href\":\"https://places.ls.hereapi.com/places/v1/categories/places/restaurant?app_id=ysc9ZkeR4eUs012qz5wt&app_code=015OwvwTHL6eiF2Cpph9cA\",\"type\":\"urn:nlp-types:category\",\"system\":\"places\"},\"icon\":\"https://download.vcdn.data.here.com/p/d/places2/icons/categories/03.icon\",\"vicinity\":\"Rua Joaquim Lopes Pintor 176<br/>4405-868 Vila Nova de Gaia\",\"having\":[],\"type\":\"urn:nlp-types:place\",\"href\":\"https://places.ls.hereapi.com/places/v1/places/620aabd1-28cfe099c787060e95c8c46d483f795c;context=Zmxvdy1pZD03OWZmZjhjOC00ZDM2LTUzMzItYmE2My1kYTgzM2U3ZGNiNTlfMTY0NzU1NzU2MjI0NV80MTA2XzU5MzAmcmFuaz0w?app_id=ysc9ZkeR4eUs012qz5wt&app_code=015OwvwTHL6eiF2Cpph9cA\",\"tags\":[{\"id\":\"european\",\"title\":\"European\",\"group\":\"cuisine\"},{\"id\":\"portuguese\",\"title\":\"Portuguese\",\"group\":\"cuisine\"}],\"id\":\"620aabd1-28cfe099c787060e95c8c46d483f795c\",\"openingHours\":{\"text\":\"Tue-Sat: 08:00 - 23:00<br/>Sun: 17:00 - 23:00\",\"label\":\"Opening hours\",\"isOpen\":true,\"structured\":[{\"start\":\"T080000\",\"duration\":\"PT15H00M\",\"recurrence\":\"FREQ:DAILY;BYDAY:TU,TU,TU,WE,WE,WE,TH,TH,TH,FR,FR,FR,SA,SA,SA\"},{\"start\":\"T170000\",\"duration\":\"PT06H00M\",\"recurrence\":\"FREQ:DAILY;BYDAY:SU,SU,SU\"}]},\"alternativeNames\":[{\"name\":\"Café Hamburgo\",\"language\":\"pt\"}]},{\"position\":[41.10967,-8.6215],\"distance\":27,\"title\":\"The Rick's Pub\",\"averageRating\":0.0,\"category\":{\"id\":\"restaurant\",\"title\":\"Restaurant\",\"href\":\"https://places.ls.hereapi.com/places/v1/categories/places/restaurant?app_id=ysc9ZkeR4eUs012qz5wt&app_code=015OwvwTHL6eiF2Cpph9cA\",\"type\":\"urn:nlp-types:category\",\"system\":\"places\"},\"icon\":\"https://download.vcdn.data.here.com/p/d/places2/icons/categories/03.icon\",\"vicinity\":\"Rua Joaquim Lopes Pintor<br/>4405-868 Vila Nova de Gaia\",\"having\":[],\"type\":\"urn:nlp-types:place\",\"href\":\"https://places.ls.hereapi.com/places/v1/places/620aabd1-b2e8525e329b00b18e627bc2113146b0;context=Zmxvdy1pZD03OWZmZjhjOC00ZDM2LTUzMzItYmE2My1kYTgzM2U3ZGNiNTlfMTY0NzU1NzU2MjI0NV80MTA2XzU5MzAmcmFuaz0x?app_id=ysc9ZkeR4eUs012qz5wt&app_code=015OwvwTHL6eiF2Cpph9cA\",\"id\":\"620aabd1-b2e8525e329b00b18e627bc2113146b0\",\"openingHours\":{\"text\":\"Mon: 20:45 - 23:59<br/>Tue-Fri: 20:00 - 23:59<br/>Sat: 19:00 - 23:59<br/>Sun: 00:00 - 03:00, 20:00 - 23:59\",\"label\":\"Opening hours\",\"isOpen\":true,\"structured\":[{\"start\":\"T204500\",\"duration\":\"PT03H14M\",\"recurrence\":\"FREQ:DAILY;BYDAY:MO,TU,WE,TH\"},{\"start\":\"T210000\",\"duration\":\"PT02H59M\",\"recurrence\":\"FREQ:DAILY;BYDAY:MO,FR,SA\"},{\"start\":\"T200000\",\"duration\":\"PT03H59M\",\"recurrence\":\"FREQ:DAILY;BYDAY:TU,WE,TH,FR,SU\"},{\"start\":\"T190000\",\"duration\":\"PT04H59M\",\"recurrence\":\"FREQ:DAILY;BYDAY:SA\"},{\"start\":\"T000000\",\"duration\":\"PT03H00M\",\"recurrence\":\"FREQ:DAILY;BYDAY:SU\"}]}},{\"position\":[41.11006,-8.62126],\"distance\":29,\"title\":\"Aurora Dias - Cabeleireiro & Estética\",\"averageRating\":0.0,\"category\":{\"id\":\"service\",\"title\":\"Service\",\"href\":\"https://places.ls.hereapi.com/places/v1/categories/places/service?app_id=ysc9ZkeR4eUs012qz5wt&app_code=015OwvwTHL6eiF2Cpph9cA\",\"type\":\"urn:nlp-types:category\",\"system\":\"places\"},\"icon\":\"https://download.vcdn.data.here.com/p/d/places2/icons/categories/02.icon\",\"vicinity\":\"Rua Joaquim Lopes Pintor 155B<br/>4405-868 Vila Nova de Gaia\",\"having\":[],\"type\":\"urn:nlp-types:place\",\"href\":\"https://places.ls.hereapi.com/places/v1/places/620httek-900321d8855805085f8e45c0cf468577;context=Zmxvdy1pZD03OWZmZjhjOC00ZDM2LTUzMzItYmE2My1kYTgzM2U3ZGNiNTlfMTY0NzU1NzU2MjI0NV80MTA2XzU5MzAmcmFuaz0y?app_id=ysc9ZkeR4eUs012qz5wt&app_code=015OwvwTHL6eiF2Cpph9cA\",\"id\":\"620httek-900321d8855805085f8e45c0cf468577\"}]},\"search\":{\"context\":{\"location\":{\"position\":[41.109909,-8.621536],\"address\":{\"text\":\"Rua Joaquim Lopes Pintor 178<br/>4405-868 Vila Nova de Gaia<br/>Portugal\",\"house\":\"178\",\"street\":\"Rua Joaquim Lopes Pintor\",\"postalCode\":\"4405-868\",\"district\":\"Outeiro\",\"city\":\"Vila Nova de Gaia\",\"county\":\"Porto\",\"country\":\"Portugal\",\"countryCode\":\"PRT\"}},\"type\":\"urn:nlp-types:place\",\"href\":\"https://places.ls.hereapi.com/places/v1/places/loc-dmVyc2lvbj0xO3RpdGxlPVJ1YStKb2FxdWltK0xvcGVzK1BpbnRvcisxNzg7bGF0PTQxLjEwOTkwOTE0MTc7bG9uPS04LjYyMTUzNTY3NTtzdHJlZXQ9UnVhK0pvYXF1aW0rTG9wZXMrUGludG9yO2hvdXNlPTE3ODtjaXR5PVZpbGErTm92YStkZStHYWlhO3Bvc3RhbENvZGU9NDQwNS04Njg7Y291bnRyeT1QUlQ7ZGlzdHJpY3Q9T3V0ZWlybztjb3VudHk9UG9ydG87Y2F0ZWdvcnlJZD1idWlsZGluZztzb3VyY2VTeXN0ZW09aW50ZXJuYWw;context=c2VhcmNoQ29udGV4dD0x?app_id=ysc9ZkeR4eUs012qz5wt&app_code=015OwvwTHL6eiF2Cpph9cA\"}}}";
        HereRestaurantInfo hereRestaurantInfo = applicationDataProvider.getHereRestaurantInfo();

        //When
        List<HereRestaurantInfo> hereRestaurantInfoList = hereUtil.getHereRestaurantInfoList(restaurantsNearCoordinate);

        //Then
        Assertions.assertEquals(hereRestaurantInfo.getHereId(), hereRestaurantInfoList.get(0).getHereId());
        Assertions.assertEquals(hereRestaurantInfo.getSearchLocation().getAddress().getCity(), hereRestaurantInfoList.get(0).getSearchLocation().getAddress().getCity());
        Assertions.assertEquals(hereRestaurantInfo.getSearchLocation().getAddress().getCountry(), hereRestaurantInfoList.get(0).getSearchLocation().getAddress().getCountry());
        Assertions.assertEquals(hereRestaurantInfo.getSearchLocation().getAddress().getPostalCode(), hereRestaurantInfoList.get(0).getSearchLocation().getAddress().getPostalCode());
        Assertions.assertEquals(hereRestaurantInfo.getSearchLocation().getAddress().getName(), hereRestaurantInfoList.get(0).getSearchLocation().getAddress().getName());
        Assertions.assertEquals(hereRestaurantInfo.getSearchLocation().getCoordinate().getLatitude(), hereRestaurantInfoList.get(0).getSearchLocation().getCoordinate().getLatitude());
        Assertions.assertEquals(hereRestaurantInfo.getSearchLocation().getCoordinate().getLongitude(), hereRestaurantInfoList.get(0).getSearchLocation().getCoordinate().getLongitude());
        Assertions.assertEquals(hereRestaurantInfo.getLocation().getAddress().getCity(), hereRestaurantInfoList.get(0).getLocation().getAddress().getCity());
        Assertions.assertEquals(hereRestaurantInfo.getLocation().getAddress().getCountry(), hereRestaurantInfoList.get(0).getLocation().getAddress().getCountry());
        Assertions.assertEquals(hereRestaurantInfo.getLocation().getAddress().getPostalCode(), hereRestaurantInfoList.get(0).getLocation().getAddress().getPostalCode());
        Assertions.assertEquals(hereRestaurantInfo.getLocation().getAddress().getName(), hereRestaurantInfoList.get(0).getLocation().getAddress().getName());
        Assertions.assertEquals(hereRestaurantInfo.getLocation().getCoordinate().getLatitude(), hereRestaurantInfoList.get(0).getLocation().getCoordinate().getLatitude());
        Assertions.assertEquals(hereRestaurantInfo.getLocation().getCoordinate().getLongitude(), hereRestaurantInfoList.get(0).getLocation().getCoordinate().getLongitude());
        Assertions.assertEquals(hereRestaurantInfo.getDistance(), hereRestaurantInfoList.get(0).getDistance());
        Assertions.assertEquals(hereRestaurantInfo.getName(), hereRestaurantInfoList.get(0).getName());
        Assertions.assertEquals(hereRestaurantInfo.getSchedule().getScheduleRoutine().size(), hereRestaurantInfoList.get(0).getSchedule().getScheduleRoutine().size());
    }

}
