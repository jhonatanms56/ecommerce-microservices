package com.example.ecommerce.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
public class PaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }
}

@RestController
@Tag(name = "Payment Health", description = "Endpoints for checking the health of the Payment Service")
class PaymentController {
    @Operation(summary = "Check service health", description = "Returns a simple string to verify the service is running")
    @GetMapping("/payments/health")
    public String health() {
        return "Payment Service is up and running!";
    }
}
