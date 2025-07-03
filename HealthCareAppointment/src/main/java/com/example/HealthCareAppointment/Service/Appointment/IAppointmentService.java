package com.example.HealthCareAppointment.Service.Appointment;

import java.util.List;

import com.example.HealthCareAppointment.Model.Appointment;
import com.example.HealthCareAppointment.Request.AppointmentRequest;

public interface IAppointmentService {
    // get appointment by id
    public Appointment getAppointmentById(Long appointmentId);

    // get all appointment
    public List<Appointment> getAllAppointments();

    // get appointment by doctorId 
    public List<Appointment> getAppointmentsByDoctor(Long doctorId);

    // get appointment by patientId
    public List<Appointment> getAppointmentsByPatient(Long patientId);

    // create appointment 
    public Appointment createAppointment(AppointmentRequest appointmentRequest);

    // confirm appointment
    public Appointment confirmAppointment(Long appointmentId);

    // cancel appointment
    public Appointment cancelAppointment(Long appointmentId);

    
}
