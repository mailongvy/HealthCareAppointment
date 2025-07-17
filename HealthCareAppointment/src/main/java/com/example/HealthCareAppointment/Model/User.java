package com.example.HealthCareAppointment.Model;


import com.example.HealthCareAppointment.Enum.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    

    @OneToOne(cascade=CascadeType.ALL, mappedBy="user")
    private Patient patient;

    @OneToOne(cascade=CascadeType.ALL, mappedBy="user")
    private Doctor doctor;

    private String email = (role == Role.DOCTOR) ? doctor.getEmail() : patient.getEmail();

    public User(String email, String password, Role role, Patient patient, Doctor doctor) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.patient = patient;
        this.doctor = doctor;
    }





    

}
