package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.CheckoutRequest;
import team.kucing.anabulshopcare.service.CheckoutService;

import java.security.Principal;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "07. Checkout Controller")
public class CheckoutController {
    private CheckoutService checkoutService;

    @PostMapping("/checkout")
    @Operation(summary = "Checkout From Cart [BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> checkoutCart(@RequestBody CheckoutRequest checkoutRequest, Principal principal) {
        return this.checkoutService.createCheckout(checkoutRequest, principal);
    }

    @PostMapping("/checkout/{id}/cancel")
    @Operation(summary = "Cancel Checkout With Checkout ID [BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> cancelCheckout(@PathVariable("id") UUID id){
        return this.checkoutService.cancelCheckout(id);
    }

    @PostMapping("/checkout/{id}/confirmPayment")
    @Operation(summary = "Confirm Payment With Checkout ID [BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> confirmPayment(@PathVariable("id") UUID id){
        return this.checkoutService.confirmPayment(id);
    }
}