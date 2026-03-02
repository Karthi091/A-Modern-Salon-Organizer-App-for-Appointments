package com.example.demo.Controller;

import com.example.demo.entity.Appointment;
import com.example.demo.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointment", description = "Appointment management APIs")
public class AppointmentController {
    private final AppointmentService appointmentService;
    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(summary = "Get all appointments", description = "Returns all appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved appointments", content = @Content(schema = @Schema(implementation = Appointment.class)))
    })

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @Operation(summary = "Get appointments with pagination and sorting", description = "Returns appointments with pagination and sorting capabilities")
    @GetMapping("/paged")
    public ResponseEntity<Page<Appointment>> getAppointmentsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentTime") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Page<Appointment> appointments = appointmentService
                .getAllAppointmentsPaged(PageRequest.of(page, size, Sort.by(sortDirection, sortBy)));
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @Operation(summary = "Get appointment by ID", description = "Returns a single appointment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the appointment", content = @Content(schema = @Schema(implementation = Appointment.class))),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        return appointment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Create a new appointment", description = "Creates a new appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment created successfully", content = @Content(schema = @Schema(implementation = Appointment.class)))
    })

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.saveAppointment(appointment);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing appointment", description = "Updates an appointment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment updated successfully", content = @Content(schema = @Schema(implementation = Appointment.class))),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        Optional<Appointment> existingAppointment = appointmentService.getAppointmentById(id);
        if (existingAppointment.isPresent()) {
            appointment.setId(id);
            Appointment updatedAppointment = appointmentService.saveAppointment(appointment);
            return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update appointment status", description = "Updates only the status of an appointment")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Appointment> updateAppointmentStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Optional<Appointment> existingAppointment = appointmentService.getAppointmentById(id);
        if (existingAppointment.isPresent()) {
            Appointment appointment = existingAppointment.get();
            appointment.setStatus(status);
            Appointment updatedAppointment = appointmentService.saveAppointment(appointment);
            return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete an appointment", description = "Deletes an appointment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Appointment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        if (appointment.isPresent()) {
            appointmentService.deleteAppointment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get appointments by customer ID", description = "Returns all appointments for a specific customer")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByCustomerId(@PathVariable Long customerId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByCustomerId(customerId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}
