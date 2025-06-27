package com.example.HealthCareAppointment.Request;

import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.Data;




@Data
public class ScheduleRequest {
    
    private Long id;

    private LocalTime startTime;

    private LocalTime endTime;

    private DayOfWeek dayOfWeek;

    private boolean isAvailable; // check xem lịch còn trống không
}
