package com.example.HealthCareAppointment.Model;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// schedule: lịch làm việc của bác sĩ 
// tạo lịch làm việc để check có thể tạo cuộc hẹn không
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime startTime;

    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    // nếu lịch bác sĩ trống sẽ là true
    // bận là false
    
    private boolean isAvailable = true;

    @ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="doctor_id", referencedColumnName="id")
    @JsonIgnore
    private Doctor doctor; // một bác sĩ có thể có nhiều lịch

    public Schedule(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek, boolean isAvailable, Doctor doctor) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.isAvailable = isAvailable;
        this.doctor = doctor;
    }

    

}
