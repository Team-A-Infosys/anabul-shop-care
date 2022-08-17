package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.CheckoutRequest;
import team.kucing.anabulshopcare.service.CheckoutService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "07. Checkout Controller")
public class CheckoutController {
    private CheckoutService checkoutService;

    @PostMapping("/checkout/{id}")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> checkoutCart(@PathVariable("id") UUID id, @RequestBody CheckoutRequest checkoutRequest) {
        return this.checkoutService.createCheckout(id, checkoutRequest);
    }

    @PostMapping("/checkout/{id}/cancel")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> cancelCheckout(@PathVariable("id") UUID id){
        return this.checkoutService.cancelCheckout(id);
    }

    @PostMapping("/checkout/{id}/confirmPayment")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> confirmPayment(@PathVariable("id") UUID id){
        return this.checkoutService.confirmPayment(id);
    }
}