package com.pep.restaurant.ms.bff.domain;

import java.util.HashSet;
import java.util.Set;

public class Restaurant {
    private long id;
    private String name;
    private String hereId;
    private Location location;
    private int capacity;
    private Menu menu;
    private ScheduleRoutine schedule;
    private Set<Employee> employeeList = new HashSet<>();

    /**
     * Method to get a Restaurant id.
     * @return id.
     */
    public long getId() {
        return id;
    }

    /**
     * Method to set a Restaurant id.
     * @param id to be set.
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Builder Restaurant for id.
     * @param id id to build.
     * @return restaurant with id.
     */
    public Restaurant id(final long id){
        this.id = id;
        return this;
    }

    /**
     * Get Restaurant here id.
     * @return restaurant here id.
     */
    public String getHereId() {
        return hereId;
    }

    /**
     * Set Restaurant here id.
     * @param hereId restaurant id.
     */
    public void setHereId(final String hereId) {
        this.hereId = hereId;
    }

    /**
     * Builder Restaurant for here id.
     * @param hereId here id to build.
     * @return restaurant with id.
     */
    public Restaurant hereId(final String hereId){
        this.hereId = hereId;
        return this;
    }

    /**
     * Method to get a Restaurant name.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set a Restaurant name.
     * @param name name to be set.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Builder Restaurant for name.
     * @param name name to build.
     * @return restaurant with name.
     */
    public Restaurant name(final String name){
        this.name = name;
        return this;
    }

    /**
     * Method to get a Restaurant Location.
     * @return location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Method to set a Restaurant location.
     * @param location location to be set.
     */
    public void setLocation(final Location location) {
        this.location = location;
    }

    /**
     * Builder Restaurant for location
     * @param location location to build.
     * @return restaurant with location.
     */
    public Restaurant location(final Location location){
        this.location = location;
        return this;
    }

    /**
     * Method to get a Restaurant Schedule.
     * @return schedule.
     */
    public ScheduleRoutine getSchedule() {
        return schedule;
    }

    /**
     * Method to set a Restaurant schedule.
     * @param schedule schedule to be set.
     */
    public void setSchedule(final ScheduleRoutine schedule) {
        this.schedule = schedule;
    }

    /**
     * Builder Restaurant for schedule
     * @param schedule schedule to build.
     * @return restaurant with schedule.
     */
    public Restaurant schedule(final ScheduleRoutine schedule){
        this.schedule = schedule;
        return this;
    }

    /**
     * Method to get Restaurant capacity.
     * @return capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Method to set Restaurant capacity.
     * @param capacity to be set.
     */
    public void setCapacity(final int capacity) {
        this.capacity = capacity;
    }

    /**
     * Builder Restaurant for capacity.
     * @param capacity capacity to build.
     * @return restaurant with capacity.
     */
    public Restaurant capacity(final int capacity){
        this.capacity = capacity;
        return this;
    }

    /**
     * Method to get Restaurant menu.
     * @return menu.
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Method to set Restaurant menu.
     * @param menu to be set.
     */
    public void setMenu(final Menu menu) {
        this.menu = menu;
    }

    /**
     * Builder Restaurant for menu.
     * @param menu menu to build.
     * @return restaurant with menu.
     */
    public Restaurant menu(final Menu menu){
        this.menu = menu;
        return this;
    }

    /**
     * Method to get Restaurant employee list.
     * @return employee list.
     */
    public Set<Employee> getEmployeeList() {
        return employeeList;
    }

    /**
     * Method to set Restaurant employee List
     * @param employeeList employee list.
     */
    public void setEmployeeList(final Set<Employee> employeeList){
        this.employeeList = employeeList;
    }

    /**
     * Builder Restaurant for employeeList.
     * @param employeeList employeeList to build.
     * @return restaurant with employeeList.
     */
    public Restaurant employeeList(final Set<Employee> employeeList){
        this.employeeList = employeeList;
        return this;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hereId='" + hereId + '\'' +
                ", location=" + location +
                ", capacity=" + capacity +
                ", menu=" + menu +
                ", schedule=" + schedule +
                ", employeeList=" + employeeList +
                '}';
    }
}
