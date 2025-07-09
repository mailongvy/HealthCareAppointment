package com.example.HealthCareAppointment.Model;

import java.time.LocalDateTime;

import com.example.HealthCareAppointment.Enum.NotificationStatus;
import com.example.HealthCareAppointment.Enum.NotificationType;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private Long id;

    private NotificationType type;

    private NotificationStatus status;

    private String content;

    private LocalDateTime sendAt;

    private Long recipantId;



}
