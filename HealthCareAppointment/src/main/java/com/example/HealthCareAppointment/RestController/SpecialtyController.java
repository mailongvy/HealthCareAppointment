package com.example.HealthCareAppointment.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HealthCareAppointment.Model.Specialty;
import com.example.HealthCareAppointment.Response.ApiResponse;
import com.example.HealthCareAppointment.Service.Specialty.SpecialtyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/specialties")
public class SpecialtyController {
    private final SpecialtyService specialtyService;

    @PostMapping("/specialty/add")
    public ResponseEntity<ApiResponse> addSpecialty(@RequestBody Specialty specialty) {
        try {
            Specialty specialty1 = specialtyService.addSpecialty(specialty);
            return ResponseEntity.ok(new ApiResponse("Add Specialty success", specialty1));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/specialty/all")
    public ResponseEntity<ApiResponse> getAllSpecialties() {
        List<Specialty> specialtyList = specialtyService.getAllSpecialties();
        return ResponseEntity.ok(new ApiResponse("Found", specialtyList));
    }
}
