package com.pep.restaurant.ms.bff.domain;

import java.util.HashSet;
import java.util.Set;

public class Employee {
    private long id;
    private String role;
    private Set<Restaurant> restaurantList = new HashSet<>();
    private Schedule schedule;

    /**
     * Get id employee.
     * @return employee id.
     */
    public long getId() {
        return id;
    }

    /**
     * Set id employee.
     * @param id employee id.
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Builder Employee for id.
     * @param id id to build.
     * @return employee with id.
     */
    public Employee id(final long id){
        this.id = id;
        return this;
    }

    /**
     * Get employee role.
     * @return employee role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Set employee role.
     * @param role employee role.
     */
    public void setRole(final String role) {
        this.role = role;
    }

    /**
     * Builder Employee for role.
     * @param role role to build.
     * @return employee with role.
     */
    public Employee role(final String role){
        this.role = role;
        return this;
    }

    /**
     * Get employee restaurant.
     * @return employee restaurant.
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Set employee schedule.
     * @param schedule employee schedule.
     */
    public void setSchedule(final Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Builder Employee for schedule.
     * @param schedule schedule to build.
     * @return employee with schedule.
     */
    public Employee schedule(final Schedule schedule){
        this.schedule = schedule;
        return this;
    }

    /**
     * Get employee restaurant list.
     * @return employee restaurant List.
     */
    public Set<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    /**
     * Set employee restaurant list.
     * @param restaurantList employee restaurant list.
     */
    public void setRestaurantList(final Set<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    /**
     * Builder Employee for restaurantList.
     * @param restaurantList restaurantList to build.
     * @return employee with restaurantList.
     */
    public Employee restaurantList(final Set<Restaurant> restaurantList){
        this.restaurantList = restaurantList;
        return this;
    }

    /**
     * Method to add Restaurant to Employee.
     * @param restaurant restaurant.
     */
    public void addRestaurant(final Restaurant restaurant) {
        this.restaurantList.add(restaurant);
        restaurant.getEmployeeList().add(this);
    }

    /**
     * Method to remove Restaurant from Employee.
     * @param restaurant restaurant.
     */
    public void removeRestaurant(final Restaurant restaurant) {
        this.restaurantList.remove(restaurant);
        restaurant.getEmployeeList().remove(this);
    }

}
