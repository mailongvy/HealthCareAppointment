package com.example.HealthCareAppointment.Service.Doctor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.HealthCareAppointment.Exception.ResourceNotFoundException;
import com.example.HealthCareAppointment.Model.Doctor;
import com.example.HealthCareAppointment.Model.Specialty;
import com.example.HealthCareAppointment.Repositories.DoctorRepository;
import com.example.HealthCareAppointment.Repositories.SpecialtyRepository;

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
    public List<Doctor> getDoctorBySpecialty(Specialty specialty) {
        if (!doctorRepository.existsBySpecialtyName(specialty.getName())) {
            throw new ResourceNotFoundException("Specialty Not found");
        }

        return doctorRepository.findBySpecialtyName(specialty.getName());
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Doctor createDoctor(Docto)

    @Override
    public void deleteDoctor(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
