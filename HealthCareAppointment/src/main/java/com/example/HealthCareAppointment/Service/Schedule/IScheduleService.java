package com.example.HealthCareAppointment.Service.Schedule;

import java.util.List;

import com.example.HealthCareAppointment.Model.Schedule;
import com.example.HealthCareAppointment.Request.ScheduleRequest;

public interface IScheduleService {
    public Schedule addSchedule(ScheduleRequest scheduleRequest);
    public Schedule getScheduleById(Long id);
    public List<Schedule> getAllSchedule();

}
