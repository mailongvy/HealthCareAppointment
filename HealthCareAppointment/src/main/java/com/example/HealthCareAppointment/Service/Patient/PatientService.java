package com.example.HealthCareAppointment.Service.Patient;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.HealthCareAppointment.Enum.Role;
import com.example.HealthCareAppointment.Exception.ResourceAlreadyExistException;
import com.example.HealthCareAppointment.Exception.ResourceNotFoundException;
import com.example.HealthCareAppointment.Model.Patient;
import com.example.HealthCareAppointment.Model.User;
import com.example.HealthCareAppointment.Repositories.PatientRepository;
import com.example.HealthCareAppointment.Repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // get all patients 
    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // get patient by id
    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Patient Not Found"));
    }

    // add patient 
    @Override
    public Patient addPatient(Patient patient) {
        if (userRepository.existsByEmail(patient.getUser().getEmail())) {
            throw new ResourceAlreadyExistException("Patient already exist");
        }

        User user = new User(
            patient.getUser().getEmail(), 
            passwordEncoder.encode(patient.getUser().getPassword()), 
            Role.PATIENT
        );

        // save user
        User savedUser = userRepository.save(user);


        Patient addPatient = createPatient(patient, savedUser);

        // save patient
        Patient savedPatient = patientRepository.save(addPatient);

        //set bidirectional relationship
        savedUser.setPatient(savedPatient);
        userRepository.save(savedUser);

        return savedPatient;
    }

    // create patient to add
    public Patient createPatient(Patient addPatient, User user) {
        if (patientRepository.existsByUserEmail(addPatient.getUser().getEmail())) {
            throw new ResourceAlreadyExistException("Patient already exists");
        }

        return new Patient(
            addPatient.getFullName(),
            addPatient.getPhoneNumber(),
            addPatient.getDateOfBirth(),
            addPatient.getGender(),
            addPatient.getAddress(),
            user
        );
    }

    //update Patient
    @Override
    public Patient updatePatient(Patient requestPatient, Long id) {
        return patientRepository.findById(id)
                                .map(existingPatient -> {
                                    existingPatient.setFullName(requestPatient.getFullName());
                                    // existingPatient.setEmail(requestPatient.getEmail());
                                    existingPatient.setPhoneNumber(requestPatient.getPhoneNumber());
                                    existingPatient.setDateOfBirth(requestPatient.getDateOfBirth());
                                    existingPatient.setGender(requestPatient.getGender());
                                    existingPatient.setAddress(requestPatient.getAddress());
                                    return existingPatient;
                                })
                                .map(patientRepository::save)
                                .orElseThrow(() -> new ResourceNotFoundException("Patient Not found"));
    }

    //delete patient by id 
    @Override
    @Transactional
    public void deletePatientById(Long id) {
        patientRepository.findById(id)
                         .ifPresentOrElse(
                             patientRepository::delete,
                             () -> { throw new ResourceNotFoundException("Patient not found"); }
                         );
    }

    
}
