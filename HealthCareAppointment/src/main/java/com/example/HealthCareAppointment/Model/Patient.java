package com.example.HealthCareAppointment.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String gender;

    private String address;

    

    public Patient(String fullName, String email, String phoneNumber, LocalDate dateOfBirth, String gender, String address) {
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }





}
