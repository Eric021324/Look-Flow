package com.lookflow.infrastructure.controller;

import com.lookflow.application.port.input.ProcessPaymentUseCase;
import com.lookflow.application.port.input.QueryPaymentUseCase;
import com.lookflow.domain.model.entity.Payment;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.PaymentId;
import com.lookflow.infrastructure.dto.request.ProcessPaymentRequest;
import com.lookflow.infrastructure.dto.response.ApiResponse;
import com.lookflow.infrastructure.dto.response.PaymentResponse;
import com.lookflow.infrastructure.mapper.PaymentRequestMapper;
import com.lookflow.infrastructure.mapper.PaymentResponseMapper;
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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payment", description = "Payment management operations")
public class PaymentController {

    private final ProcessPaymentUseCase processPaymentUseCase;
    private final QueryPaymentUseCase queryPaymentUseCase;
    private final PaymentRequestMapper paymentRequestMapper;
    private final PaymentResponseMapper paymentResponseMapper;

    @Autowired
    public PaymentController(ProcessPaymentUseCase processPaymentUseCase,
                           QueryPaymentUseCase queryPaymentUseCase,
                           PaymentRequestMapper paymentRequestMapper,
                           PaymentResponseMapper paymentResponseMapper) {
        this.processPaymentUseCase = processPaymentUseCase;
        this.queryPaymentUseCase = queryPaymentUseCase;
        this.paymentRequestMapper = paymentRequestMapper;
        this.paymentResponseMapper = paymentResponseMapper;
    }

    @PostMapping
    @Operation(summary = "Process a payment", description = "Processes a payment for an appointment")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Payment processed successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(
            @Valid @RequestBody ProcessPaymentRequest request) {
        try {
            Payment payment = processPaymentUseCase.processPayment(
                    paymentRequestMapper.toDomain(request).getAppointmentId(),
                    paymentRequestMapper.toDomain(request).getCustomerId(),
                    paymentRequestMapper.toDomain(request).getAmount(),
                    request.getPaymentDate()
            );
            
            PaymentResponse response = paymentResponseMapper.toResponse(payment);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Payment processed successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to process payment: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID", description = "Retrieves a payment by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payment found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Payment not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(
            @Parameter(description = "Payment ID") @PathVariable UUID id) {
        try {
            Optional<Payment> payment = queryPaymentUseCase.getPaymentById(new PaymentId(id));
            if (payment.isPresent()) {
                PaymentResponse response = paymentResponseMapper.toResponse(payment.get());
                return ResponseEntity.ok(ApiResponse.success(response));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Payment not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve payment: " + e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Get all payments", description = "Retrieves all payments")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {
        try {
            List<Payment> payments = queryPaymentUseCase.getAllPayments();
            List<PaymentResponse> responses = payments.stream()
                    .map(paymentResponseMapper::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve payments: " + e.getMessage()));
        }
    }

    @GetMapping("/appointment/{appointmentId}")
    @Operation(summary = "Get payments by appointment", description = "Retrieves all payments for a specific appointment")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByAppointment(
            @Parameter(description = "Appointment ID") @PathVariable UUID appointmentId) {
        try {
            List<Payment> payments = queryPaymentUseCase.getPaymentsByAppointment(new AppointmentId(appointmentId));
            List<PaymentResponse> responses = payments.stream()
                    .map(paymentResponseMapper::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve payments: " + e.getMessage()));
        }
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get payments by customer", description = "Retrieves all payments for a specific customer")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByCustomer(
            @Parameter(description = "Customer ID") @PathVariable UUID customerId) {
        try {
            List<Payment> payments = queryPaymentUseCase.getPaymentsByCustomer(new CustomerId(customerId));
            List<PaymentResponse> responses = payments.stream()
                    .map(paymentResponseMapper::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve payments: " + e.getMessage()));
        }
    }
}
