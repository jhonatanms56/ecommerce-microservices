package com.example.ecommerce.shipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
public class ShippingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShippingApplication.class, args);
    }
}

@RestController
@Tag(name = "Shipping Health", description = "Endpoints for checking the health of the Shipping Service")
class ShippingController {
    @Operation(summary = "Check service health", description = "Returns a simple string to verify the service is running")
    @GetMapping("/shipping/health")
    public String health() {
        return "Shipping Service is up and running!";
    }
}
