package com.pep.restaurant.ms.bff.domain;

public class Location {
    private long id;
    private Address address;
    private Coordinate coordinate;

    /**
     * Get Location id.
     * @return location id.
     */
    public long getId() {
        return id;
    }

    /**
     * Set Restaurant Id.
     * @param id restaurant id.
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Builder Location for id.
     * @param id id to build.
     * @return location with id.
     */
    public Location id(final long id){
        this.id = id;
        return this;
    }

    /**
     * Get Location Address.
     * @return Location Address.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Set Location Address.
     * @param address location address.
     */
    public void setAddress(final Address address) {
        this.address = address;
    }

    /**
     * Builder Location for address.
     * @param address address to build.
     * @return location with address.
     */
    public Location address(final Address address){
        this.address = address;
        return this;
    }

    /**
     * Get Location Coordinate.
     * @return Location Coordinate.
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Set Location Coordinate.
     * @param coordinate location coordinate.
     */
    public void setCoordinate(final Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Builder Location for coordinate.
     * @param coordinate coordinate to build.
     * @return location with coordinate.
     */
    public Location coordinate(final Coordinate coordinate){
        this.coordinate = coordinate;
        return this;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", address=" + address +
                ", coordinate=" + coordinate +
                '}';
    }
}
