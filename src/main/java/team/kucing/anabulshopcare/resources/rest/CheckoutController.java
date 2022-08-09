package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.CheckoutRequest;
import team.kucing.anabulshopcare.service.CheckoutService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "7. Checkout Controller")
public class CheckoutController {
    private CheckoutService checkoutService;

    @PostMapping("/checkout/{id}")
    public ResponseEntity<Object> checkoutCart(@PathVariable("id") UUID id, @RequestBody CheckoutRequest checkoutRequest) {
        return this.checkoutService.createCheckout(id, checkoutRequest);
    }

    @PostMapping("/checkout/{id}/cancel")
    public ResponseEntity<Object> cancelCheckout(@PathVariable("id") UUID id){
        return this.checkoutService.cancelCheckout(id);
    }
}