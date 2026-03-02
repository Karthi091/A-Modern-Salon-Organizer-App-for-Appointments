package com.example.demo.entity;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String serviceName;
    @Column()
    private LocalDateTime appointmentTime;
    @Column()
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    // Constructors
    public Appointment() {
    }
    public Appointment(String serviceName, LocalDateTime appointmentTime, String status) {
        this.serviceName = serviceName;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    @Override
        public String toString() {
            return "Appointment [id=" + id + ", serviceName=" + serviceName + ", appointmentTime=" + appointmentTime + ", status=" + status + "]";
            }
}
