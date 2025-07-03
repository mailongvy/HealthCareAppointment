package com.example.HealthCareAppointment.Request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AppointmentRequest {
    private Long appoimentId;
    private Long doctorId;
    private Long patientId;
    private LocalDateTime appointmentDate;
    private String reason;

}
