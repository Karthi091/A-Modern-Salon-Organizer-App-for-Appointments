package com.example.demo.dto;

import java.time.LocalDateTime;

public class AppointmentDTO {

    private String serviceName;

    private LocalDateTime appointmentTime;

    private String status;

    private Long customerId;

    // Default constructor

    public AppointmentDTO() {

    }

    // Constructor with all fields

    public AppointmentDTO(String serviceName, LocalDateTime appointmentTime, String status, Long customerId) {

        this.serviceName = serviceName;

        this.appointmentTime = appointmentTime;

        this.status = status;

        this.customerId = customerId;

    }

    // Getters and Setters

    public String getServiceName() {

        return serviceName;

    }

    public void setServiceName(String serviceName) {

        this.serviceName = serviceName;

    }

    public LocalDateTime getAppointmentTime() {

        return appointmentTime;

    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {

        this.appointmentTime = appointmentTime;

    }

    public String getStatus() {

        return status;

    }

    public void setStatus(String status) {

        this.status = status;

    }

    public Long getCustomerId() {

        return customerId;

    }

    public void setCustomerId(Long customerId) {

        this.customerId = customerId;

    }

}
