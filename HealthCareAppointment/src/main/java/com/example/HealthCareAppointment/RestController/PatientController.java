package com.example.HealthCareAppointment.RestController;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.example.HealthCareAppointment.Service.Patient.PatientService;

import lombok.RequiredArgsConstructor;

@RestController("${apiPrefix}/patients")
@Service
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    // get all patients
    public 
}
