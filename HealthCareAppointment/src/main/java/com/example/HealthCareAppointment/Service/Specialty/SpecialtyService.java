package com.example.HealthCareAppointment.Service.Specialty;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.HealthCareAppointment.Exception.ResourceAlreadyExistException;
import com.example.HealthCareAppointment.Exception.ResourceNotFoundException;
import com.example.HealthCareAppointment.Model.Specialty;
import com.example.HealthCareAppointment.Repositories.SpecialtyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecialtyService implements  ISpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    @Override
    public Specialty addSpecialty(Specialty addSpecialty) {
        // TODO Auto-generated method stub
        if (specialtyRepository.existsByName(addSpecialty.getName())) {
            throw new ResourceAlreadyExistException("Specialty already existed");
        }

        Specialty specialty = createSpecialty(addSpecialty);
        return specialtyRepository.save(specialty);
    }

    public Specialty createSpecialty(Specialty addSpecialty) {
        return new Specialty(
            addSpecialty.getName(),
            addSpecialty.getDescription()
        );
    }

    @Override
    public void deleteSpecialty(Long id) {
        specialtyRepository.findById(id)
            .ifPresentOrElse(
                specialtyRepository::delete,
                () -> { throw new ResourceNotFoundException("Specialty Not Found"); }
            );
    }

    @Override
    public List<Specialty> getAllSpecialties() {
        // TODO Auto-generated method stub
        return specialtyRepository.findAll();
    }

    @Override
    public Specialty getSpecialtyById(Long id) {
        // TODO Auto-generated method stub
        return specialtyRepository.findById(id)
                                 .orElseThrow(() -> new ResourceNotFoundException("Specialty Not Found"));
    }

    @Override
    public Specialty updateSpecialty(Specialty updateSpecialty, Long id) {
        // TODO Auto-generated method stub
        return specialtyRepository.findById(id)
                                  .map(existingSpecialty -> {
                                    existingSpecialty.setName(updateSpecialty.getName());
                                    existingSpecialty.setDescription(updateSpecialty.getDescription());
                                    existingSpecialty.setDoctors(updateSpecialty.getDoctors());
                                    return specialtyRepository.save(existingSpecialty);
                                  })
                                  .orElseThrow(() -> new ResourceNotFoundException("Specialty Not Found"));
    }

    @Override
    public Specialty getSpecialtyByName(String name) {
        // TODO Auto-generated method stub
        if (!specialtyRepository.existsByName(name)) {
            throw new ResourceNotFoundException("Specialty already existed"); 
        } 
        
        return specialtyRepository.findByName(name);
    }

    
}
