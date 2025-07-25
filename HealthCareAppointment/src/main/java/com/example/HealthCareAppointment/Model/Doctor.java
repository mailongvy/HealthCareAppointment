package com.example.HealthCareAppointment.Model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    // private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    @OneToMany(mappedBy="doctor", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Schedule> schedules;

    @ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="specialty_id", referencedColumnName="id")
    private Specialty specialty;

    @OneToMany(mappedBy="doctor", cascade=CascadeType.ALL)   
    @JsonIgnore
    private List<Appointment> appointment;

    @OneToMany(mappedBy="doctor", cascade=CascadeType.ALL)
    private List<Notification> notifications;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;
    

    public Doctor(String fullName, String phoneNumber, LocalDate dateOfBirth, Specialty specialty) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.specialty = specialty;
    }


    public Doctor(String fullName, String phoneNumber, LocalDate dateOfBirth, Specialty specialty, User user) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.specialty = specialty;
        this.user = user;
    }

    

    

}
