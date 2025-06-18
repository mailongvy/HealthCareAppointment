package com.example.HealthCareAppointment.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HealthCareAppointment.Exception.ResourceNotFoundException;
import com.example.HealthCareAppointment.Model.Patient;
import com.example.HealthCareAppointment.Response.ApiResponse;
import com.example.HealthCareAppointment.Service.Patient.PatientService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/patients")
public class PatientController {
    private final PatientService patientService;

    // get all patients
    @GetMapping("/patient/allPatients")
    public ResponseEntity<ApiResponse> getAllPatient() {
        List<Patient> list = patientService.getAllPatients();
        return ResponseEntity.ok(new ApiResponse("Found", list));
    }

    // get patient by id    
    @GetMapping("/patient/{id}")
    public ResponseEntity<ApiResponse> getPatientById(@PathVariable Long id ) {
        try {
            Patient patient = patientService.getPatientById(id);
            return ResponseEntity.ok(new ApiResponse("Foundn patient", patient));
        } catch (ResourceNotFoundException e) {
            // TODO Auto-generated catch block
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new ApiResponse(e.getMessage(), null));
        }
    }

    //add patient
    @PostMapping("/patient/addPatient") 
    public ResponseEntity<ApiResponse> addPatient (@RequestBody Patient patient) {
        try {
            Patient addPatient = patientService.addPatient(patient);
            return  ResponseEntity.ok(new ApiResponse("Add Patient Success", addPatient));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body(new ApiResponse(e.getMessage(), null));
        }
    }

    // update patient 
    @PutMapping("/patient/update/{id}")
    public ResponseEntity<ApiResponse> updatePatient(@RequestBody Patient patient, @PathVariable Long id){
        try {
            Patient updatedPatient = patientService.updatePatient(patient, id);
            return ResponseEntity.ok(new ApiResponse("Update success", updatedPatient));
        } catch (ResourceNotFoundException e) {
            // TODO Auto-generated catch block
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new ApiResponse(e.getMessage(), null)); 
        }
    }

    @DeleteMapping("/patient/delete/{id}")
    public ResponseEntity<ApiResponse> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatientById(id);
            return ResponseEntity.ok(new ApiResponse("Delete success", null));
        } catch (ResourceNotFoundException e) {
            // TODO Auto-generated catch block
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new ApiResponse(e.getMessage(), null));  
        }
    }






}
