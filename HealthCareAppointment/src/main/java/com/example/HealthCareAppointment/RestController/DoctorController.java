package com.example.HealthCareAppointment.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HealthCareAppointment.Model.Doctor;
import com.example.HealthCareAppointment.Response.ApiResponse;
import com.example.HealthCareAppointment.Service.Doctor.IDoctorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/doctors")
public class DoctorController { 
    private final IDoctorService doctorService;

    @GetMapping("/doctor/all")
    public ResponseEntity<ApiResponse> getAllDoctors() {
        List<Doctor> listDoctor = doctorService.getAllDoctors();
        return ResponseEntity.ok(new ApiResponse("Found", listDoctor));
    }

    @PostMapping("/doctor/add")
    public ResponseEntity<ApiResponse> addDoctor(@RequestBody Doctor doctor) {
        try {
            Doctor created = doctorService.addDoctor(doctor);
            return ResponseEntity.ok(new ApiResponse("Add Doctor success", created));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    
}
