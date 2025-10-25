package com.lookflow.infrastructure.controller;

import com.lookflow.application.port.input.*;
import com.lookflow.domain.model.entity.Customer;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.infrastructure.dto.request.RegisterCustomerRequest;
import com.lookflow.infrastructure.dto.response.ApiResponse;
import com.lookflow.infrastructure.dto.response.CustomerResponse;
import com.lookflow.infrastructure.mapper.CustomerRequestMapper;
import com.lookflow.infrastructure.mapper.CustomerResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer", description = "Customer management operations")
public class CustomerController {

    private final RegisterCustomerUseCase registerCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final QueryCustomerUseCase queryCustomerUseCase;
    private final CustomerRequestMapper customerRequestMapper;
    private final CustomerResponseMapper customerResponseMapper;

    @Autowired
    public CustomerController(RegisterCustomerUseCase registerCustomerUseCase,
                            UpdateCustomerUseCase updateCustomerUseCase,
                            QueryCustomerUseCase queryCustomerUseCase,
                            CustomerRequestMapper customerRequestMapper,
                            CustomerResponseMapper customerResponseMapper) {
        this.registerCustomerUseCase = registerCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.queryCustomerUseCase = queryCustomerUseCase;
        this.customerRequestMapper = customerRequestMapper;
        this.customerResponseMapper = customerResponseMapper;
    }

    @PostMapping
    @Operation(summary = "Register a new customer", description = "Registers a new customer in the system")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Customer registered successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<CustomerResponse>> registerCustomer(
            @Valid @RequestBody RegisterCustomerRequest request) {
        try {
            Customer customer = registerCustomerUseCase.registerCustomer(
                    request.getName(),
                    request.getPhoneNumber(),
                    customerRequestMapper.toDomain(request).getEmail(),
                    request.getRegisterDate()
            );
            
            CustomerResponse response = customerResponseMapper.toResponse(customer);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Customer registered successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to register customer: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer", description = "Updates an existing customer")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Customer updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Customer not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @Parameter(description = "Customer ID") @PathVariable UUID id,
            @Valid @RequestBody RegisterCustomerRequest request) {
        try {
            Customer customer = updateCustomerUseCase.updateCustomer(
                    new CustomerId(id),
                    request.getName(),
                    request.getPhoneNumber(),
                    customerRequestMapper.toDomain(request).getEmail(),
                    request.getRegisterDate()
            );
            
            CustomerResponse response = customerResponseMapper.toResponse(customer);
            return ResponseEntity.ok(ApiResponse.success("Customer updated successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to update customer: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID", description = "Retrieves a customer by their ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Customer found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Customer not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(
            @Parameter(description = "Customer ID") @PathVariable UUID id) {
        try {
            Customer customer = queryCustomerUseCase.getCustomerById(new CustomerId(id));
            if (customer != null) {
                CustomerResponse response = customerResponseMapper.toResponse(customer);
                return ResponseEntity.ok(ApiResponse.success(response));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Customer not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve customer: " + e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Get all customers", description = "Retrieves all customers")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Customers retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers() {
        try {
            List<Customer> customers = queryCustomerUseCase.getAllCustomers();
            List<CustomerResponse> responses = customers.stream()
                    .map(customerResponseMapper::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve customers: " + e.getMessage()));
        }
    }
}
