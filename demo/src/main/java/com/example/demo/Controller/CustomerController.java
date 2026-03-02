package com.example.demo.Controller;

import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer", description = "Customer management APIs")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @Operation(summary = "Get all customers", description = "Returns all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved customers", content = @Content(schema = @Schema(implementation = Customer.class)))
    })
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    @Operation(summary = "Get customer by ID", description = "Returns a single customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer", content = @Content(schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Operation(summary = "Get customer with appointments", description = "Returns a customer and their appointments in a single query")
    @GetMapping("/{id}/with-appointments")
    public ResponseEntity<Customer> getCustomerWithAppointments(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerWithAppointments(id);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Operation(summary = "Get customer by username", description = "Returns a single customer by username")
    @GetMapping("/by-username/{username}")
    public ResponseEntity<Customer> getCustomerByUsername(@PathVariable String username) {
        Customer customer = customerService.getCustomerByUsername(username);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Create a new customer", description = "Creates a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully", content = @Content(schema = @Schema(implementation = Customer.class)))
    })
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }
    @Operation(summary = "Update an existing customer", description = "Updates a customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully", content = @Content(schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Optional<Customer> existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer.isPresent()) {
            customer.setId(id);
            Customer updatedCustomer = customerService.saveCustomer(customer);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Delete a customer", description = "Deletes a customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            customerService.deleteCustomer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}