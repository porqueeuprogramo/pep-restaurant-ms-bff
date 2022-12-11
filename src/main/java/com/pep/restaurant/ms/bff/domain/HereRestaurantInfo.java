package com.pep.restaurant.ms.bff.domain;

public class HereRestaurantInfo {
    private String hereId;
    private Location searchLocation;
    private long distance;
    private String name;
    private ScheduleRoutine schedule;
    private Location location;

    /**
     * Get here id.
     * @return here id.
     */
    public String getHereId() {
        return hereId;
    }

    /**
     * Set here id.
     * @param hereId here id.
     */
    public void setHereId(final String hereId) {
        this.hereId = hereId;
    }

    /**
     * Builder HereRestaurantInfo for here id.
     * @param hereId here id to build.
     * @return hereRestaurantInfo with hereId.
     */
    public HereRestaurantInfo hereId(final String hereId){
        this.hereId = hereId;
        return this;
    }

    /**
     * Get search location.
     * @return search location.
     */
    public Location getSearchLocation() {
        return searchLocation;
    }

    /**
     * Set search location.
     * @param searchLocation search location.
     */
    public void setSearchLocation(final Location searchLocation) {
        this.searchLocation = searchLocation;
    }

    /**
     * Builder HereRestaurantInfo for searchLocation.
     * @param searchLocation searchLocation to build.
     * @return hereRestaurantInfo with searchLocation.
     */
    public HereRestaurantInfo searchLocation(final Location searchLocation){
        this.searchLocation = searchLocation;
        return this;
    }

    /**
     * Get location.
     * @return location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set location.
     * @param location location.
     */
    public void setLocation(final Location location) {
        this.location = location;
    }

    /**
     * Builder HereRestaurantInfo for location.
     * @param location location to build.
     * @return hereRestaurantInfo with location.
     */
    public HereRestaurantInfo location(final Location location){
        this.location = location;
        return this;
    }

    /**
     * Get distance.
     * @return distance.
     */
    public long getDistance() {
        return distance;
    }

    /**
     * Set distance.
     * @param distance distance.
     */
    public void setDistance(final long distance) {
        this.distance = distance;
    }

    /**
     * Builder HereRestaurantInfo for distance.
     * @param distance distance to build.
     * @return hereRestaurantInfo with distance.
     */
    public HereRestaurantInfo distance(final long distance){
        this.distance = distance;
        return this;
    }


    /**
     * Get restaurant name.
     * @return restaurant name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set restaurant name.
     * @param name restaurant name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Builder HereRestaurantInfo for name.
     * @param name name to build.
     * @return hereRestaurantInfo with name.
     */
    public HereRestaurantInfo name(final String name){
        this.name = name;
        return this;
    }

    /**
     * Get schedule.
     * @return schedule.
     */
    public ScheduleRoutine getSchedule() {
        return schedule;
    }

    /**
     * Set schedule.
     * @param schedule schedule.
     */
    public void setSchedule(final ScheduleRoutine schedule) {
        this.schedule = schedule;
    }

    /**
     * Builder HereRestaurantInfo for schedule.
     * @param schedule schedule to build.
     * @return hereRestaurantInfo with schedule.
     */
    public HereRestaurantInfo schedule(final ScheduleRoutine schedule){
        this.schedule = schedule;
        return this;
    }

    @Override
    public String toString() {
        return "HereRestaurantInfo{" +
                "hereId='" + hereId + '\'' +
                ", searchLocation=" + searchLocation +
                ", distance=" + distance +
                ", name='" + name + '\'' +
                ", schedule=" + schedule +
                ", location=" + location +
                '}';
    }
}
