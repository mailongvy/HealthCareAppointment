package com.example.HealthCareAppointment.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HealthCareAppointment.Model.Schedule;
import com.example.HealthCareAppointment.Request.ScheduleRequest;
import com.example.HealthCareAppointment.Response.ApiResponse;
import com.example.HealthCareAppointment.Service.Schedule.IScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${apiPrefix}/schedules")
@RequiredArgsConstructor
public class ScheduleController {   
    private final IScheduleService scheduleService;

    @GetMapping("/schedule/all")
    public ResponseEntity<ApiResponse> getAllSchedule() {
        List<Schedule> list = scheduleService.getAllSchedule();
        return ResponseEntity.ok(new ApiResponse("Found", list));
    }

    @PostMapping("/schedule/add")
    public ResponseEntity<ApiResponse> addSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        try {
            Schedule schedule = scheduleService.addSchedule(scheduleRequest);
            return ResponseEntity.ok(new ApiResponse("Add Schedule Success", schedule));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


}
