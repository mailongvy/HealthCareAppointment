package com.example.HealthCareAppointment.Service.Schedule;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.HealthCareAppointment.Exception.ResourceNotFoundException;
import com.example.HealthCareAppointment.Exception.TimeException;
import com.example.HealthCareAppointment.Model.Schedule;
import com.example.HealthCareAppointment.Repositories.DoctorRepository;
import com.example.HealthCareAppointment.Repositories.ScheduleRepository;
import com.example.HealthCareAppointment.Request.ScheduleRequest;
import com.example.HealthCareAppointment.Service.Doctor.IDoctorService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;
    private final IDoctorService doctorService;
    
    // check the condition to add schedule for doctor
    private void validateScheduleRequest(ScheduleRequest request) {
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new TimeException("Start time must be before end time");
        }
        if (!doctorRepository.existsById(request.getDoctorId())) {    
            throw new ResourceNotFoundException("Not Found Exception");
        }
    }

    @Override
    public Schedule addSchedule(ScheduleRequest scheduleRequest) {
        validateScheduleRequest(scheduleRequest);
        Schedule schedule = createSchedule(scheduleRequest);

        return scheduleRepository.save(schedule);
    }

    /*
     * public Schedule(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek, boolean isAvailable, Doctor doctor) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.isAvailable = isAvailable;
        this.doctor = doctor;
    }
     */
    public Schedule createSchedule(ScheduleRequest request) {
        return new Schedule(
            request.getStartTime(),
            request.getEndTime(),
            request.getDayOfWeek(),
            true,
            doctorService.getDoctorById(request.getDoctorId())
        );
    }

    @Override
    public List<Schedule> getAllSchedule() {
        // TODO Auto-generated method stub
        
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule getScheduleById(Long id) {
        // TODO Auto-generated method stub
        return scheduleRepository.findById(id)
                                    .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
    }

    @Override
    @Transactional
    public void deleteScheduleById(Long id) {
        // TODO Auto-generated method stub
        scheduleRepository.findById(id)
                            .ifPresentOrElse(
                                scheduleRepository::delete,
                                () -> { throw new ResourceNotFoundException("Schedule Not Found"); }
                            );
        
    }

    @Override
    public boolean isDoctorAvailable(Long doctorId, LocalDateTime appointmentDateTime) {
        DayOfWeek dayOfWeek = appointmentDateTime.getDayOfWeek();
        LocalTime appointmentTime = appointmentDateTime.toLocalTime();

        List<Schedule> schedules = scheduleRepository.findByDoctorIdAndDayOfWeek(doctorId, dayOfWeek);

        for (Schedule schedule : schedules) {
            if (schedule.isAvailable() && (appointmentTime.isAfter(schedule.getStartTime())) 
                && (appointmentTime.isBefore(appointmentTime))
            ) {
                return true;
            }
        }

        return  false;

    }
    
    

    


    


    
}
