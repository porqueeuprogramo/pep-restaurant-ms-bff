package com.pep.restaurant.ms.bff.web.rest;

import com.pep.restaurant.ms.bff.service.ScheduleService;
import com.pep.restaurant.ms.bff.service.mapper.ScheduleMapper;
import com.pep.restaurant.ms.bff.web.api.ScheduleApi;
import com.pep.restaurant.ms.bff.web.api.model.ScheduleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
public class ScheduleController implements ScheduleApi, ApiController {

    public static final String SCHEDULE_ADD_EMPLOYEE_SCHEDULE_ID_EMPLOYEE_ID =
            "/schedule/add/employee/{scheduleId}/{employeeId}";
    public static final String SCHEDULE_REMOVE_EMPLOYEE_SCHEDULE_ID_EMPLOYEE_ID =
            "/schedule/remove/employee/{scheduleId}/{employeeId}";
    public static final String SCHEDULE_SCHEDULE_ID = "/schedule/{scheduleId}";
    public static final String SCHEDULE = "/schedule";
    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;


    /**
     * Constructor for Schedule Controller.
     *
     * @param scheduleService Schedule Service.
     * @param scheduleMapper  Schedule Mapper.
     */
    public ScheduleController(final ScheduleService scheduleService, final ScheduleMapper scheduleMapper) {
        this.scheduleService = scheduleService;
        this.scheduleMapper = scheduleMapper;
    }

    /**
     * Controller to get a schedule by id.
     *
     * @param scheduleId id of schedule to get.
     * @return ScheduleDTO with the provided id.
     */

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = SCHEDULE_SCHEDULE_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<ScheduleDTO> getSchedule(final Long scheduleId) {
        return ResponseEntity.ok(scheduleMapper.mapScheduleToScheduleDTO(
                scheduleService.getSchedule(scheduleId)));
    }

    /**
     * Controller to create a schedule.
     *
     * @param scheduleDTO scheduleDTO to create.
     * @return ScheduleDTO created.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(value = SCHEDULE,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody final ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok(scheduleMapper.mapScheduleToScheduleDTO(
                scheduleService.createSchedule(scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO))));
    }

    /**
     * Controller to edit a schedule.
     *
     * @param scheduleId schedule to be edited.
     * @param scheduleDTO scheduleDTO new data.
     * @return ScheduleDTO created.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(value = SCHEDULE_SCHEDULE_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<ScheduleDTO> editSchedule(final Long scheduleId,
                                                        @RequestBody final ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok(scheduleMapper.mapScheduleToScheduleDTO(
                scheduleService.editSchedule(scheduleId,
                        scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO))));

    }

    /**
     * Controller to add employee to schedule.
     *
     * @param scheduleId schedule id.
     * @param employeeId employee id.
     * @return ScheduleDTO with employee added.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(value = SCHEDULE_ADD_EMPLOYEE_SCHEDULE_ID_EMPLOYEE_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<ScheduleDTO> addEmployeeToSchedule(final Long scheduleId, final Long employeeId) {
        return ResponseEntity.ok(scheduleMapper.mapScheduleToScheduleDTO(
                scheduleService.addEmployee(scheduleId, employeeId)));
    }

    /**
     * Controller to remove employee from schedule.
     *
     * @param scheduleId schedule id.
     * @param employeeId employee id.
     * @return ScheduleDTO with employee removed.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(value = SCHEDULE_REMOVE_EMPLOYEE_SCHEDULE_ID_EMPLOYEE_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<ScheduleDTO> removeEmployeeFromSchedule(final Long scheduleId, final Long employeeId) {
        return ResponseEntity.ok(scheduleMapper.mapScheduleToScheduleDTO(
                scheduleService.removeEmployee(scheduleId, employeeId)));
    }

    /**
     * Controller to delete schedule by id.
     * @param scheduleId schedule Id.
     * @return Schedule deleted.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping(value = SCHEDULE_SCHEDULE_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<ScheduleDTO> deleteSchedule(final Long scheduleId) {
        return ResponseEntity.ok(scheduleMapper.mapScheduleToScheduleDTO(
                scheduleService.deleteSchedule(scheduleId)));
    }

    /**
     * Controller to Get all schedules.
     * @return Schedules List.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = SCHEDULE,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        return ResponseEntity.ok(scheduleMapper.mapScheduleListToScheduleDTOList(
                scheduleService.getAllSchedules()));
    }
}
