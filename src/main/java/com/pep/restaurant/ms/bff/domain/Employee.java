package com.pep.restaurant.ms.bff.domain;

import java.util.HashSet;
import java.util.Set;

public class Employee {
    private String uid;
    private String role;
    private Set<Restaurant> restaurantList = new HashSet<>();
    private ScheduleRoutine schedule;

    /**
     * Get uid employee.
     * @return employee uid.
     */
    public String getUid() {
        return uid;
    }

    /**
     * Set id employee.
     * @param uid employee uid.
     */
    public void setUid(final String uid) {
        this.uid = uid;
    }

    /**
     * Builder Employee for uid.
     * @param uid uid to build.
     * @return employee with uid.
     */
    public Employee uid(final String uid){
        this.uid = uid;
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
     * Get employee restaurantDTO list.
     * @return employee restaurantDTO List.
     */
    public Set<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    /**
     * Set employee restaurantDTO list.
     * @param restaurantList employee restaurantDTO list.
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
     * Get employee schedule.
     * @return employee schedule.
     */
    public ScheduleRoutine getSchedule() {
        return schedule;
    }

    /**
     * Set employee schedule.
     * @param schedule employee schedule.
     */
    public void setSchedule(final ScheduleRoutine schedule) {
        this.schedule = schedule;
    }

    /**
     * Builder Employee for schedule.
     * @param schedule schedule to build.
     * @return employee with schedule.
     */
    public Employee schedule(final ScheduleRoutine schedule){
        this.schedule = schedule;
        return this;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "uid=" + uid +
                ", role='" + role + '\'' +
                ", restaurantList=" + restaurantList +
                ", schedule=" + schedule +
                '}';
    }
}
