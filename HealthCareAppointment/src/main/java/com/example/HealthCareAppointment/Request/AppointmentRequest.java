package com.example.HealthCareAppointment.Request;

import lombok.Data;

@Data
public class AppointmentRequest {
    private Long appoimentId;
    private Long doctorId;
    private Long patientId;
    private String reason;

}
