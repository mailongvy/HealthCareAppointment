package com.example.HealthCareAppointment.Service.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HealthCareAppointment.Enum.AppointmentStatus;
import com.example.HealthCareAppointment.Exception.ResourceNotFoundException;
import com.example.HealthCareAppointment.Exception.TimeException;
import com.example.HealthCareAppointment.Model.Appointment;
import com.example.HealthCareAppointment.Model.Doctor;
import com.example.HealthCareAppointment.Model.Patient;
import com.example.HealthCareAppointment.Repositories.AppointmentRepository;
import com.example.HealthCareAppointment.Repositories.DoctorRepository;
import com.example.HealthCareAppointment.Repositories.PatientRepository;
import com.example.HealthCareAppointment.Request.AppointmentRequest;
import com.example.HealthCareAppointment.Service.Schedule.ScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {
    @Autowired
    private final AppointmentRepository appointmentRepository;

    @Autowired
    private final DoctorRepository doctorRepository;

    @Autowired
    private final PatientRepository patientRepository;

    @Autowired
    private final ScheduleService scheduleService;



    

    @Override
    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Appointment Not found"));
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(Long doctorId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return appointmentRepository.findByDoctorIdAndAppointmentDateBetween(doctorId, startOfDay, endOfDay);
        
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        List<Appointment> appointment = appointmentRepository.findByPatientId(patientId);

        return appointment;
    }

    @Override
    public Appointment createAppointment(AppointmentRequest appointmentRequest) {
        Patient patient = patientRepository.findById(appointmentRequest.getPatientId())
                                            .orElseThrow(() -> new ResourceNotFoundException("Patient Not found"));
        Doctor doctor = doctorRepository.findById(appointmentRequest.getDoctorId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Doctor Not Found"));
        
        if (!scheduleService.isDoctorAvailable(appointmentRequest.getDoctorId(), appointmentRequest.getAppointmentDate())) {
            throw new TimeException("Doctor is not available at this time");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
        appointment.setReason(appointmentRequest.getReason());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setCreateAt(LocalDateTime.now());

        appointment = appointmentRepository.save(appointment);

        

        

        return appointment;




    }

    @Override
    public Appointment confirmAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                                                        .orElseThrow(() -> new ResourceNotFoundException("Appointment Not Found"));
        
        appointment.setStatus(AppointmentStatus.CONFIRMED);


        appointment = appointmentRepository.save(appointment);

        // send sms notification
        

        

        return appointment;
    }

    @Override
    public Appointment cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                                                        .orElseThrow(() -> new ResourceNotFoundException("Appointment Not Found"));
        
        appointment.setStatus(AppointmentStatus.CANCELED);
        return appointmentRepository.save(appointment);
    }

    

}
