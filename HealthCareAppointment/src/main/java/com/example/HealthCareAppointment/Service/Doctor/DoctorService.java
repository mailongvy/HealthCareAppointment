package com.example.HealthCareAppointment.Service.Doctor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.HealthCareAppointment.Enum.Role;
import com.example.HealthCareAppointment.Exception.ResourceAlreadyExistException;
import com.example.HealthCareAppointment.Exception.ResourceNotFoundException;
import com.example.HealthCareAppointment.Model.Doctor;
import com.example.HealthCareAppointment.Model.Specialty;
import com.example.HealthCareAppointment.Model.User;
import com.example.HealthCareAppointment.Repositories.DoctorRepository;
import com.example.HealthCareAppointment.Repositories.SpecialtyRepository;
import com.example.HealthCareAppointment.Repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService implements IDoctorService {
    @Autowired
    private final DoctorRepository doctorRepository;

    @Autowired
    private final SpecialtyRepository specialtyRepository;

    @Autowired
    private final UserRepository userRepository;

    
    private final BCryptPasswordEncoder passwordEncoder;


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
                                    // existingDoctor.setEmail(updateDoctor.getEmail());
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
    @Transactional
    public Doctor addDoctor(Doctor addDoctor) {
        // Kiểm tra email đã tồn tại chưa
        if(userRepository.existsByEmail(addDoctor.getUser().getEmail())) {
            throw new ResourceAlreadyExistException("Email already exists");
        }

        // Tìm specialty
        Specialty specialty = Optional.ofNullable(specialtyRepository.findByName(addDoctor.getSpecialty().getName()))
                                        .orElseThrow(() -> new ResourceNotFoundException("Specialty not found"));

        // Tạo User mới với password đã hash
        User newUser = new User(
            addDoctor.getUser().getEmail(),
            passwordEncoder.encode(addDoctor.getUser().getPassword()), //  Hash password
            Role.DOCTOR
        );
        
        // Save User trước
        User savedUser = userRepository.save(newUser);

        // Tạo Doctor với User đã save
        Doctor newDoctor = new Doctor(
            addDoctor.getFullName(),
            addDoctor.getPhoneNumber(),
            addDoctor.getDateOfBirth(),
            specialty,
            savedUser
        );

        // Save Doctor
        Doctor savedDoctor = doctorRepository.save(newDoctor);

        // Set bidirectional relationship
        savedUser.setDoctor(savedDoctor);
        userRepository.save(savedUser);

        return savedDoctor;
    }

    public Doctor createDoctor(Doctor addDoctor, Specialty specialty) {

        
        return new Doctor(
            addDoctor.getFullName(),
            // addDoctor.getEmail(),
            addDoctor.getPhoneNumber(),
            addDoctor.getDateOfBirth(),
            specialty,
            addDoctor.getUser()
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
