package com.example.HealthCareAppointment.Request;

import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.Data;


@Data
public class ScheduleRequest {
    
    private Long doctorId;

    private LocalTime startTime;

    private LocalTime endTime;

    private DayOfWeek dayOfWeek;

    // mặc định là false
    private boolean isAvailable = false; // check xem lịch còn trống không
}
