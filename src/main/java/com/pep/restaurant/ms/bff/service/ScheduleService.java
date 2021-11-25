package com.pep.restaurant.ms.bff.service;

import com.pep.restaurant.ms.bff.client.ScheduleClient;
import com.pep.restaurant.ms.bff.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleClient scheduleClient;

    @Autowired
    public ScheduleService(final ScheduleClient scheduleClient) {
        this.scheduleClient = scheduleClient;
    }

    /**
     * Call get schedule client.
     * @param scheduleId schedule id.
     * @return schedule.
     */
    public Schedule getSchedule(final long scheduleId) {
        return scheduleClient.getSchedule(scheduleId);
    }

    /**
     * Call create schedule client.
     * @param schedule schedule to create.
     * @return schedule.
     */
    public Schedule createSchedule(final Schedule schedule) {
        return scheduleClient.createSchedule(schedule);
    }

    /**
     * Call edit schedule client.
     * @param scheduleId schedule id to edit.
     * @param schedule schedule to be edited
     * @return schedule edited.
     */
    public Schedule editSchedule(final Long scheduleId, final Schedule schedule) {
        return scheduleClient.editSchedule(scheduleId, schedule);
    }

    /**
     * Call add employee to schedule.
     * @param scheduleId schedule id.
     * @param employeeId employee id.
     * @return schedule with employee added.
     */
    public Schedule addEmployee(final Long scheduleId, final Long employeeId) {
        return scheduleClient.addEmployee(scheduleId, employeeId);
    }

    /**
     * Call remove employee from schedule.
     * @param scheduleId schedule id.
     * @param employeeId employee id.
     * @return schedule with employee removed.
     */
    public Schedule removeEmployee(final Long scheduleId, final Long employeeId) {
        return scheduleClient.removeEmployee(scheduleId, employeeId);
    }

    /**
     * Call remove schedule.
     * @param scheduleId schedule id.
     * @return Schedule Deleted.
     */
    public Schedule deleteSchedule(final Long scheduleId) {
        return scheduleClient.deleteSchedule(scheduleId);
    }

    /**
     * Call Get all schedules.
     * @return Schedule List.
     */
    public List<Schedule> getAllSchedules() {
        return scheduleClient.getAllSchedules();
    }
}
