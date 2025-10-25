package com.lookflow.infrastructure.controller;

import com.lookflow.application.port.input.*;
import com.lookflow.domain.model.entity.Appointment;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.infrastructure.dto.request.CreateAppointmentRequest;
import com.lookflow.infrastructure.dto.response.ApiResponse;
import com.lookflow.infrastructure.dto.response.AppointmentResponse;
import com.lookflow.infrastructure.mapper.AppointmentRequestMapper;
import com.lookflow.infrastructure.mapper.AppointmentResponseMapper;
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
@RequestMapping("/api/v1/appointments")
@Tag(name = "Appointment", description = "Appointment management operations")
public class AppointmentController {

    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final ConfirmAppointmentUseCase confirmAppointmentUseCase;
    private final CancelAppointmentUseCase cancelAppointmentUseCase;
    private final CompleteAppointmentUseCase completeAppointmentUseCase;
    private final QueryAppointmentUseCase queryAppointmentUseCase;
    private final AppointmentRequestMapper appointmentRequestMapper;
    private final AppointmentResponseMapper appointmentResponseMapper;

    @Autowired
    public AppointmentController(CreateAppointmentUseCase createAppointmentUseCase,
                               ConfirmAppointmentUseCase confirmAppointmentUseCase,
                               CancelAppointmentUseCase cancelAppointmentUseCase,
                               CompleteAppointmentUseCase completeAppointmentUseCase,
                               QueryAppointmentUseCase queryAppointmentUseCase,
                               AppointmentRequestMapper appointmentRequestMapper,
                               AppointmentResponseMapper appointmentResponseMapper) {
        this.createAppointmentUseCase = createAppointmentUseCase;
        this.confirmAppointmentUseCase = confirmAppointmentUseCase;
        this.cancelAppointmentUseCase = cancelAppointmentUseCase;
        this.completeAppointmentUseCase = completeAppointmentUseCase;
        this.queryAppointmentUseCase = queryAppointmentUseCase;
        this.appointmentRequestMapper = appointmentRequestMapper;
        this.appointmentResponseMapper = appointmentResponseMapper;
    }

    @PostMapping
    @Operation(summary = "Create a new appointment", description = "Creates a new appointment with the provided details")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Appointment created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<AppointmentResponse>> createAppointment(
            @Valid @RequestBody CreateAppointmentRequest request) {
        try {
            Appointment appointment = createAppointmentUseCase.createAppointment(
                    appointmentRequestMapper.toDomain(request).getCustomerId(),
                    appointmentRequestMapper.toDomain(request).getEmployeeId(),
                    appointmentRequestMapper.toDomain(request).getServiceIds(),
                    request.getStartDate(),
                    request.getEndDate()
            );
            
            AppointmentResponse response = appointmentResponseMapper.toResponse(appointment);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Appointment created successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to create appointment: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/confirm")
    @Operation(summary = "Confirm an appointment", description = "Confirms a pending appointment")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointment confirmed successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Appointment not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid appointment state"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<AppointmentResponse>> confirmAppointment(
            @Parameter(description = "Appointment ID") @PathVariable UUID id) {
        try {
            Appointment appointment = confirmAppointmentUseCase.confirmAppointment(new AppointmentId(id));
            AppointmentResponse response = appointmentResponseMapper.toResponse(appointment);
            return ResponseEntity.ok(ApiResponse.success("Appointment confirmed successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to confirm appointment: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel an appointment", description = "Cancels an appointment")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointment cancelled successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Appointment not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid appointment state"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<AppointmentResponse>> cancelAppointment(
            @Parameter(description = "Appointment ID") @PathVariable UUID id) {
        try {
            Appointment appointment = cancelAppointmentUseCase.cancelAppointment(new AppointmentId(id));
            AppointmentResponse response = appointmentResponseMapper.toResponse(appointment);
            return ResponseEntity.ok(ApiResponse.success("Appointment cancelled successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to cancel appointment: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/complete")
    @Operation(summary = "Complete an appointment", description = "Marks an appointment as completed")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointment completed successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Appointment not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid appointment state"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<AppointmentResponse>> completeAppointment(
            @Parameter(description = "Appointment ID") @PathVariable UUID id) {
        try {
            Appointment appointment = completeAppointmentUseCase.completeAppointment(new AppointmentId(id));
            AppointmentResponse response = appointmentResponseMapper.toResponse(appointment);
            return ResponseEntity.ok(ApiResponse.success("Appointment completed successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to complete appointment: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get appointment by ID", description = "Retrieves an appointment by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointment found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Appointment not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<AppointmentResponse>> getAppointmentById(
            @Parameter(description = "Appointment ID") @PathVariable UUID id) {
        try {
            Appointment appointment = queryAppointmentUseCase.getAppointmentById(new AppointmentId(id));
            if (appointment != null) {
                AppointmentResponse response = appointmentResponseMapper.toResponse(appointment);
                return ResponseEntity.ok(ApiResponse.success(response));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Appointment not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve appointment: " + e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Get all appointments", description = "Retrieves all appointments")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAllAppointments() {
        try {
            List<Appointment> appointments = queryAppointmentUseCase.getAllAppointments();
            List<AppointmentResponse> responses = appointments.stream()
                    .map(appointmentResponseMapper::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve appointments: " + e.getMessage()));
        }
    }
}
