package com.example.demo.service;

import com.example.demo.Repository.AppointmentRepository;
import com.example.demo.entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    public Page<Appointment> getAllAppointmentsPaged(Pageable pageable) {
        return appointmentRepository.findAll(pageable);
    }
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }
    @Transactional
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    @Transactional
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
    public List<Appointment> getAppointmentsByCustomerId(Long customerId) {
        return appointmentRepository.findByCustomerId(customerId);
    }
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }
    @Transactional
    public Appointment updateAppointmentStatus(Long id, String status) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus(status);
            return appointmentRepository.save(appointment);
        }
        return null;
    }
}
