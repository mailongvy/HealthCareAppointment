package com.example.HealthCareAppointment.Service.Notification;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HealthCareAppointment.Config.Twilio;
import com.example.HealthCareAppointment.Enum.NotificationStatus;
import com.example.HealthCareAppointment.Enum.NotificationType;
import com.example.HealthCareAppointment.Exception.ResourceNotFoundException;
import com.example.HealthCareAppointment.Model.Doctor;
import com.example.HealthCareAppointment.Model.Notification;
import com.example.HealthCareAppointment.Model.Patient;
import com.example.HealthCareAppointment.Repositories.DoctorRepository;
import com.example.HealthCareAppointment.Repositories.NotificationRepository;
import com.example.HealthCareAppointment.Repositories.PatientRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {
    @Autowired
    private final Twilio twilio;

    private final NotificationRepository notificationRepository;

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    @Override
    public void sendSMSNotification(Long doctorId, Long patientId, String content) {
        // TODO Auto-generated method stub
        Doctor doctor = doctorRepository.findById(doctorId)
                                        .orElseThrow(() -> new ResourceNotFoundException("Doctor Not Found"));
        
        Patient patient = patientRepository.findById(patientId)
                                            .orElseThrow(() -> new ResourceNotFoundException("Doctor Not Found"));

        Notification notification = new Notification();
        notification.setDoctor(doctor);
        notification.setPatient(patient);
        notification.setType(NotificationType.SMS);
        notification.setContent(content);
        notification.setSendAt(LocalDateTime.now());
        

        try {
            Message.creator(
                // chuyển đổi mã quốc tế thành +84
                new PhoneNumber("+84".concat(patient.getPhoneNumber().substring(1))),
                new PhoneNumber(twilio.getPhoneNumber()),
                content
            ).create();

            notification.setStatus(NotificationStatus.SENT);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            notification.setStatus(NotificationStatus.FAILED);
            throw new RuntimeException("Failed to send SMS: " + e.getMessage() );
        }

        notificationRepository.save(notification);






        
    }


    
}
