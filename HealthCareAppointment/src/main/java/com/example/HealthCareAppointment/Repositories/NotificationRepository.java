package com.example.HealthCareAppointment.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HealthCareAppointment.Enum.NotificationStatus;
import com.example.HealthCareAppointment.Enum.NotificationType;
import com.example.HealthCareAppointment.Model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
    List<Notification> findByDoctorId(Long doctorId);
    List<Notification> findByPatientId(Long patientId);
    List<Notification> findByDoctorIdAndType(Long doctorId, NotificationType type);
    List<Notification> findByPatientIdAndType(Long patientId, NotificationType type);
    List<Notification> findByStatus(NotificationStatus status);
}   
