package com.lookflow.infrastructure.controller;

import com.lookflow.application.port.input.*;
import com.lookflow.domain.model.entity.Employee;
import com.lookflow.domain.model.valueobject.EmployeeId;
import com.lookflow.infrastructure.dto.request.RegisterEmployeeRequest;
import com.lookflow.infrastructure.dto.response.ApiResponse;
import com.lookflow.infrastructure.dto.response.EmployeeResponse;
import com.lookflow.infrastructure.mapper.EmployeeRequestMapper;
import com.lookflow.infrastructure.mapper.EmployeeResponseMapper;
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
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee", description = "Employee management operations")
public class EmployeeController {

    private final RegisterEmployeeUseCase registerEmployeeUseCase;
    private final UpdateEmployeeUseCase updateEmployeeUseCase;
    private final QueryEmployeeUseCase queryEmployeeUseCase;
    private final ManageEmployeeWorkShiftsUseCase manageEmployeeWorkShiftsUseCase;
    private final EmployeeRequestMapper employeeRequestMapper;
    private final EmployeeResponseMapper employeeResponseMapper;

    @Autowired
    public EmployeeController(RegisterEmployeeUseCase registerEmployeeUseCase,
                            UpdateEmployeeUseCase updateEmployeeUseCase,
                            QueryEmployeeUseCase queryEmployeeUseCase,
                            ManageEmployeeWorkShiftsUseCase manageEmployeeWorkShiftsUseCase,
                            EmployeeRequestMapper employeeRequestMapper,
                            EmployeeResponseMapper employeeResponseMapper) {
        this.registerEmployeeUseCase = registerEmployeeUseCase;
        this.updateEmployeeUseCase = updateEmployeeUseCase;
        this.queryEmployeeUseCase = queryEmployeeUseCase;
        this.manageEmployeeWorkShiftsUseCase = manageEmployeeWorkShiftsUseCase;
        this.employeeRequestMapper = employeeRequestMapper;
        this.employeeResponseMapper = employeeResponseMapper;
    }

    @PostMapping
    @Operation(summary = "Register a new employee", description = "Registers a new employee in the system")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Employee registered successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<EmployeeResponse>> registerEmployee(
            @Valid @RequestBody RegisterEmployeeRequest request) {
        try {
            Employee employee = registerEmployeeUseCase.registerEmployee(
                    request.getName(),
                    request.getFirstSurname(),
                    request.getSecondSurname(),
                    employeeRequestMapper.toDomain(request).getEmail(),
                    employeeRequestMapper.toAddress(request.getAddress()),
                    employeeRequestMapper.toDomain(request).getWorkShifts(),
                    employeeRequestMapper.toDomain(request).getServices()
            );
            
            EmployeeResponse response = employeeResponseMapper.toResponse(employee);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Employee registered successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to register employee: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employee", description = "Updates an existing employee")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Employee not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
            @Parameter(description = "Employee ID") @PathVariable UUID id,
            @Valid @RequestBody RegisterEmployeeRequest request) {
        try {
            Employee employee = updateEmployeeUseCase.updateEmployee(
                    new EmployeeId(id),
                    request.getName(),
                    request.getFirstSurname(),
                    request.getSecondSurname(),
                    employeeRequestMapper.toDomain(request).getEmail(),
                    employeeRequestMapper.toAddress(request.getAddress()),
                    employeeRequestMapper.toDomain(request).getWorkShifts(),
                    employeeRequestMapper.toDomain(request).getServices()
            );
            
            EmployeeResponse response = employeeResponseMapper.toResponse(employee);
            return ResponseEntity.ok(ApiResponse.success("Employee updated successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to update employee: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/work-shifts")
    @Operation(summary = "Manage employee work shifts", description = "Updates employee work shifts")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Work shifts updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Employee not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid work shifts"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<EmployeeResponse>> manageWorkShifts(
            @Parameter(description = "Employee ID") @PathVariable UUID id,
            @Valid @RequestBody RegisterEmployeeRequest request) {
        try {
            Employee employee = manageEmployeeWorkShiftsUseCase.manageWorkShifts(
                    new EmployeeId(id),
                    employeeRequestMapper.toDomain(request).getWorkShifts()
            );
            
            EmployeeResponse response = employeeResponseMapper.toResponse(employee);
            return ResponseEntity.ok(ApiResponse.success("Work shifts updated successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to update work shifts: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID", description = "Retrieves an employee by their ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Employee found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Employee not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(
            @Parameter(description = "Employee ID") @PathVariable UUID id) {
        try {
            Employee employee = queryEmployeeUseCase.getEmployeeById(new EmployeeId(id));
            if (employee != null) {
                EmployeeResponse response = employeeResponseMapper.toResponse(employee);
                return ResponseEntity.ok(ApiResponse.success(response));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Employee not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve employee: " + e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Get all employees", description = "Retrieves all employees")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Employees retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees() {
        try {
            List<Employee> employees = queryEmployeeUseCase.getAllEmployees();
            List<EmployeeResponse> responses = employees.stream()
                    .map(employeeResponseMapper::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve employees: " + e.getMessage()));
        }
    }
}
