package com.lookflow.infrastructure.controller;

import com.lookflow.application.port.input.*;
import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.ServiceId;
import com.lookflow.infrastructure.dto.request.CreateServiceRequest;
import com.lookflow.infrastructure.dto.response.ApiResponse;
import com.lookflow.infrastructure.dto.response.ServiceResponse;
import com.lookflow.infrastructure.mapper.ServiceRequestMapper;
import com.lookflow.infrastructure.mapper.ServiceResponseMapper;
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
@RequestMapping("/api/v1/services")
@Tag(name = "Service", description = "Service management operations")
public class ServiceController {

    private final CreateServiceUseCase createServiceUseCase;
    private final UpdateServiceUseCase updateServiceUseCase;
    private final ActivateServiceUseCase activateServiceUseCase;
    private final QueryServiceUseCase queryServiceUseCase;
    private final ServiceRequestMapper serviceRequestMapper;
    private final ServiceResponseMapper serviceResponseMapper;

    @Autowired
    public ServiceController(CreateServiceUseCase createServiceUseCase,
                           UpdateServiceUseCase updateServiceUseCase,
                           ActivateServiceUseCase activateServiceUseCase,
                           QueryServiceUseCase queryServiceUseCase,
                           ServiceRequestMapper serviceRequestMapper,
                           ServiceResponseMapper serviceResponseMapper) {
        this.createServiceUseCase = createServiceUseCase;
        this.updateServiceUseCase = updateServiceUseCase;
        this.activateServiceUseCase = activateServiceUseCase;
        this.queryServiceUseCase = queryServiceUseCase;
        this.serviceRequestMapper = serviceRequestMapper;
        this.serviceResponseMapper = serviceResponseMapper;
    }

    @PostMapping
    @Operation(summary = "Create a new service", description = "Creates a new service in the system")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Service created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<ServiceResponse>> createService(
            @Valid @RequestBody CreateServiceRequest request) {
        try {
            Service service = createServiceUseCase.createService(
                    request.getName(),
                    request.getDescription(),
                    request.getDuration(),
                    serviceRequestMapper.toDomain(request).getCost(),
                    serviceRequestMapper.toDomain(request).getServiceCategory(),
                    request.getActive()
            );
            
            ServiceResponse response = serviceResponseMapper.toResponse(service);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Service created successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to create service: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update service", description = "Updates an existing service")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Service updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Service not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<ServiceResponse>> updateService(
            @Parameter(description = "Service ID") @PathVariable UUID id,
            @Valid @RequestBody CreateServiceRequest request) {
        try {
            Service service = updateServiceUseCase.updateService(
                    new ServiceId(id),
                    request.getName(),
                    request.getDescription(),
                    request.getDuration(),
                    serviceRequestMapper.toDomain(request).getCost(),
                    serviceRequestMapper.toDomain(request).getServiceCategory(),
                    request.getActive()
            );
            
            ServiceResponse response = serviceResponseMapper.toResponse(service);
            return ResponseEntity.ok(ApiResponse.success("Service updated successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to update service: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Activate/Deactivate service", description = "Activates or deactivates a service")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Service status updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Service not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid operation"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<ServiceResponse>> activateService(
            @Parameter(description = "Service ID") @PathVariable UUID id,
            @Parameter(description = "Activate service") @RequestParam boolean active) {
        try {
            Service service = activateServiceUseCase.activateService(new ServiceId(id), active);
            ServiceResponse response = serviceResponseMapper.toResponse(service);
            return ResponseEntity.ok(ApiResponse.success("Service status updated successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to update service status: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get service by ID", description = "Retrieves a service by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Service found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Service not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<ServiceResponse>> getServiceById(
            @Parameter(description = "Service ID") @PathVariable UUID id) {
        try {
            Service service = queryServiceUseCase.getServiceById(new ServiceId(id));
            if (service != null) {
                ServiceResponse response = serviceResponseMapper.toResponse(service);
                return ResponseEntity.ok(ApiResponse.success(response));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Service not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve service: " + e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Get all services", description = "Retrieves all services")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Services retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<ServiceResponse>>> getAllServices() {
        try {
            List<Service> services = queryServiceUseCase.getAllServices();
            List<ServiceResponse> responses = services.stream()
                    .map(serviceResponseMapper::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve services: " + e.getMessage()));
        }
    }

    @GetMapping("/active")
    @Operation(summary = "Get active services", description = "Retrieves all active services")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Active services retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<ServiceResponse>>> getActiveServices() {
        try {
            List<Service> services = queryServiceUseCase.getActiveServices();
            List<ServiceResponse> responses = services.stream()
                    .map(serviceResponseMapper::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve active services: " + e.getMessage()));
        }
    }
}
