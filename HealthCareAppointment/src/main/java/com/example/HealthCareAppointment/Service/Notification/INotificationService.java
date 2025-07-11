package com.example.HealthCareAppointment.Service.Notification;

public interface INotificationService {
    public void sendSMSNotification(Long doctorId, Long patientId, String content);
}
