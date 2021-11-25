package com.pep.restaurant.ms.bff.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pep.restaurant.ms.bff.web.api.model.ScheduleType;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private long id;
    private ScheduleType type;
    @JsonManagedReference
    private List<Employee> employeeList = new ArrayList<>();

    /**
     * Get Schedule id.
     * @return schedule id.
     */
    public long getId() {
        return id;
    }

    /**
     * Set schedule id.
     * @param id schedule id.
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Builder Schedule for id.
     * @param id id to build.
     * @return schedule with id.
     */
    public Schedule id(final long id){
        this.id = id;
        return this;
    }

    /**
     * Get schedule type.
     * @return schedule typy enum.
     */
    public ScheduleType getType() {
        return type;
    }

    /**
     * Set schedule type
     * @param type schedule type enum.
     */
    public void setType(final ScheduleType type) {
        this.type = type;
    }

    /**
     * Builder Schedule for type.
     * @param type type to build.
     * @return schedule with type.
     */
    public Schedule type(final ScheduleType type){
        this.type = type;
        return this;
    }

    /**
     * Method to get Schedule employee list.
     * @return Schedule employee list.
     */
    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    /**
     * Method to set Schedule employee List
     * @param employeeList schedule employee list.
     */
    public void setEmployeeList(final List<Employee> employeeList){
        this.employeeList = employeeList;
    }

    /**
     * Builder Schedule for employeeList.
     * @param employeeList employeeList to build.
     * @return schedule with employeeList.
     */
    public Schedule employeeList(final List<Employee> employeeList){
        this.employeeList = employeeList;
        return this;
    }

    /**
     * Method to add Employee to Schedule.
     * @param employee employee.
     */
    public void addEmployee(final Employee employee) {
        employeeList.add(employee);
        employee.setSchedule(this);
    }

    /**
     * Method to remove employee from schedule.
     * @param employee employee.
     */
    public void removeEmployee(final Employee employee) {
        employeeList.remove(employee);
        employee.setSchedule(null);
    }

}


