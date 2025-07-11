package com.example.HealthCareAppointment.Service.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.example.HealthCareAppointment.Service.Notification.NotificationService;
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

    @Autowired
    private final NotificationService notificationService;


    

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

        // gửi sms cho bệnh nhân 
        String smsContent = String.format(
            "Kính gửi %s,\n\nLịch hẹn của bạn với Bác sĩ %s vào %s đã được tạo và đang chờ xác nhận.\n\nTrân trọng,\nHệ thống Y tế",
            patient.getFullName(),
            doctor.getFullName(),
            appointment.getAppointmentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        );

        notificationService.sendSMSNotification(doctor.getId(), patient.getId(), smsContent);

        return appointment;




    }

    @Override
    public Appointment confirmAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                                                        .orElseThrow(() -> new ResourceNotFoundException("Appointment Not Found"));
        
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                                                        .orElseThrow(() -> new ResourceNotFoundException("Appointment Not Found"));
        
        appointment.setStatus(AppointmentStatus.CANCELED);
        return appointmentRepository.save(appointment);
    }

    

}
