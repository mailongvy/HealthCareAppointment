package com.example.HealthCareAppointment.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HealthCareAppointment.Exception.ResourceNotFoundException;
import com.example.HealthCareAppointment.Model.Appointment;
import com.example.HealthCareAppointment.Request.AppointmentRequest;
import com.example.HealthCareAppointment.Response.ApiResponse;
import com.example.HealthCareAppointment.Service.Appointment.IAppointmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${apiPrefix}/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final IAppointmentService appointmentService;

    // get all appointments
    @GetMapping("/appointment/all")
    public  ResponseEntity<ApiResponse> getAllAppointment() {
        List<Appointment> list = appointmentService.getAllAppointments();
        return ResponseEntity.ok(new ApiResponse("Found", list));
    }

    // get appointment by id
    @GetMapping("/appointment/{id}")
    public ResponseEntity<ApiResponse> getAppointmentById(@PathVariable Long id) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            return ResponseEntity.ok(new ApiResponse("Found", appointment));
        } catch (ResourceNotFoundException e) {
            // TODO Auto-generated catch block
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body(new ApiResponse("Not Found", null));
        }

    }

    // create appointment
    @PostMapping("/appointment/add")
    public ResponseEntity<ApiResponse> createAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        try {
            Appointment appointment = appointmentService.createAppointment(appointmentRequest);
            return ResponseEntity.ok(new ApiResponse("Success", appointment));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                    .body(new ApiResponse("Can not create the appointment", null));
        }
    }

    // confirm appointment
    @PutMapping("/appointment/confirm/{id}")
    public ResponseEntity<ApiResponse> confirmAppointment(@PathVariable Long id) {
        try {
            Appointment appointment = appointmentService.confirmAppointment(id);
            return ResponseEntity.ok(new ApiResponse("Confirm Appointment success", appointment));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body(new ApiResponse("Cannot confirm appointment", null));
        }
    }

    // cancel appointment
    @PutMapping("/appointment/cancel/{id}")
    public ResponseEntity<ApiResponse> cancelAppointment(@PathVariable Long id) {
        try {
            Appointment appointment = appointmentService.cancelAppointment(id);
            return ResponseEntity.ok(new ApiResponse("Cancel Appointment success", appointment));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body(new ApiResponse("Cannot confirm appointment", null));
        }
    }



    








}
