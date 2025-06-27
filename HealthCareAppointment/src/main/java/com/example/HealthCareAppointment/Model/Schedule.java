package com.example.HealthCareAppointment.Model;

import java.time.DayOfWeek;
import java.time.LocalTime;

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

    private LocalTime startTime;

    private LocalTime endTime;

    private DayOfWeek dayOfWeek;

    private boolean isAvailable; // check xem lịch còn trống không

    @ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="id", referencedColumnName="id")
    private Doctor doctor; // một bác sĩ có thể có nhiều lịch

}
