package com.example.demo.Repository;

import com.example.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
        Customer findByUsername(String username);
        boolean existsByUsername(String username);
        boolean existsByEmail(String email);
        @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.appointments WHERE c.id = :id")
        Optional<Customer> findByIdWithAppointments(@Param("id") Long id);
}