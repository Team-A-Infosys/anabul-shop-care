package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import team.kucing.anabulshopcare.service.CheckoutService;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class CheckoutController {
    private CheckoutService checkoutService;

    @PostMapping("/checkout/{id}")
    public ResponseEntity<Object> checkoutCart(@PathVariable("id") UUID id) {
        return this.checkoutService.createCheckout(id);
    }
}