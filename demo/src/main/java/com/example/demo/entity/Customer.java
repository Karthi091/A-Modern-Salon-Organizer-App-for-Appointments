package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String username;
    @Column()
    private String email;
    @JsonIgnore
    @Column()
    private String password;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Reward reward;
    // Constructors
    public Customer() {
    }
    public Customer(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    public Reward getReward() {
        return reward;
    }
    public void setReward(Reward reward) {
        this.reward = reward;
    }
    // Helper methods
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setCustomer(this);
    }
    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
        appointment.setCustomer(null);
    }
    @Override
        public String toString() {
            return "Customer [id=" + id + ", username=" + username + ", email=" + email + "]";                                                                                                                                                   
        }
}