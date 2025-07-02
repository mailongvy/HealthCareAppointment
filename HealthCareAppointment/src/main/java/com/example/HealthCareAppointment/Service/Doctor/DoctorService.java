package com.example.HealthCareAppointment.Service.Doctor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.HealthCareAppointment.Exception.ResourceAlreadyExistException;
import com.example.HealthCareAppointment.Exception.ResourceNotFoundException;
import com.example.HealthCareAppointment.Model.Doctor;
import com.example.HealthCareAppointment.Model.Specialty;
import com.example.HealthCareAppointment.Repositories.DoctorRepository;
import com.example.HealthCareAppointment.Repositories.SpecialtyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService implements IDoctorService {
    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository;


    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Doctor Not Found"));
    }

    @Override
    public List<Doctor> getDoctorBySpecialty(String specialty) {
        if (!specialtyRepository.existsByName(specialty)) {
            throw new ResourceNotFoundException("Specialty Not found");
        }

        return doctorRepository.findBySpecialtyName(specialty);
    }

    

    @Override
    /*
     * private String fullName;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;
     */
    public Doctor updateDoctor(Doctor updateDoctor, Long id) {
        return doctorRepository.findById(id)
                                .map((existingDoctor) -> {
                                    existingDoctor.setFullName(updateDoctor.getFullName());
                                    existingDoctor.setEmail(updateDoctor.getEmail());
                                    existingDoctor.setPhoneNumber(updateDoctor.getPhoneNumber());
                                    existingDoctor.setDateOfBirth(updateDoctor.getDateOfBirth());
                                    
                                    // update the specialty for doctor
                                    Specialty specialty = specialtyRepository.findByName(updateDoctor.getSpecialty().getName());

                                    existingDoctor.setSpecialty(specialty);
                                    return existingDoctor;
                                })
                                .map(doctorRepository::save)
                                .orElseThrow(() -> new ResourceNotFoundException("Doctor Not Found"));
    }

    @Override
    public Doctor addDoctor(Doctor addDoctor) {
        if(doctorRepository.existsByEmail(addDoctor.getEmail())) {
            throw new ResourceAlreadyExistException("Doctor already exists");
        }
        // specialtyRepository.findByName(addDoctor.getSpecialty().getName());

        Specialty specialty = Optional.ofNullable(specialtyRepository.findByName(addDoctor.getSpecialty().getName()))
                                        .orElseThrow(() -> new ResourceNotFoundException("Specialty not found"));

        addDoctor.setSpecialty(specialty);

        return doctorRepository.save(createDoctor(addDoctor, specialty));
    }

    public Doctor createDoctor(Doctor addDoctor, Specialty specialty) {
        return new Doctor(
            addDoctor.getFullName(),
            addDoctor.getEmail(),
            addDoctor.getPhoneNumber(),
            addDoctor.getDateOfBirth(),
            specialty
        );
    }

    @Override
    @Transactional
    public void deleteDoctor(Long id) {
        doctorRepository.findById(id)
                        .ifPresentOrElse(
                            doctorRepository::delete,
                            () -> { throw new ResourceNotFoundException("Doctor Not Found"); }
                         );
    }
    

}
