package com.example.HealthCareAppointment.Model;

import java.time.LocalDateTime;

import com.example.HealthCareAppointment.Enum.AppointmentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="id", referencedColumnName="doctor_id")
    private Doctor doctor;

    @ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="id", referencedColumnName="patient_id")
    private Patient patient;

    // ngày hẹn 
    private LocalDateTime appointmentDate;

    // trạng thái cuộc hẹn
    private AppointmentStatus status;

    private String reason;

    // lịch sử tạo cuộc hẹn 
    private LocalDateTime createAt;


}
