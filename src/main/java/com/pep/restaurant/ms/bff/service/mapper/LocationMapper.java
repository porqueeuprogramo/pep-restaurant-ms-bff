package com.pep.restaurant.ms.bff.service.mapper;

import com.pep.restaurant.ms.bff.domain.Address;
import com.pep.restaurant.ms.bff.domain.Coordinate;
import com.pep.restaurant.ms.bff.domain.Location;
import com.pep.restaurant.ms.bff.web.api.model.AddressDTO;
import com.pep.restaurant.ms.bff.web.api.model.CoordinateDTO;
import com.pep.restaurant.ms.bff.web.api.model.LocationDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationMapper {

    /**
     * Map location list to location list DTO.
     * @param locationList location list.
     * @return Location List DTO.
     */
    public List<LocationDTO> mapLocationListToLocationDTOList(final List<Location> locationList) {
        return locationList != null
                ? locationList
                .stream()
                .map(this::mapLocationToLocationDTO)
                .collect(Collectors.toList())
                : null;
    }

    /**
     * Map Location DTO List to Location List.
     * @param locationDTOList location DTO List.
     * @return List of Restaurants.
     */
    public List<Location> mapLocationDTOListToLocationList(final List<LocationDTO> locationDTOList) {
        return locationDTOList != null
                ? locationDTOList
                .stream()
                .map(this::mapLocationDTOToLocation)
                .collect(Collectors.toList())
                : null;
    }

    /**
     * Map Location to Location DTO.
     * @param location location.
     * @return LocationDTO.
     */
    public LocationDTO mapLocationToLocationDTO(final Location location) {
        return location != null ?
                new LocationDTO()
                        .id(location.getId())
                        .address(mapAddressToAddressDTO(location.getAddress()))
                        .locationCoordinate(mapCoordinateToCoordinateDTO(location.getCoordinate()))
                : null;
    }

    /**
     * Map location DTO to location.
     * @param locationDTO location DTO.
     * @return Location.
     */
    public Location mapLocationDTOToLocation(final LocationDTO locationDTO) {
        return locationDTO != null ?
                new Location()
                        .id(locationDTO.getId())
                        .address(mapAddressDTOToAddress(locationDTO.getAddress()))
                        .coordinate(mapCoordinateDTOToCoordinate(locationDTO.getLocationCoordinate()))
                : null;
    }

    /**
     * Map Address to Address DTO.
     * @param address address.
     * @return AddressDTO.
     */
    public AddressDTO mapAddressToAddressDTO(final Address address) {
        return address != null ?
                new AddressDTO()
                        .id(address.getId())
                        .name(address.getName())
                        .postalCode(address.getPostalCode())
                        .city(address.getCity())
                        .country(address.getCountry())
                : null;
    }

    /**
     * Map address DTO to address.
     * @param addressDTO address DTO.
     * @return Address.
     */
    public Address mapAddressDTOToAddress(final AddressDTO addressDTO) {
        return addressDTO != null ?
                new Address()
                        .id(addressDTO.getId())
                        .name(addressDTO.getName())
                        .postalCode(addressDTO.getPostalCode())
                        .city(addressDTO.getCity())
                        .country(addressDTO.getCountry())
                : null;
    }

    /**
     * Map Coordinate to Coordinate DTO.
     * @param coordinate coordinate.
     * @return CoordinateDTO.
     */
    public CoordinateDTO mapCoordinateToCoordinateDTO(final Coordinate coordinate) {
        return coordinate != null ?
                new CoordinateDTO()
                        .latitude(coordinate.getLatitude())
                        .longitude(coordinate.getLongitude())
                : null;
    }

    /**
     * Map coordinate DTO to coordinate.
     * @param coordinateDTO coordinate DTO.
     * @return Coordinate.
     */
    public Coordinate mapCoordinateDTOToCoordinate(final CoordinateDTO coordinateDTO) {
        return coordinateDTO != null ?
                new Coordinate()
                        .latitude(coordinateDTO.getLatitude())
                        .longitude(coordinateDTO.getLongitude())
                : null;
    }

}
