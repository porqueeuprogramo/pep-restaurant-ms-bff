package com.pep.restaurant.ms.bff.domain;

import java.util.HashSet;
import java.util.Set;

public class Restaurant {
    private long id;
    private String name;
    private String location;
    private int capacity;
    private Menu menu;
    private Set<Employee> employeeList = new HashSet<>();

    /**
     * Get Restaurant id.
     * @return restaurant id.
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
     * Builder Restaurant for id.
     * @param id id to build.
     * @return restaurant with id.
     */
    public Restaurant id(final long id){
        this.id = id;
        return this;
    }

    /**
     * Get Restaurant name.
     * @return restaurant name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set Restaurant name.
     * @param name restaurant name.
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
     * Get Restaurant Location.
     * @return restaurant location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set Restaurant Location.
     * @param location restaurant location.
     */
    public void setLocation(final String location) {
        this.location = location;
    }

    /**
     * Builder Restaurant for location
     * @param location location to build.
     * @return restaurant with location.
     */
    public Restaurant location(final String location){
        this.location = location;
        return this;
    }

    /**
     * Get restaurant capacity.
     * @return restaurant capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Set restaurant capacity.
     * @param capacity restaurant capacity.
     */
    public void setCapacity(final int capacity) {
        this.capacity = capacity;
    }

    /**
     * Builder for restaurant capacity.
     * @param capacity capacity to build.
     * @return restaurant with capacity.
     */
    public Restaurant capacity(final int capacity){
        this.capacity = capacity;
        return this;
    }

    /**
     * Get restaurant menu.
     * @return menu.
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Set restaurant menu
     * @param menu restaurant menu.
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

    /**
     * Method to add Employee to Restaurant.
     * @param employee employee.
     */
    public void addEmployee(final Employee employee) {
        this.employeeList.add(employee);
        employee.getRestaurantList().add(this);
    }

    /**
     * Method to remove Employee from Restaurant.
     * @param employee employee.
     */
    public void removeEmployee(final Employee employee) {
        this.employeeList.remove(employee);
        employee.getRestaurantList().remove(this);
    }

}
